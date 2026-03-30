package Controller.page;

import java.io.IOException;
import java.util.List;

import api.Api;
import data.Media;
import data.MediaList;
import data.Settings;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import model.SearchPreview;
import rootManager.Manager;
import rootManager.ModalType;
import rootManager.PageType;

public class SearchControl {
	@FXML
	private FlowPane FlowPane;

	@FXML
	private TextField searchBar;

	@FXML
	private Button backButton, searchButton;
	
	@FXML
	private MenuItem settings;

	@FXML
	private CheckBox movieFilter, seriesFilter;
	
	@FXML
	public void initialize() {
		FlowPane.setPrefWrapLength(750);
		afficher();
	}

	public void search() {
		String query = searchBar.getText();
		if (Settings.getInstance().getApiKey().isEmpty() || query.isEmpty()) {
	    	try {
				Manager.getInstance().openModal(ModalType.API_POPUP, "API KEY");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		else
		{
			if (!MediaList.getInstance().getSearchMedia().isEmpty()) {
				MediaList.getInstance().getSearchMedia().clear();
			}

			Api.getInstance().request(query).thenRun(() -> {
				Platform.runLater(() -> {
					afficher();
				});
			});
		}
		
	}

	private void afficher() {
		FlowPane.getChildren().clear();

		ObservableList<Media> results = MediaList.getInstance().getSearchMedia();
		for (Media m : results) {
			SearchPreview p = new SearchPreview(m, 800, 200);
			FlowPane.getChildren().add(p);
			FlowPane.setMargin(p, new Insets(20, 10, 10, 20));
		}

		results.addListener((javafx.collections.ListChangeListener<Media>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					for (Media newMedia : c.getAddedSubList()) {
						Platform.runLater(() -> {
							SearchPreview p = new SearchPreview(newMedia, 800, 200);
							FlowPane.getChildren().add(p);
							FlowPane.setMargin(p, new Insets(20, 10, 10, 20));
						});
					}
				}
			}
		});
	}

	public void openSettings() throws IOException
	{
		Manager.getInstance().openModal(ModalType.SETTINGS, "Settings");
	}
	
	public void goBack() {
		Manager.getInstance().setPage(PageType.HOME);
	}
}
