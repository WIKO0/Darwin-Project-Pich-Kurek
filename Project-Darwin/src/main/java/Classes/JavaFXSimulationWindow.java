package Classes;

import Interfaces.WorldMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JavaFXSimulationWindow extends Application {
    private WorldMap map;
    private Canvas canvas;

    // Domyślny konstruktor
    public JavaFXSimulationWindow() {}

    // Konstruktor z mapą
    public JavaFXSimulationWindow(WorldMap map) {
        this.map = map;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Darwin Projekt: Wiktor Kurek, Piotr Pich");

        canvas = new Canvas(1000, 800);  // Tworzymy płótno do rysowania
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);  // Umieszczamy canvas w kontenerze StackPane
        Scene scene = new Scene(root, 1000, 800);  // Tworzymy scenę

        primaryStage.setScene(scene);  // Ustawiamy scenę na oknie
        primaryStage.show();  // Pokazujemy okno

        // Sprawdzenie, czy mamy mapę typu EarthWithOwlBear
        if (map instanceof EarthWithOwlBear) {
            drawOwlBearMap(gc);
        } else {
            drawMap(gc);
        }
    }

    // Rysowanie mapy w zwykłej wersji
    private void drawMap(GraphicsContext gc) {
        int mapHeight = map.getUpperRight().getY() - map.getLowerLeft().getY();
        int mapWidth = map.getUpperRight().getX() - map.getLowerLeft().getX();
        double cellWidth = canvas.getWidth() / mapWidth;
        double cellHeight = canvas.getHeight() / mapHeight;

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Vector2D pos = new Vector2D(x, y);
                int animalCount = map.NumberOfAnimalsAt(pos);

                // Ustawiamy kolor dla trawy i pustych pól
                if (map.isGrassOn(pos)) {
                    gc.setFill(javafx.scene.paint.Color.GREEN);
                } else {
                    gc.setFill(javafx.scene.paint.Color.LIGHTYELLOW);
                }
                gc.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);  // Rysujemy pole

                // Rysowanie liczby zwierząt
                if (animalCount > 0) {
                    gc.setFill(javafx.scene.paint.Color.BROWN);
                    gc.fillText(
                            String.valueOf(animalCount),
                            x * cellWidth + cellWidth / 2,
                            y * cellHeight + cellHeight / 2
                    );
                }
            }
        }
    }

    // Rysowanie mapy z OwlBear
    private void drawOwlBearMap(GraphicsContext gc) {
        EarthWithOwlBear owlBearMap = (EarthWithOwlBear) map;  // Rzutowanie mapy do typu z OwlBear
        int mapHeight = map.getUpperRight().getY() - map.getLowerLeft().getY();
        int mapWidth = map.getUpperRight().getX() - map.getLowerLeft().getX();
        double cellWidth = canvas.getWidth() / mapWidth;
        double cellHeight = canvas.getHeight() / mapHeight;

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Vector2D pos = new Vector2D(x, y);
                int animalCount = map.NumberOfAnimalsAt(pos);

                // Ustawiamy kolor dla trawy i pustych pól
                if (map.isGrassOn(pos)) {
                    gc.setFill(javafx.scene.paint.Color.GREEN);
                } else {
                    gc.setFill(javafx.scene.paint.Color.LIGHTYELLOW);
                }
                gc.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);  // Rysowanie pola

                // Rysowanie liczby zwierząt
                if (animalCount > 0) {
                    gc.setFill(javafx.scene.paint.Color.BROWN);
                    gc.fillText(
                            String.valueOf(animalCount),
                            x * cellWidth + cellWidth / 2,
                            y * cellHeight + cellHeight / 2
                    );
                }

                // Rysowanie OwlBear na mapie
                if (owlBearMap.getOwlBearPosition().equals(pos)) {
                    gc.setFill(javafx.scene.paint.Color.RED);
                    gc.fillOval(
                            x * cellWidth + cellWidth / 4,
                            y * cellHeight + cellHeight / 4,
                            cellWidth / 2,
                            cellHeight / 2
                    );
                }
            }
        }
    }

    // Metoda uruchamiająca aplikację JavaFX
    public static void launchSimulation(WorldMap map) {
        if (map == null) {
            System.out.println("ERROR: Map is null when launching JavaFX window.");
            throw new IllegalArgumentException("Map is null");
        }

        // Tworzymy obiekt JavaFXSimulationWindow z mapą, ale nie wywołujemy launch() w tej metodzie
        JavaFXSimulationWindow window = new JavaFXSimulationWindow(map);
        // Uruchamiamy aplikację JavaFX w metodzie main
    }

    public static void main(String[] args) {
        // Uruchamiamy aplikację JavaFX
        launch(args);
    }
}
