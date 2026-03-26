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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import rootManager.Manager;
import rootManager.ModalType;

public class SETTINGSpopupControl {

	
	@FXML
	private FlowPane content;
	
	@FXML
	private Button ApiButton, General;
	
	
	private String languageSelected;
	
	@FXML
	private MenuButton langSelecter;

	@FXML
	private CheckBox savePoster;
	
	@FXML
	public void initialize()
	{
		langSelecter.setText(Settings.getInstance().getLang());

		savePoster.setSelected(Settings.getInstance().isSavePoster());
		
		loadLanguageSelecter();
	}
	
	private void loadLanguageSelecter()
	{
		langSelecter.getItems().clear();
		for(String l : Settings.getInstance().getLangList())
		{
			if(!l.equals(langSelecter.getText()))
			{
				MenuItem it = new MenuItem(l);
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
		data.put("Lang", langSelecter.getText());
		data.put("SavePoster", String.format("%s", Settings.getInstance().isSavePoster()));

		FileManager.getInstance().saveFile(data, "settings");
	}
	
}
