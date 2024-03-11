module com.example.automataconverter {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.automataconverter to javafx.fxml;
    exports com.example.automataconverter;
}