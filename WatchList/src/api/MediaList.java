package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaList {	
	private static MediaList instance;
	
	private List<Media> searchMedia = new ArrayList<Media>();
	private List<Media> myMedia = new ArrayList<Media>();

	
	public static void init()
	{
		instance = new MediaList();	
	}	
	
	public List<Media> getSearchMedia() {
		return searchMedia;
	}

	public void clearSearchMedia()
	{
		searchMedia.clear();
	}

	public List<Media> getMyMedia() {
		return myMedia;
	}
	
	public void clearMyMedia()
	{
		myMedia.clear();
	}
	public void addSearchMedia(Media m)
	{
		searchMedia.add(m);
	}

	public void addMyMedia(Media m)
	{
		myMedia.add(m);
	}

	public static MediaList getInstance()
	{
		return instance;
	}	
	
	@Override
	public String toString() {
		return "MediaList (myMedia=" + myMedia + ")\n";
	}

	public Map<String, String> toMap() // convertion de la liste mymedia en Map java
	{
		Map<String,String> data = new HashMap<String, String>();
		
		
		for(Media entity : this.getMyMedia())
		{
			data.put("MEDIA-" + entity.getId(), entity.toString());
		}
		
		return data;
	}
}
