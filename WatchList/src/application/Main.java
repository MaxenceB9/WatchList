package application;

import api.Api;
import api.MediaList;
import data.FileManager;
import javafx.application.Application;
import javafx.stage.Stage;
import rootManager.Manager;
import rootManager.ModalType;

public class Main extends Application {

	
    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	MediaList.init();
    	
    	Api.init();

    	FileManager.init();
    	FileManager.getInstance().readFile("settings");
    	FileManager.getInstance().readFile("myMedia");
    	
    	Manager.init(primaryStage);  // on initialise le root manager

    	
    	if(!Api.getInstance().isApiKeyExist())
    	{
    		Manager.getInstance().openModal(ModalType.API_POPUP, "API KEY");
    	}
    	
    	primaryStage.setTitle("Watchlist");

    }

    public static void main(String[] args) {
        launch(args);
    }
}