package controller.popup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import api.Api;
import data.FileManager;
import data.MediaList;
import data.Settings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import rootManager.Manager;
import rootManager.ModalType;
import rootManager.Theme;
import rootManager.ThemeList;

public class SETTINGSpopupControl {

	@FXML
	private FlowPane content;
	
	@FXML
	private Button ApiButton, General;
	
	
	private String languageSelected;
	
	@FXML
	private MenuButton langSelecter, themeSelecter;

	@FXML
	private CheckBox savePoster;
	
	@FXML
	private Slider RateSlider;
	@FXML
	private Label RateLabel;
	
	@FXML
	public void initialize()
	{
		langSelecter.setText(Settings.getInstance().getLang());
		themeSelecter.setText(Settings.getInstance().getTheme());
		
		savePoster.setSelected(Settings.getInstance().isSavePoster());
		
		loadLanguageSelecter();
		loadThemeSelecter();
		loadRateSelecter();
	}
	
	private void loadLanguageSelecter()
	{
		langSelecter.getItems().clear();
		for(String l : Settings.getInstance().getLangList())
		{
			if(!l.equals(langSelecter.getText()))
			{
				MenuItem it = new MenuItem(l.toLowerCase());
				it.setOnAction(event ->{
					String selected = it.getText();
					languageSelected = selected;
					langSelecter.setText(selected);
					loadLanguageSelecter();
				});
				langSelecter.getItems().add(it);
			}
		}
	}
	private void loadThemeSelecter() {
		themeSelecter.getItems().clear();
	    for (ThemeList t : ThemeList.values()) {
	        if (!t.name().equalsIgnoreCase(themeSelecter.getText())) {       
	            MenuItem it = new MenuItem(t.name());  
	            it.setOnAction(event -> {
	            	themeSelecter.setText(it.getText().toUpperCase());
	                loadThemeSelecter();
	            });
	            
	            themeSelecter.getItems().add(it);
	        }
	    }
	}
	
	private void loadRateSelecter()
	{
		RateSlider.valueProperty().setValue((double) Settings.getInstance().getApiRateLimit());
		RateLabel.setText("Rate value: " + (int) RateSlider.getValue());
		RateSlider.setMax(Settings.getInstance().getMaxApiRate());
		RateSlider.valueProperty().addListener((observable, oldValue, newValue) ->{
			int value = newValue.intValue();
			RateLabel.setText("Rate value: " + value);
			Settings.getInstance().setApiRateLimit(value);
		});
	}
	
	@FXML
	public void ApiContent()
	{
		try {
			Manager.getInstance().openModal(ModalType.API_POPUP, "API KEY");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void checkSavePoster()
	{
		Settings.getInstance().setSavePoster(savePoster.isSelected());
	}
	
	public void SaveData()
	{
		Map<String, String> data = new HashMap<>();
		data.put("ApiKey", Settings.getInstance().getApiKey());
		data.put("ApiRate", String.valueOf(Settings.getInstance().getApiRateLimit()));
		data.put("Lang", langSelecter.getText());
		//appliquer la nouvel langue.
		data.put("Theme", themeSelecter.getText());
		for (ThemeList t : ThemeList.values()) { //appliquer le nouveau theme à la sauvegarde.
	        if(t.name().equalsIgnoreCase(themeSelecter.getText()))
	        {
	        	Manager.getInstance().setTheme(t);
	        	break;
	        }
	    }
		
		data.put("SavePoster", String.format("%s", Settings.getInstance().isSavePoster()));
		FileManager.getInstance().saveFile(data, "settings");
	}
	
}
