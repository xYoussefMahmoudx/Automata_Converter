module com.example.automata_converter {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.automataconverter to javafx.fxml;
    exports com.example.automataconverter;
    exports callbackinterfaces;
    opens callbackinterfaces to javafx.fxml;
}