package api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class Api{
	private static Api instance;
	private String api_key;
	private int RateLimit = 5;
	
	public static void init()
	{
		instance = new Api();
	}
	
	
	public String getApi_key() {
		return api_key;
	}

	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	
	public boolean isApiKeyExist()
	{
		return api_key != null && !api_key.trim().isEmpty();
	}
	
	public void request(String query) throws IOException, InterruptedException
	{
		List<Media> res = new ArrayList<Media>(); //création d'une liste pour stocker les Media reçu.
		
		String encodedQuery = java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8); // on encore le query en UTF-8
		String url = String.format("https://xmdbapi.com/api/v1/search?q=%s&limit=%d&apiKey=%s", encodedQuery, this.RateLimit, this.api_key);
		
		try(HttpClient client = HttpClient.newHttpClient()) // on crée un client http
		{
	        HttpRequest request = HttpRequest.newBuilder() // requette http
	                .uri(URI.create(url.trim()))
	                .GET()
	                .build();
	        
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); //reponse
	        
	        String body = response.body(); // on recupère le corp de la réponse
	        
	        JSONObject bodypars = new JSONObject(body); // on transofrme le string en objet json
	        JSONArray result = bodypars.getJSONArray("results");  //on transform l'objet en tableau
	        
	        
	        for(int i = 0; i < result.length(); i++) // on crée les media en fonction du res
	        {
	        	JSONObject fs = result.getJSONObject(i);
	        	
	        	System.out.println(fs);
	        	
	        	String year = "not found";
	        	String image = "";
	        	
	        	if(fs.has("year")) // on recup l'année
	        	{
	        		year = String.valueOf(fs.getInt("year"));
	        	}
	        	if(fs.has("image") && !fs.isNull("image")) //on récup l'image
	        	{
	        		image = fs.getString("image");
	        	}
	        
	        	Media m = new Media(
	        			fs.getString("id"),
	        			fs.getString("name"),
	        			fs.getString("description"),
	        			String.valueOf(fs.getInt("rank")),
	        			image,
	        			year
	        		); // on crée le nouveau media
	        	
	        	if(fs.has("image") && !fs.isNull("image")) // il faut une image pour que le media soit ajouter
	        	{
		        	MediaList.getInstance().addSearchMedia(m); //on ajoute le media à la liste
	        	}
	        }
	        
	       for(Media m : res) // on ajoute les media restant a la liste
	       {
	    	   MediaList.getInstance().addSearchMedia(m);
	       }
	        
		}
	}
	
	
	public int getRateLimit() {
		return RateLimit;
	}


	public void setRateLimit(int rateLimit) {
		RateLimit = rateLimit >= 5 ? RateLimit : 5 ;
	}

	public static Api getInstance() {
        return instance;
    }
    
}


//https://xmdbapi.com/docs