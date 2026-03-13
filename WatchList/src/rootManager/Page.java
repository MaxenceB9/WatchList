package rootManager;


import java.util.EnumMap;
import java.util.Map;

class Page {
	
	private final Map<PageType, String> index = new EnumMap<>(PageType.class);
	
	Page()
	{
		index.put(PageType.HOME, "/screen/home.fxml");
	}

	public String getPath(PageType type) {
        return index.get(type);
    }
	
}
