package controller.popup;

import javafx.scene.control.TextField;
import rootManager.Manager;

import java.awt.Desktop;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import api.Api;
import data.FileManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

public class APIpopupControl {

	@FXML
	private TextField input;
	
	@FXML
	private Button save;
	
	@FXML
	private Hyperlink link;
	
	
	public void SaveClick()
	{
		Map<String, String> data = new HashMap<>();
		data.put("APIKEY", input.getText());
		
		
		Api.getInstance().setApi_key(input.getText());
		
		FileManager.getInstance().saveFile(data, "settings");
		
		if(!Api.getInstance().getApi_key().isEmpty())
		{
			Manager.closeModal();
		}
	
	}
	
	public void LinkClick()
	{
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		
		if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
		{
			try {
				desktop.browse(new URI("https://xmdbapi.com/api-key"));
			}catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	
}
