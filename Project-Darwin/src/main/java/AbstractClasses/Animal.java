package AbstractClasses;

import Classes.Genes;
import Classes.Vector2D;
import Interfaces.PositionChangeObserver;

import java.util.Random;

public class Animal extends AbstractAnimal{
    private int energy;
    private int speed;
    private int age;
    public Animal(Vector2D pos, int n, int energy){
        this.position = pos;
        Random rand = new Random();
        this.direction = direction.fromRadians(rand.nextInt(8) * 45);
        this.genes = new Genes(n);
        this.energy = energy;
    }



    @Override
    public boolean canMove() {
        move();
        return true;
    }

    @Override
    public void addObserver(PositionChangeObserver observer) {

    }
}
