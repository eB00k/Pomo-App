module com.example.pomo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.pomo to javafx.fxml;
    exports com.example.pomo;
}