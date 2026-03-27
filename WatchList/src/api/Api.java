package api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import data.Media;
import data.MediaList;
import data.Settings;

public class Api {
	private static Api instance;

	public static void init() {
		instance = new Api();
	}

	public CompletableFuture<Void> request(String query) { // la requette est asynchrone
		return CompletableFuture.runAsync(()->{
			List<Media> res = new ArrayList<Media>(); // création d'une liste pour stocker les Media reçu.

			String encodedQuery = java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8);
			String url = String.format("https://xmdbapi.com/api/v1/search?q=%s&limit=%d&apiKey=%s", encodedQuery,
						Settings.getInstance().getApiRateLimit(), Settings.getInstance().getApiKey());

			try (HttpClient client = HttpClient.newHttpClient()) // on crée un client http
			{
				HttpRequest request = HttpRequest.newBuilder() // requette http
						.uri(URI.create(url.trim())).GET().build();

				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // reponse

				String body = response.body(); // on recupère le corp de la réponse

				JSONObject bodypars = new JSONObject(body); // on transofrme le string en objet json
				JSONArray result = bodypars.getJSONArray("results"); // on transform l'objet en tableau
				new Thread(() -> {
					for (int i = 0; i < result.length(); i++) // on crée les media en fonction du res
					{
						JSONObject fs = result.getJSONObject(i);

						String year = "not found";
						String image = "";

						if (fs.has("year")) // on recup l'année
						{
							year = String.valueOf(fs.getInt("year"));
						}
						if (fs.has("image") && !fs.isNull("image")) // on récup l'image
						{
							image = fs.getString("image");
						}

						List<String> movie = null;
						movie = getMoreData_Movie(fs.getString("id"));

						List<String> serie = null;
						serie = getMoreData_Serie(fs.getString("id"));
						

						Media m = new Media(
								fs.getString("id"),
								fs.getString("name"),
								fs.getString("description"),
								movie.isEmpty() ? serie.get(1) : movie.get(1),
								movie.isEmpty() ? serie.get(0) : movie.get(0),
								String.valueOf(fs.getInt("rank")),
								image,
								year.isEmpty() ? "0000" : year
							); // on crée le nouveau media
						
						if (fs.has("image") && !fs.isNull("image")) // il faut une image pour que le media soit ajouter
						{
							MediaList.getInstance().addSearchMedia(m); // on ajoute le media à la liste
						}
					}
				}).start();
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (Media m : res) // on ajoute les media restant a la liste
			{
				MediaList.getInstance().addSearchMedia(m);
			}
		});
	}
	

	private List<String> getMoreData_Movie(String query){
		List<String> res = new ArrayList<>();
		// res[0] type et res[1} plot
		String encodedQuery = java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8);
		String url = String.format("https://xmdbapi.com/api/v1/movies/%s?apiKey=%s", encodedQuery, Settings.getInstance().getApiKey());
		try (HttpClient client = HttpClient.newHttpClient()) // on crée un client http
		{
			HttpRequest request = HttpRequest.newBuilder() // requette http
					.uri(URI.create(url.trim())).GET().build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // reponse

			String body = response.body();

			JSONObject bodypars = new JSONObject(body);

			String type = bodypars.optString("title_type", "not found");
			res.add(type);
			String plot = bodypars.optString("plot", "not found");
			res.add(plot);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			return res;
		}
		return res;
	}

	private List<String> getMoreData_Serie(String query) {
		List<String> res = new ArrayList<>();

		String encodedQuery = java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8);
		String url = String.format("https://xmdbapi.com/api/v1/seasons/%s?apiKey=%s", encodedQuery, Settings.getInstance().getApiKey());
		try (HttpClient client = HttpClient.newHttpClient()) // on crée un client http
		{
			HttpRequest request = HttpRequest.newBuilder() // requette http
					.uri(URI.create(url.trim())).GET().build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // reponse

			String body = response.body();

			JSONObject bodypars = new JSONObject(body);

			String type = bodypars.optString("type", "not found");
			res.add(type);
			String plot = "not found";
			res.add(plot);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			return res;
		}

		return res;
	}

	public static Api getInstance() {
		return instance;
	}

}

//https://xmdbapi.com/docs