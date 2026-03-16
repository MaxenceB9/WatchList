package api;

public class Media {
	private String id;
	private String Title;
	private String Desc;
	private String Score;
	private String PosterUri;
	private String year;
	
	
	public Media(String id, String title, String desc, String score, String posterUri, String year) {
		this.id = id;
		Title = title;
		Desc = desc;
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
		return "Media (id=" + id + ", Title=" + Title + ", Desc=" + Desc + ", Score=" + Score + ", PosterUri="
				+ PosterUri + ", year=" + year + ")\n";
	}
	
	
	
}
