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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.util.StringConverter;

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
    private MenuItem menuSalir;
    @FXML
    private MenuItem menuAcercade;
    @FXML
    private ComboBox<Receta> comboRecetas;
    @FXML
    private ToggleGroup dificultad;
    @FXML
    private ImageView carita;

    private MediaPlayer mediaPlayer;

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

    //valores iniciales y configuraciones de la ventana
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Media sonido = new Media(HelloApplication.class.getClassLoader().getResource("com/example/recetario/audio" +
                "/clic.wav").toExternalForm());
        mediaPlayer = new MediaPlayer(sonido);

        comboDificultad.getItems().add("Fácil");
        comboDificultad.getItems().add("Moderada");
        comboDificultad.getItems().add("Difícil");

        //comboDificultad.getItems().addAll("Fácil","Medio","Dificil");


        //un observable list es como un arrayList pero con mas capacidad
        /*
        ObservableList<String> datos = FXCollections.observableArrayList();
        datos.addAll("","");
        comboDificultad.setItems(datos);
        */

        comboDificultad.getSelectionModel().selectFirst();

        comboDificultad.valueProperty().addListener(
                (observableValue, s, t1) -> {
                    String imagen = "neutral.png";
                    //t1 es el valor actual
                    if(t1=="Fácil")
                        imagen="feliz.png";
                    else if(t1=="Difícil")
                        imagen="muerto.png";

                    carita.setImage(new Image("com/example/recetario/img/"+imagen));

                    mediaPlayer.seek(new Duration(0));
                    mediaPlayer.play();

                }
        );

        sliderDuracion.setValue(60);
        labelDuracion.setText(Math.round(sliderDuracion.getValue()) + " min");

        listTipo.getItems().addAll("Desayuno","Segundo desayuno","Almuerzo","Sobrealmurezo","Merienda","Cena","Recena","Postcena");
        listTipo.getSelectionModel().selectFirst();


        //cuando localizo una propiedad, le puedo añadir un listener
        //cuando se produce un cambio en la propiedad, se ejecuta el listener
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

        comboRecetas.setConverter(new StringConverter<Receta>() {
            @Override
            public String toString(Receta receta) {
                if(receta!=null) return receta.getNombre();
                else return "";
            }

            @Override
            public Receta fromString(String s) {
                return null;
            }
        });

        comboRecetas.getItems().addAll(tabla.getItems());


    }

    @FXML
    public void actualizarDuracion(Event event) {
        //labelDuracion.setText(Math.round(sliderDuracion.getValue()) + " min");

    }


    @FXML
    public void salir(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void mostrarAcerdaDe(ActionEvent actionEvent) {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("El Creador");
        alert.setContentText("Álvaro");
        alert.showAndWait();
    }

    @FXML
    public void mostrarReceta(ActionEvent actionEvent) {
        System.out.println(comboRecetas.getSelectionModel().getSelectedItem());

        Session.setRecetaActual(comboRecetas.getSelectionModel().getSelectedItem());

        HelloApplication.loadFXML("ventanaSecundaria.fxml");
    }
}