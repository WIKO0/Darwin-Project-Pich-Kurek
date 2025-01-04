package AbstractClasses;

import Classes.Genes;
import Classes.Vector2D;
import Interfaces.PositionChangeObserver;

import java.util.Random;

public class Animal extends AbstractAnimal{
    private int energy;
    private int numberOfMoves;
    private int age;
    private int paceOfAging;

    public Animal(Vector2D pos, int n, int energy, int paceOfAging) {
        this.position = pos;
        Random rand = new Random();
        this.direction = direction.fromRadians(rand.nextInt(8) * 45);
        this.genes = new Genes(n);
        this.energy = energy;
        this.numberOfMoves = 0;
        this.paceOfAging = paceOfAging;
    }



    @Override
    public boolean canMove() {
        numberOfMoves++;
        energy -= 1;
        Random rand = new Random();
        int randomNum = rand.nextInt(100);
        if(randomNum < Math.min(age,79)){
            move();
            return true;
        };
        return false;
    }

    public void aging(){
        if (numberOfMoves % paceOfAging == 0) {
            age ++;
        }
    }

    public int getEnergy() {
        return energy;
    }
    

    @Override
    public void addObserver(PositionChangeObserver observer) {

    }
}
