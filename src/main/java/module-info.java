module com.example.upixelmaven {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;


    opens com.tejasbhovad.upixelapp to javafx.fxml;
    exports com.tejasbhovad.upixelapp;
}