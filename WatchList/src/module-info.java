module WatchList {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.json;
	requires trakt.java;
	requires retrofit2;
	requires java.desktop;
	requires java.net.http;
	
	
	opens controller.page to javafx.fxml;
	opens controller.popup to javafx.fxml;

	opens application to javafx.graphics, javafx.fxml;
}
