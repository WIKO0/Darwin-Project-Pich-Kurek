package Classes;

import AbstractClasses.AbstractAnimal;

import java.util.Comparator;

public class CompareAnimals implements Comparator<AbstractAnimal> {

    @Override
    public int compare(AbstractAnimal animal1, AbstractAnimal animal2 ) {
        if (animal1 instanceof Animal && animal2 instanceof Animal) {
            Animal firstAnimal = (Animal) animal1;
            Animal secondAnimal = (Animal) animal2;
            if(firstAnimal.dominates(secondAnimal)) {
                return -1;
            }
            else {
                return 1;
            }
        }
        else {
            return 0;
        }
    }
}
