package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;


public class Controller {

    @FXML
    public ListView<String> listaTiempo;    // ListView donde se almacenan los datos de cada día
    public ObservableList observableList = FXCollections.observableArrayList(); // Observable list para seguir la lista del tiempo

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

    // Menus de los que hemos de saber de donde viene el evento
    public MenuItem menuSemana;
    public MenuItem menuDosSemanas;
    public MenuItem menuTresSemanas;

    // Para controlar un futuro dialog de estadisticas
    // private Dialog dialog;
    // private StatsController statsController;

    public void initialize() throws IOException, SAXException, ParserConfigurationException {

      refreshButton.setGraphic(new ImageView("/img/refresh.png"));  //ruta de la imagen para el botón de actualizar
      buttonBack.setGraphic(new ImageView("/img/back.png"));    //ruta de la imagen para el botón de ir atrás
      textCiudad.setText(Parser.getNombreCiudad()); //asignamos el nombre de la ciudad
      Parser.anadirInfoArrays();  //Llenamos el array con los datos

    }

    public void actualizarLista(ActionEvent actionEvent) throws IOException, SAXException, ParserConfigurationException {

        /*Cada día tiene una previsión que puede ser "few clouds", "rain", "clear sky", etc, etc.
        Lo que he hecho ha sido descargar las imagenes y cambiarle el nombre a cada una al estado que representa
        Así, unicamente leyendo la previsión y añadiendole .png al final, ya sabe que imagen mostrar
        */


        observableList.clear(); //Limpia la lista antes de escribir

        Image icon = new Image("/icons/" + Parser.toPrevision(selectedItemIndex) + ".png"); // ruta de la imagen de la previsión

        previsionImagen.setImage(icon); // la asigna al ImageView.

        //Rellena una posición del observable list con la información del día
        for(int iterador = 0; iterador < Parser.dias.size(); iterador++){
            observableList.add(Parser.toString(iterador));
        }

        listaTiempo.setItems(observableList); // Añadimos a la listView los items del observableList
        setStageTitle(Parser.nombreCiudad);  // Y asginamos al nombre de la ventana la ciudad
    }

    public void onChangeListView(){ //Cada vez que se haga click en el listView se ejcutará esto

        listaTiempo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            selectedItemIndex = listaTiempo.getSelectionModel().getSelectedIndex(); //Sacamos el numero del índice que tenemos seleccionado

            Image icon = new Image("/icons/" + Parser.toPrevision(selectedItemIndex) + ".png"); // ruta de la imagen de la previsión
            previsionImagen.setImage(icon); // la asigna al ImageView.
            listViewPrevision.setImage(icon); // la asigna al ImageView oculto
            listViewText.setText(observableList.get(selectedItemIndex).toString());
            listViewCiudad.setText(Parser.nombreCiudad);
            listViewTemp.setText(Parser.temperatura.get(selectedItemIndex));

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

    public void estadisticas(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Estadisticas | Weather Client v0.1");
        alert.setHeaderText("Estadisticas");

        if (actionEvent.getSource().equals(menuSemana)) {
            alert.setContentText(Parser.mitjana(1));
        }
        else if (actionEvent.getSource().equals(menuDosSemanas)) {
            alert.setContentText(Parser.mitjana(2));
        }
        else if (actionEvent.getSource().equals(menuTresSemanas)){
            alert.setContentText(Parser.mitjana(3));
        }

        alert.showAndWait();
    }

    /*

    public void mieDialogClick(ActionEvent actionEvent) {
        dialog.show();  // Mostrem el diàleg
    }

    public void setStatsController(StatsController statsController) {
        this.statsController = statsController;
    }

    public void miDialegFXMLClick(ActionEvent actionEvent) {
        statsController.showDialogStage();
    }

    */

}
