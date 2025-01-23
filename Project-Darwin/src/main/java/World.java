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

//        try{
//            PropertiesValidator props = new PropertiesValidator();
//
//            Integer[] defaultMapProps = {
//                    props.getMapHeight(),
//                    props.getMapWidth(),
//                    props.getNAnimals(),
//                    props.getNGrass(),
//                    props.getNGenes(),
//                    props.getDefaultEnergy(),
//                    props.getDefaultAge(),
//                    props.getPaceOfAging(),
//                    props.getGrassEnergyGiven(),
//                    props.getGrassDailyGrowth(),
//                    props.getMinEnergyForCopulation(),
//                    props.getEnergyUsedToCopulate(),
//                    props.getMinMutations(),
//                    props.getMaxMutations(),
//                    props.getGapTime()
//            };
//            Boolean[] Upgrades = {
//                    props.isSpawnOwlBear(),
//                    props.isAgeIsHeavyBurden()
//            };
//
//            MenuSettings menu = new MenuSettings();
//            menu.startSimulation(defaultMapProps, Upgrades);
//
//
//        }
//        catch(IllegalArgumentException e){
//            System.out.println(e);
//            return;
//        }
//
//    }
}
