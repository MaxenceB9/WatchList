package data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import api.Api;

public class FileManager {
	private static FileManager instance;

	private static final String DEFAULT_PATH = System.getProperty("user.home") + File.separator + "Documents"
			+ File.separator + "WatchListAPP";

	private static String path;

	public static void init() {
		instance = new FileManager(DEFAULT_PATH);
	}

	private FileManager(String path) {
		this.path = path;
		pathVerify();
	}

	public void readFile(String filename) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> data = new HashMap<>();

		try {
			File file = new File(path + File.separator + filename + ".json");
			if (!file.exists())
				return;

			data = mapper.readValue(file, new TypeReference<Map<String, String>>() {});

			switch (filename) {
			case "settings":
				Settings.getInstance().setApiKey(data.get("ApiKey"));
				Settings.getInstance().setLang(data.get("Lang"));
				Settings.getInstance().setSavePoster(data.get("SavePoster") == "false" ? false : true);
				break;
			case "myMedia":
				for (Map.Entry<String, String> m : data.entrySet()) {
					String value = m.getValue();

					String id = value.split("id=")[1].split(",")[0];
					String title = value.split("Title=")[1].split(",")[0];
					String desc = value.split("Desc=")[1].split(",")[0];
					String plot = value.split("plot=")[1].split(",")[0];
					String type = value.split("type=")[1].split(",")[0];
					String score = value.split("Score=")[1].split(",")[0];
					;
					String uri = value.split("PosterUri=")[1].split(",")[0];
					String year = value.split("year=")[1].replace(")", "").trim();
					;

					Media media = new Media(id, title, desc, plot, type, score, uri, year);

					MediaList.getInstance().addMyMedia(media);
				}
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void saveFile(Map<String, String> data, String filename) // a recoder
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);
		try {
			File file = new File(path + File.separator + filename + ".json");

			mapper.writeValue(file, data);

		} catch (IOException e) {
			System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
		}
	}

	public void update(Map<String, String> data, String filename) {
		File file = new File(path + File.separator + filename + ".json");
		if (!file.exists()) {
			saveFile(data, filename);
			return;
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> newData = new HashMap<>();
		try {
			newData = mapper.readValue(file, new TypeReference<Map<String, String>>() {});
			newData.putAll(data);
			
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, newData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void pathVerify() {
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
		}
	}

	public String DownloadPoster(String url, String ID) throws MalformedURLException
	{
		String filepath = path + File.separator + "poster" + File.separator + ID +"_poster.png";
		
		URL addr = new URL(url);
		
		try (InputStream start = addr.openStream()){
			File file = new File(filepath);
			file.getParentFile().mkdirs();
			
			try (FileOutputStream end = new FileOutputStream(file)){
				byte[] buffer = new byte[1024];
				int size;
				
				while((size = start.read(buffer)) != -1)
				{
					end.write(buffer, 0, size);
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filepath;
	}
	
	public static FileManager getInstance() {
		return instance;
	}
}
