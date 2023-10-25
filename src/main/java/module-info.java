module com.example.recetario {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires javafx.media;

    opens com.example.recetario.img;
    opens com.example.recetario.audio;
    opens com.example.recetario to javafx.fxml;
    exports com.example.recetario;
}