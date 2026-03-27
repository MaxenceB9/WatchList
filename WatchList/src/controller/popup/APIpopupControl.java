package controller.popup;

import javafx.scene.control.TextField;
import rootManager.Manager;

import java.awt.Desktop;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import api.Api;
import data.FileManager;
import data.Settings;
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
		data.put("ApiKey", input.getText());
		data.put("ApiRate", String.format("%d", Settings.getInstance().getApiRateLimit()));
		data.put("Lang", Settings.getInstance().getLang());
		data.put("SavePoster", String.format("%s", Settings.getInstance().isSavePoster()));

		Settings.getInstance().setApiKey(input.getText());
		
		FileManager.getInstance().saveFile(data, "settings");
		
		
		Manager.closeModal();
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
