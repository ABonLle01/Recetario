module com.example.recetario {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens com.example.recetario to javafx.fxml;
    exports com.example.recetario;
}