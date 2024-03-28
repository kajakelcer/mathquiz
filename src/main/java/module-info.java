module com.example.mathapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.mathapp to javafx.fxml;
    exports com.example.mathapp;
}