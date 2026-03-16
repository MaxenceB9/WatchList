package api;

import java.util.ArrayList;
import java.util.List;

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



	public List<Media> getMyMedia() {
		return myMedia;
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
}
