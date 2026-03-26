package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MediaList {	
	private static MediaList instance;
	
	private ObservableList<Media> searchMedia = FXCollections.observableArrayList();
	private List<Media> myMedia = new ArrayList<Media>();
	private List<Media> tvMedia = new ArrayList<Media>();
	private List<Media> movieMedia = new ArrayList<Media>();
	private List<Media> temp = new ArrayList<Media>();
	
	
	public static void init()
	{
		instance = new MediaList();	
	}	
	
	public ObservableList<Media> getSearchMedia() {
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
	
	
	public boolean contain(List<Media> data, Media media)
	{
		for(Media m : data)
		{
			if(m.getId() == media.getId())
			{
				return true;
			}
		}
		
		
		return false;
	}
	
	
	public List<Media> getTvMedia() {
		return tvMedia;
	}

	public void setTvMedia(Media tvMedia) {
		this.tvMedia.add(tvMedia);
	}

	public List<Media> getMovieMedia() {
		return movieMedia;
	}

	public void setMovieMedia(Media movieMedia) {
		this.movieMedia.add(movieMedia);
	}
	
	
	
	public List<Media> getTempMedia() {
		return temp;
	}

	public void setTempMedia(List<Media> temp) {
		this.temp = temp;
	}
	

	public void separateMedia() //trier les media entre Film et Serie
	{
		List<Media> med = myMedia;
		
		
		if(!med.isEmpty() || med != null)
		{
			this.movieMedia.clear();
			this.tvMedia.clear();
			
			for(Media m : med)
			{
				if(m.getType().equals("Movie"))
				{
					MediaList.getInstance().setMovieMedia(m);
				}
				else
				{
					MediaList.getInstance().setTvMedia(m);
				}
			}
		}
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
