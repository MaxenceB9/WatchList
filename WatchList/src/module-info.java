module WatchList {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens Controller to javafx.fxml;
	opens application to javafx.graphics, javafx.fxml;
}
