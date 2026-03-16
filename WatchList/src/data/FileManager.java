package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import api.Api;


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
	
	public void saveFile(Map<String, String> data, String filename)
	{
		JSONObject jo = new JSONObject();
		for(Map.Entry<String, String> entry : data.entrySet())
		{
			jo.put(entry.getKey(), entry.getValue());
		}
		
		try {
			File file = new File(path + File.separator + filename + ".json");
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(jo.toString());
			
			bw.close();
			fw.close();
			
		}catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public static void readSettings() {
	    File file = new File(path + File.separator + "settings.json");
	    if (!file.exists()) return;

	    try {            
	        String content = Files.readString(file.toPath());
	        JSONObject jo = new JSONObject(content);
	        
	        if (jo.has("APIKEY")) {
	            Api.getInstance().setApi_key(jo.getString("APIKEY"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	//public void update(Map<String, String> data, String filename) {}
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
