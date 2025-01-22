import Classes.*;
import Menu.MenuSettings;
import javafx.application.Application;

import java.util.Properties;

public class World {
    public static void main(String[] args) {
        /*
        System.out.println("return 0");
        Vector2D position1 = new Vector2D(0,0);
        int size1 = 9;
        int energy1 = 200;
        int age1 = 10;
        int pace1 = 5;

        Animal animal1 = new Animal(position1,size1,energy1,age1,pace1);

        
        Vector2D position2 = new Vector2D(0,0);
        int size2 = 18;
        int energy2 = 100;
        int age2 = 10;
        int pace2 = 5;

        Animal animal2 = new Animal(position2,size2,energy2,age2,pace2);

        Genes kidGenes = animal1.createOffspringGene(animal2,0);

        System.out.println(animal1.getGenes().toString());
        System.out.println(animal2.getGenes().toString());
        System.out.println(kidGenes.toString());
        */

//        Application.launch(App.class, args);



//        SimulationEngine engine = new SimulationEngine();
//        Simulation simulation = new Simulation(10, 10, 20, 40, 6,
//                40, 10,1,10, 5, 25,
//                20, 0, 0, 1,true, true);
//        ConsoleDisplay consoleDisplay = new ConsoleDisplay();
//        simulation.registerObserver(consoleDisplay);
//        engine.addSimulation(simulation);

        try{
            PropertiesValidator props = new PropertiesValidator();

            Integer[] defaultMapProps = {
                    props.getMapHeight(),
                    props.getMapWidth(),
                    props.getNAnimals(),
                    props.getNGrass(),
                    props.getNGenes(),
                    props.getDefaultEnergy(),
                    props.getDefaultAge(),
                    props.getPaceOfAging(),
                    props.getGrassEnergyGiven(),
                    props.getGrassDailyGrowth(),
                    props.getMinEnergyForCopulation(),
                    props.getEnergyUsedToCopulate(),
                    props.getMinMutations(),
                    props.getMaxMutations(),
                    props.getGapTime()
            };
            Boolean[] Upgrades = {
                    props.isSpawnOwlBear(),
                    props.isAgeIsHeavyBurden()
            };

            MenuSettings menu = new MenuSettings();
            menu.startSimulation(defaultMapProps, Upgrades);
            JavaFXSimulationWindow.launchSimulation(null);
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
            return;
        }

    }
}
