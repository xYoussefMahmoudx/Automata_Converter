module com.example.automata_converter {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.automata_converter to javafx.fxml;
    exports com.example.automata_converter;
}