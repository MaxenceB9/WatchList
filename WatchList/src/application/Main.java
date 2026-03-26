package application;

import api.Api;
import data.FileManager;
import data.MediaList;
import data.Settings;
import javafx.application.Application;
import javafx.stage.Stage;
import rootManager.Manager;
import rootManager.ModalType;

public class Main extends Application {

	
    @Override
    public void start(Stage primaryStage) throws Exception {
  
    	Settings.init();
    	
    	MediaList.init();
    	Api.init();
  
    	FileManager.init();
    	FileManager.getInstance().readFile("settings");
    	FileManager.getInstance().readFile("myMedia");
    	MediaList.getInstance().separateMedia();

    	Manager.init(primaryStage);  // on initialise le root manager

    	
    	if(Settings.getInstance().isApiKeyExist() == false)
    	{
    		Manager.getInstance().openModal(ModalType.API_POPUP, "API KEY");
    	}
    	
    	primaryStage.setTitle("Watchlist");
    }

    public static void main(String[] args) {
        launch(args);
    }
}