import Classes.*;
import Menu.MenuSettings;
import javafx.application.Application;


public class World {
    public static void main(String[] args) {
        try {
            Application.launch(SimulationApp.class,args);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
    }
}
