package com.example.recetario;

import com.example.recetario.models.Receta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ResourceBundle;

public class VentanaSecundariaControler implements Initializable {
    @javafx.fxml.FXML
    private Button btnVolver;
    @javafx.fxml.FXML
    private Button btnActualizar;
    @javafx.fxml.FXML
    private TextField txtNombre;
    @javafx.fxml.FXML
    private ComboBox<String> comboDificultad;
    @javafx.fxml.FXML
    private ComboBox<String> comboTipo;
    @javafx.fxml.FXML
    private Spinner<Integer> spDuracion;

    private Receta recetaActual;
    @javafx.fxml.FXML
    private Button btnBorrar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recetaActual = Session.getRecetaActual();
        //info.setText(r.toString());
        spDuracion.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,180,recetaActual.getDuracion(),10));

        comboDificultad.getItems().addAll("Fácil","Moderada","Difícil");
        comboDificultad.setValue(recetaActual.getDificultad());

        comboTipo.getItems().addAll("Desayuno","Segundo desayuno","Almuerzo","Sobrealmurezo","Merienda","Cena","Recena","Postcena");
        comboTipo.setValue(recetaActual.getTipo());

        txtNombre.setText(recetaActual.getNombre());



    }

    @javafx.fxml.FXML
    public void actualizar(ActionEvent actionEvent) {
        recetaActual.setNombre(txtNombre.getText());
        recetaActual.setTipo(comboTipo.getValue());
        recetaActual.setDificultad(comboDificultad.getValue());
        recetaActual.setDuracion(spDuracion.getValue());

        Session.setRecetaActual(recetaActual);
        Session.getRecetas().set(Session.getPos(),Session.getRecetaActual());

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText("Cambio realizado");
        alerta.showAndWait();
        Session.setPos(null);

        HelloApplication.loadFXML("ventanaPrincipal.fxml");
    }

    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
        HelloApplication.loadFXML("ventanaPrincipal.fxml");
    }

    @javafx.fxml.FXML
    public void borrarTarea(ActionEvent actionEvent) {

        Session.getRecetas().remove((int)Session.getPos());
        Session.setRecetaActual(null);

        HelloApplication.loadFXML("ventanaPrincipal.fxml");
    }
}