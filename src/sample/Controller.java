package sample;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    public ListView<String> listaTiempo;

    public ImageView previsionImagen;
    public Text textCiudad;
    public ObservableList observableList = FXCollections.observableArrayList();
    public Button refreshButton;   // Botón que refresca la lista

    public void initialize() throws IOException, SAXException, ParserConfigurationException {

       refreshButton.setGraphic(new ImageView("/img/refresh.png"));
       //actualizarLista(null);  //Actualizamos la lista automaticamente al inicio

      textCiudad.setText(parser.getNombreCiudad());
      parser.anadirInfoArrays();
      setStageTitle(parser.getNombreCiudad());
    }

    public void actualizarLista(ActionEvent actionEvent) throws IOException, SAXException, ParserConfigurationException {

        Image icon = new Image("/icons/" + parser.toPrevision(0) + ".png");
        previsionImagen.setImage(icon);

        //Leemos del XML con DOM
            observableList.clear();

        //Para cada tem llama a la funcion toString para dar la informacion y la anadimos al ObservableList
        for(int iterador = 0; iterador < parser.dias.size(); iterador++){
            observableList.add(parser.toString(iterador));
        }

        //Seteamos el ListView con los Items del ObservableList
            listaTiempo.setItems(observableList);
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
