package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;


public class Controller {

    @FXML
    public ListView<String> listaTiempo;    // ListView donde se almacenan los datos de cada día
    public ObservableList observableList = FXCollections.observableArrayList();

    // Toolbar y cabecera
    public int selectedItemIndex = 0;   // Posición del listView que está seleccionada
    public Button refreshButton;   // Botón que refresca la lista
    public Button buttonBack;   // Botón para volver al listView
    public ImageView previsionImagen;   // Imagen que muestra el estado del tiempo
    public Text textCiudad;             // Texto con el nombre de la ciudad

    // Mostrables al clicar un item del listView
    public ImageView listViewPrevision; // Imagen que se muestra al clicar un Item del listView
    public Text listViewCiudad;         // Texto con el nombre de la ciudad que se muestra al clicar un Item del listView
    public Text listViewText;   // Texto con información que se muestra al clicar un Item del listView
    public Text listViewTemp;   // Texto con la temperatura que se muestra al clicar un Item del listView
    
    public void initialize() throws IOException, SAXException, ParserConfigurationException {

      refreshButton.setGraphic(new ImageView("/img/refresh.png"));  //ruta de la imagen para el botón de actualizar
      buttonBack.setGraphic(new ImageView("/img/back.png"));    //ruta de la imagen para el botón de ir atrás
      textCiudad.setText(parser.getNombreCiudad()); //asignamos el nombre de la ciudad
      parser.anadirInfoArrays();  //Llenamos el array con los datos
    }

    public void actualizarLista(ActionEvent actionEvent) throws IOException, SAXException, ParserConfigurationException {

        /*Cada día tiene una previsión que puede ser "few clouds", "rain", "clear sky", etc, etc.
        Lo que he hecho ha sido descargar las imagenes y cambiarle el nombre a cada una al estado que representa
        Así, unicamente leyendo la previsión y añadiendole .png al final, ya sabe que imagen mostrar
        */

        setStageTitle(parser.getNombreCiudad());  // Y asginamos al nombre de la ventana la ciudad
        
        Image icon = new Image("/icons/" + parser.toPrevision(selectedItemIndex) + ".png"); // ruta de la imagen de la previsión
        previsionImagen.setImage(icon); // la asigna al ImageView.

        //Rellena una posición del observable list con la información del día
        for(int iterador = 0; iterador < parser.dias.size(); iterador++){
            observableList.add(parser.toString(iterador));
        }
            listaTiempo.setItems(observableList); // Añadimos a la listView los items del observableList
    }

    public void onChangeListView(){ //Cada vez que se haga click en el listView se ejcutará esto

        listaTiempo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            selectedItemIndex = listaTiempo.getSelectionModel().getSelectedIndex(); //Sacamos el numero del índice que tenemos seleccionado

            Image icon = new Image("/icons/" + parser.toPrevision(selectedItemIndex) + ".png"); // ruta de la imagen de la previsión
            previsionImagen.setImage(icon); // la asigna al ImageView.
            listViewPrevision.setImage(icon); // la asigna al ImageView oculto
            listViewText.setText(observableList.get(selectedItemIndex).toString());
            listViewCiudad.setText(parser.nombreCiudad);
            listViewTemp.setText(parser.temperatura.get(selectedItemIndex));

            // PROVISIONAL - Mostramos y ocultamos los objetos que nos interesan

            listaTiempo.setVisible(false);
            previsionImagen.setVisible(false);
            listViewPrevision.setVisible(true);
            listViewTemp.setVisible(true);
            listViewCiudad.setVisible(true);
            buttonBack.setVisible(true);
            listViewText.setVisible(true);
        });
    }

    public void volverListView(ActionEvent actionEvent) {

        // PROVISIONAL - Mostramos y ocultamos los objetos que nos interesan

        previsionImagen.setVisible(true);
        buttonBack.setVisible(false);
        listViewText.setVisible(false);
        listViewTemp.setVisible(false);
        listViewCiudad.setVisible(false);
        listViewPrevision.setVisible(false);
        listaTiempo.setVisible(true);
    }

    public void about(ActionEvent actionEvent) {       //Ventana dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Open Weather Client v0.1");
        alert.setHeaderText("Open Weather Client v0.1");
        alert.setContentText("Developed by Sergi Barjola." + "\n" + "Project aviable on gitHub:" + "\n" + "https://github.com/helicida/ElTempsDesktop");
        alert.showAndWait();
    }

    public void setStageTitle(String newTitle){ // Método que nos cambiara el stage en el Main controller
        Main.getStage().setTitle(newTitle + " | Open Weather Client"); // Es necesario porque no se puede referenciar en un contexto éstatico directamente
    }

    public void salirAplicacion(ActionEvent actionEvent) {
        Platform.exit();
    }


}
