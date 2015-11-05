package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;


public class Controller {

    public ListView<String> listaTiempo;
    public ObservableList observableList;
    public Button refreshButton;   // Botón que refresca la lista

    public void initialize(){
       refreshButton.setGraphic(new ImageView("/img/refresh.png"));
       actualizarLista(null);  //Actualizamos la lista automaticamente al inicio
    }

    public void actualizarLista(ActionEvent actionEvent) {

        //Leemos del XML con DOM

        observableList.clear();

        
    }
}
