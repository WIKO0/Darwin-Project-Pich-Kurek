package View;

import Classes.Simulation;
import Presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("app.fxml"));
        loader.setLocation(getClass().getClassLoader().getResource("app.fxml"));
        BorderPane viewRoot = loader.load();

        SimulationPresenter presenter = loader.getController();

        configureStage(primaryStage, viewRoot);
        primaryStage.show();


    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);
        scene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Darwin Project");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}


