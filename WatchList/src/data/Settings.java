package data;

public class Settings {
	private static Settings instance;
	
	private String langList[] = {"English"};
	
	private final String LANGDEFAULT = langList[0];
	private String Lang;
	private String ApiKey;
	private String theme = "LIGHT";
	private boolean SavePoster = false;
	private int ApiRate = 5;
	private int MaxApiRate = 20;
	
	public static void init()
	{
		if(instance == null)
		{
			instance = new Settings();
		}
	}
	
	private Settings() {
		this.Lang = LANGDEFAULT;
	}

	
	
	public String getLang() {
		return Lang;
	}

	public void setLang(String lang) {
		Lang = lang;
	}

	public String[] getLangList() {
		return langList;
	}

	public String getApiKey() {
		return ApiKey;
	}
	public boolean isApiKeyExist()
	{
		return ApiKey != null && !ApiKey.trim().isEmpty(); 
	}
	public void setApiKey(String apiKey) {
		this.ApiKey = apiKey;
	}
	
	public boolean isSavePoster() {
		return SavePoster;
	}

	public void setSavePoster(boolean savePoster) {
		SavePoster = savePoster;
	}

	public int getApiRateLimit() {
		return this.ApiRate;
	}

	public void setApiRateLimit(int rateLimit) {
		if(rateLimit >= 5 && rateLimit <= 20)
		{
			this.ApiRate = rateLimit;
		}
		else if(rateLimit < 5)
		{
			this.ApiRate = 5;
		}
		else if(rateLimit > getMaxApiRate())
		{
			rateLimit = getMaxApiRate();
		}
	}
	
	public int getMaxApiRate() {
		return MaxApiRate;
	}

	
	
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public static Settings getInstance()
	{
		if(instance == null)
		{
			init();
			return instance;
		}
		return instance;
	}
}
