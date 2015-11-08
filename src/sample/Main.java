package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("OpenWeather Client");
        primaryStage.setScene(new Scene(root, 424, 476));
        primaryStage.show();
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
