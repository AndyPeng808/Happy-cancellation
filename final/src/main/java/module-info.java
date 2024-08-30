module com.example.final {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.final to javafx.fxml;
    exports com.example.final;
}