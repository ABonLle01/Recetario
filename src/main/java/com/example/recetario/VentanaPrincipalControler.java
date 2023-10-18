package com.example.recetario;

import com.example.recetario.models.Receta;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


public class VentanaPrincipalControler implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private Label welcomeText1;
    @FXML
    private TextField txtNombre;
    @FXML
    private Label labelDuracion;
    @FXML
    private Slider sliderDuracion;
    @FXML
    private ComboBox<String> comboDificultad;
    @FXML
    private ListView<String> listTipo;
    @FXML
    private Button btnAñadir;
    @FXML
    private TableView<Receta> tabla;
    @FXML
    private TableColumn<Receta,String> cNombre;
    @FXML
    private TableColumn<Receta,String> cDuracion;
    @FXML
    private TableColumn<Receta,String> cDificultad;
    @FXML
    private TableColumn<Receta,String> cTipo;
    @FXML
    private Label info;


    @FXML
    public void insertarReceta(ActionEvent actionEvent) {

        if(!txtNombre.getText().isEmpty()){
            Receta receta = new Receta();
            receta.setNombre(txtNombre.getText());
            receta.setTipo(listTipo.getSelectionModel().getSelectedItem());
            receta.setDuracion((int) sliderDuracion.getValue());
            receta.setDificultad(comboDificultad.getSelectionModel().getSelectedItem());
            tabla.getItems().add(receta);
            info.setText(receta.toString());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboDificultad.getItems().add("Fácil");
        comboDificultad.getItems().add("Moderada");
        comboDificultad.getItems().add("Difícil");

        //comboDificultad.getItems().addAll("Fácil","Medio","Dificil");


        /*
        ObservableList<String> datos = FXCollections.observableArrayList();
        datos.addAll("","");
        comboDificultad.setItems(datos);
        */

        comboDificultad.getSelectionModel().selectFirst();

        sliderDuracion.setValue(60);
        labelDuracion.setText(Math.round(sliderDuracion.getValue()) + " min");

        listTipo.getItems().addAll("Desayuno","Segundo desayuno","Almuerzo","Sobrealmurezo","Merienda","Cena","Recena","Postcena");
        listTipo.getSelectionModel().selectFirst();


        //cuando localizo una propiedad, le puedo añadir un listener
        sliderDuracion.valueProperty().addListener((observableValue, number, t1) -> labelDuracion.setText(t1.intValue() + " min"));

        //txtNombre.textProperty().addListener((ob,vold,vnew)->info.setText("Antiguo: "+vold+" Nuevo: "+vnew));

        //cuando se hace click en una fila de la tabla
        tabla.getSelectionModel().selectedItemProperty().addListener(
                (observable, vOld, vNew)->{
                    info.setText(vNew.toString());
                    txtNombre.setText(vNew.getNombre());
                    sliderDuracion.setValue(vNew.getDuracion());
                    listTipo.getSelectionModel().select(vNew.getTipo());
                    comboDificultad.getSelectionModel().select(vNew.getDificultad());
                }
        );

        cNombre.setCellValueFactory((fila)-> {
            String nombre = fila.getValue().getNombre();
            return new SimpleStringProperty(nombre);
        } );

        cDificultad.setCellValueFactory((fila)-> new SimpleStringProperty(fila.getValue().getDificultad()));

        cDuracion.setCellValueFactory((fila)-> {
            String duracion = fila.getValue().getDuracion().toString()+" min";
            return new SimpleStringProperty(duracion);
        });

        cTipo.setCellValueFactory((fila)-> new SimpleStringProperty(fila.getValue().getTipo()));

        tabla.getItems().add(new Receta("Tacos de carne asada", "Almuerzo", 45, "Fácil"));
        tabla.getItems().add(new Receta("Huevos revueltos con tocino", "Desayuno", 15, "Moderada"));
        tabla.getItems().add(new Receta("Sándwich de jamón y queso", "Merienda", 10, "Fácil"));
        tabla.getItems().add(new Receta("Pollo a la parrilla con verduras", "Almuerzo", 60, "Moderada"));
        tabla.getItems().add(new Receta("Avena con frutas", "Desayuno", 20, "Fácil"));
        tabla.getItems().add(new Receta("Ensalada de atún", "Almuerzo", 30, "Moderada"));
        tabla.getItems().add(new Receta("Pizza casera", "Cena", 35, "Moderada"));
        tabla.getItems().add(new Receta("Batido de frutas", "Merienda", 5, "Fácil"));
        tabla.getItems().add(new Receta("Sopa de pollo casera", "Cena", 40, "Difícil"));
        tabla.getItems().add(new Receta("Pancakes con sirope de arce", "Desayuno", 25, "Moderada"));

    }

    @FXML
    public void actualizarDuracion(Event event) {
        //labelDuracion.setText(Math.round(sliderDuracion.getValue()) + " min");

    }



}