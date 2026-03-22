package controller.page;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import rootManager.Manager;
import rootManager.ModalType;
import rootManager.PageType;
import javafx.application.Platform;
import java.io.IOException;
import java.util.List;

import javax.swing.SwingUtilities;

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
		private MenuItem settings;
		
		@FXML
		public void initialize()
		{
			FlowPane.setPrefWrapLength(750);
			
			afficher(MediaList.getInstance().getMyMedia());
			
			separateMedia();
		}
		
		
		public void search() {
		    String query = searchBar.getText();
		    if(Api.getInstance().getApi_key() == null || query.isEmpty())
		    {
		    	try {
					Manager.getInstance().openModal(ModalType.API_POPUP, "API KEY");
				} catch (IOException e) {
					e.printStackTrace();
				}
		    	return;
		    }
		    else
		    {
		    	if(!MediaList.getInstance().getSearchMedia().isEmpty())
			    {
			    	MediaList.getInstance().getSearchMedia().clear();
			    }

			    Api.getInstance().request(query).thenRun(()->{
			    	Platform.runLater(()->{
			    		Manager.getInstance().setPage(PageType.SEARCHPAGE);
			    	});
			    });
		    }
		    
		}
		
		
		public void openSettings() throws IOException
		{
			Manager.getInstance().openModal(ModalType.SETTINGS, "Settings");
		}
		
		
		
		private void separateMedia() //trier les media entre Film et Serie
		{
			List<Media> med = MediaList.getInstance().getMyMedia();
			
			if(!med.isEmpty() || med == null)
			{
				for(Media m : med)
				{
					if(m.getType().equals("Movie"))
					{
						MediaList.getInstance().setMovieMedia(m);
					}
					else
					{
						MediaList.getInstance().setTvMedia(m);
					}
				}
			}
		}
		
		
		public void afficher(List<Media> data)
		{
			FlowPane.getChildren().clear();
			if(!data.isEmpty())
			{
			    new Thread(() -> {		            			          
			           Platform.runLater(() -> {
			               for (Media m : data) {
			                   Preview p = new Preview(m, 130, 250);
		                    FlowPane.getChildren().add(p);
		                    FlowPane.setMargin(p, new Insets(20, 10, 10, 20));
		                }
		            });		        
			    }).start(); 
			}
		}
		
		public void showAll()
		{
			List<Media> all = MediaList.getInstance().getMyMedia();
			if(all.isEmpty() || all.equals(null))
			{
				return;
			}
			afficher(all);
		}
		
		public void showMovie()
		{
			List<Media> movie = MediaList.getInstance().getMovieMedia();
			if(MediaList.getInstance().getMyMedia().isEmpty() || MediaList.getInstance().getMyMedia().equals(null))
			{
				return;
			}
			if(movie.isEmpty() || movie.equals(null))
			{
				separateMedia();
			}
			afficher(movie);
		}
		public void showSerie()
		{
			List<Media> serie = MediaList.getInstance().getTvMedia();
			if(MediaList.getInstance().getMyMedia().isEmpty() || MediaList.getInstance().getMyMedia().equals(null))
			{
				return;
			}
			if(serie.isEmpty() || serie.equals(null))
			{
				separateMedia();
			}
			
			afficher(serie);
		}
}
