package data;

public class Settings {
	private static Settings instance;
	
	private String langList[] = {"English", "French"};
	
	private final String LANGDEFAULT = langList[0];
	private String Lang;
	private String ApiKey;
	private boolean SavePoster = false;
	
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
