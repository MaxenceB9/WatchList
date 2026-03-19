package controller.page;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import rootManager.Manager;
import rootManager.PageType;
import javafx.application.Platform;
import java.io.IOException;
import java.util.List;
import api.Api;
import api.Media;
import api.MediaList;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import model.Preview;

public class HomeControl {
		
		@FXML 
		private FlowPane FlowPane;
		
		@FXML
		private TextField searchBar;
		
		@FXML
		private Button searchButton;
		
		@FXML
		public void initialize()
		{
			FlowPane.setPrefWrapLength(750);
			FlowPane.getChildren().clear();
			
			if(!MediaList.getInstance().getMyMedia().isEmpty())
			{
			    new Thread(() -> {		            
			           List<Media> results = MediaList.getInstance().getMyMedia();
			          
			           Platform.runLater(() -> {
			               for (Media m : results) {
			                   Preview p = new Preview(m, 130, 250);
		                    FlowPane.getChildren().add(p);
		                    FlowPane.setMargin(p, new Insets(20, 10, 10, 20));
		                }
		            });		        
			    }).start(); 
			}
		}
		
		
		public void search() {
		    String query = searchBar.getText();
		    if(!MediaList.getInstance().getSearchMedia().isEmpty())
		    {
		    	MediaList.getInstance().getSearchMedia().clear();
		    }
		    try {
				Api.getInstance().request(query);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		    
		    Manager.setPage(PageType.SEARCHPAGE);
		}
}
