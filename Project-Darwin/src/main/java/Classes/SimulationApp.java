package Classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import Presenter.SimulationPresenter;

public class SimulationApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/app.fxml"));
        BorderPane view = fxmlLoader.load();
        SimulationPresenter presenter = fxmlLoader.getController();
        configureStage(stage, view);
        stage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);
        // Load the CSS file
        scene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Darwin Project");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    public static void main(String[] args) {
        launch(args);
    }
}