package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import rootManager.Manager;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

	
    @Override
    public void start(Stage primaryStage) throws Exception {
    	Manager.init(primaryStage);  // on initialise le root manager
        
        primaryStage.setTitle("Watchlist");
    }

    public static void main(String[] args) {
        launch(args);
    }
}