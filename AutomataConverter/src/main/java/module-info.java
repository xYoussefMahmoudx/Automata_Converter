module com.example.automata_converter {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.automataconverter to javafx.fxml;
    exports com.example.automataconverter;
    exports callbackinterfaces;
    opens callbackinterfaces to javafx.fxml;
}