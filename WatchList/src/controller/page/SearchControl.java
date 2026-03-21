package controller.page;

import java.io.IOException;
import java.util.List;

import api.Api;
import api.Media;
import api.MediaList;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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
	private Button searchButton;

	@FXML
	private Button backButton;

	@FXML
	public void initialize() {
		FlowPane.setPrefWrapLength(750);
		afficher();
	}

	public void search() {
		String query = searchBar.getText();
		if (Api.getInstance().getApi_key().isEmpty() || query.isEmpty()) {
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
			SearchPreview p = new SearchPreview(m, 400, 200);
			FlowPane.getChildren().add(p);
			FlowPane.setMargin(p, new Insets(20, 10, 10, 20));
		}

		results.addListener((javafx.collections.ListChangeListener<Media>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					for (Media newMedia : c.getAddedSubList()) {
						Platform.runLater(() -> {
							SearchPreview p = new SearchPreview(newMedia, 400, 200);
							FlowPane.getChildren().add(p);
							FlowPane.setMargin(p, new Insets(20, 10, 10, 20));
						});
					}
				}
			}
		});
	}

	public void goBack() {
		Manager.getInstance().setPage(PageType.HOME);
	}
}
