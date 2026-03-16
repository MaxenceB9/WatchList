package rootManager;


import java.util.EnumMap;
import java.util.Map;

class Modal {
	
	private final Map<ModalType, String> index = new EnumMap<>(ModalType.class);
	
	Modal()
	{
		//screen
		index.put(ModalType.API_POPUP, "/fxml/popup/api_popup_init.fxml");
	}

	public String getPath(ModalType type) {
        return index.get(type);
    }
	
}
