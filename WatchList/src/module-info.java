module WatchList {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires retrofit2;
    requires java.desktop;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens Controller.page to javafx.fxml;
    opens Controller.popup to javafx.fxml;
    opens application to javafx.graphics, javafx.fxml;

    exports Controller.page;
    exports Controller.popup;
    exports application;
}