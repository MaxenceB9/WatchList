package data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import api.Api;
import api.Media;
import api.MediaList;


public class FileManager {
	private static FileManager instance;
	
	private static final String DEFAULT_PATH = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "WatchListAPP";
	
	private static String path;
	
	public static void init()
	{
		instance = new FileManager(DEFAULT_PATH);
	}
	
	private FileManager(String path)
	{
		this.path = path;
		pathVerify();
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
	
	public void readFile(String filename)
	{
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> data = new HashMap<>();		
		
		try {
			File file = new File(path + File.separator + filename +".json");
			if(!file.exists()) return;
			
			data = mapper.readValue(file, new TypeReference<Map<String, String>>(){});
			
			switch(filename)
			{
			case "settings":
				Api.getInstance().setApi_key(data.get("APIKEY"));
				break;
			case "myMedia":
			
				for(Map.Entry<String, String> m : data.entrySet())
				{
					String value = m.getValue();
					
					String id = value.split("id=")[1].split(",")[0];
					String title = value.split("Title=")[1].split(",")[0];
					String desc = value.split("Desc=")[1].split(",")[0];
					String plot = value.split("plot=")[1].split(",")[0];
					String type = value.split("type=")[1].split(",")[0];
					String score = value.split("Score=")[1].split(",")[0];;
					String uri = value.split("PosterUri=")[1].split(",")[0];
					String year = value.split("year=")[1].replace(")", "").trim();;
					
					Media media = new Media(id,title,desc,plot, type,score,uri,year);
					
					MediaList.getInstance().addMyMedia(media);
				}
				break;
			}
			
			
		}catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}
	
	public void update(Map<String, String> data, String filename) {
		File file = new File(path + File.separator + filename + ".json");
		if(!file.exists()) {
			saveFile(data,filename);
			return;
		}
		
		String content = "";
		
		Map<String, String> newData = new HashMap<>();
		
	    try {            
	        content = Files.readString(file.toPath());
	        JSONObject jo = new JSONObject(content);
	        
	        for(String key : jo.keySet())
	        {
	        	newData.put(key, jo.optString(key));
	        }
			
	        for(Map.Entry<String, String> entry : data.entrySet()){
	        	if(!newData.containsKey(entry.getKey()))
	        	{
	        		newData.put(entry.getKey(), entry.getValue());
	        	}
	        }
	        
			saveFile(newData, filename);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	private void pathVerify()
	{
		File folder = new File(path);
		if(!folder.exists())
		{
			folder.mkdir();
		}
	}
	
	public static FileManager getInstance() {
        return instance;
    }
}
