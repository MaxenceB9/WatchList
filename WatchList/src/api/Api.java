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

import retrofit2.*;
import rootManager.Manager;


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
		List<Media> res = new ArrayList<Media>();
		String encodedQuery = java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8);
		String url = String.format("https://xmdbapi.com/api/v1/search?q=%s&limit=%d&apiKey=%s", encodedQuery, this.RateLimit, this.api_key);
		
		try(HttpClient client = HttpClient.newHttpClient())
		{
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url.trim()))
	                .GET()
	                .build();
	        
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        
	        String body = response.body();
	        
	        JSONObject bodypars = new JSONObject(body);
	        JSONArray result = bodypars.getJSONArray("results");  
	        
	        
	        for(int i = 0; i < result.length(); i++)
	        {
	        	JSONObject fs = result.getJSONObject(i);
	        	
	        	Media m = new Media(
	        			fs.getString("id"),
	        			fs.getString("name"),
	        			fs.getString("description"),
	        			String.valueOf(fs.getInt("rank")),
	        			fs.getString("image"),
	        			String.valueOf(fs.getInt("year"))
	        		);
	        	
	        	MediaList.getInstance().addSearchMedia(m);
	        }

	        
	        
	       for(Media m : res)
	       {
	    	   MediaList.getInstance().addSearchMedia(m);
	       }
	        
		}
	}
	
	
	
	
	public static Api getInstance() {
        return instance;
    }
    
}


// https://xmdbapi.com/docs