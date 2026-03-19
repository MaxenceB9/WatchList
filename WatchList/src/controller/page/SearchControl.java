package controller.page;

import java.io.IOException;
import java.util.List;

import api.Api;
import api.Media;
import api.MediaList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import model.SearchPreview;
import rootManager.Manager;
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
	public void initialize()
	{
		FlowPane.setPrefWrapLength(750);
		afficher();
	}
	
	
	public void search() {
	    String query = searchBar.getText();
	    MediaList.getInstance().clearSearchMedia();
	    try {
			Api.getInstance().request(query);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	    afficher();
	}
	
	private void afficher()
	{
		FlowPane.getChildren().clear();
		if(!MediaList.getInstance().getSearchMedia().isEmpty())
		{
		    new Thread(() -> {		            
		           List<Media> results = MediaList.getInstance().getSearchMedia();
		          
		           Platform.runLater(() -> {
		               for (Media m : results) {
		                   SearchPreview p = new SearchPreview(m, 400, 200);
	                    FlowPane.getChildren().add(p);
	                    FlowPane.setMargin(p, new Insets(20, 10, 10, 20));
	                }
	            });		        
		    }).start(); 
		}
	}
	
	
	public void goBack()
	{
		Manager.getInstance();
		Manager.setPage(PageType.HOME);
	}
}
