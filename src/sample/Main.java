package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;
    private Stage stats;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("OpenWeather Client");
        primaryStage.setScene(new Scene(root, 424, 476));
        primaryStage.show();

        /*

        // Load the fxml file and create a new stage for the popup
        FXMLLoader statsLoader = new FXMLLoader(getClass().getResource("stats.fxml"));
        Parent page = statsLoader.load();
        stats = new Stage();
        stats.setTitle("Diàleg FXML");
        stats.initModality(Modality.WINDOW_MODAL);
        stats.initOwner(primaryStage);
        stats.setScene(new Scene(page));

        // Guardem el dialogStage al controlador per poder accedir-hi i mostrar ña finestra
        ((StatsController)statsLoader.getController()).setDialogStage(stats);

        // Guardem el controlador del diàleg al controlador principal per poder cridar el mètode que
        // el mostra quan triem la opció al menú.
        ((Controller)loader.getController()).setStatsController(statsLoader.getController());

        */

    }

    public static void main(String[] args) {
        launch(args);
    }

    //Getter

    public static Stage getStage() {
        return stage;
    }

    //Setter
    public void setStage(Stage primaryStage) {
        this.stage = stage;
    }
}
