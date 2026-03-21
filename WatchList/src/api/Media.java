package api;

import org.json.JSONObject;

public class Media {
	private String id;
	private String Title;
	private String Desc;
	private String plot;
	private String type;
	private String Score;
	private String PosterUri;
	private String year;
	public Media(String id, String title, String desc, String plot, String type, String score, String posterUri,
			String year) {
		super();
		this.id = id;
		Title = title;
		Desc = desc;
		this.plot = plot;
		this.type = type;
		Score = score;
		PosterUri = posterUri;
		this.year = year;
	}
	public String getId() {
		return id;
	}
	public String getTitle() {
		return Title;
	}
	public String getDesc() {
		return Desc;
	}
	public String getPlot() {
		return plot;
	}
	public String getType() {
		return type;
	}
	public String getScore() {
		return Score;
	}
	public String getPosterUri() {
		return PosterUri;
	}
	public String getYear() {
		return year;
	}
	@Override
	public String toString() {
		return "Media (id=" + id + ", Title=" + Title + ", Desc=" + Desc + ", plot=" + plot + ", type=" + type
				+ ", Score=" + Score + ", PosterUri=" + PosterUri + ", year=" + year + ")\n";
	}
	


	
}
