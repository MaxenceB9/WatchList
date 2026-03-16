package application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.Api;
import api.Media;
import api.MediaList;
import data.FileManager;
import javafx.application.Application;
import javafx.stage.Stage;
import rootManager.Manager;
import rootManager.ModalType;
import rootManager.PageType;

public class Main extends Application {

	
    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	Manager.init(primaryStage);  // on initialise le root manager
    	FileManager.init();
    	Api.init();
    	MediaList.init();
   
    	FileManager.readSettings();
    
    	if(!Api.getInstance().isApiKeyExist())
    	{
    		Manager.openModal(ModalType.API_POPUP, "API KEY");
    	}
    	
    	primaryStage.setTitle("Watchlist");

    }

    public static void main(String[] args) {
        launch(args);
    }
}