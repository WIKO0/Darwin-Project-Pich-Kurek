package Classes;

import AbstractClasses.AbstractAnimal;
import EnumClasses.MoveDirections;
import Interfaces.MapElement;
import Interfaces.PositionChangeObserver;

import java.util.Objects;
import java.util.Random;

public class OwlBear extends AbstractAnimal {

    public OwlBear(Vector2D position, int N) {
        Random rand = new Random();
        this.position = position;
        this.direction = MoveDirections.fromRadians(rand.nextInt(8) * 45);
        this.genes = new Genes(N);
    }

    @Override
    public void move() {
        int currRadians = direction.toRadians();
        int newRadians = (currRadians + this.genes.getCurrentGene() * 45) % 360;
        this.direction = MoveDirections.fromRadians(newRadians);

//        System.out.println("Owl Bear UpperRight: " + this.borderUpperRight);
//        System.out.println("Owl Bear LowerLeft: " + this.borderLowerLeft);

        Vector2D vec = this.direction.toVector();
        Vector2D newPosition = this.position.add(vec);
        if(newPosition.precedes(this.borderUpperRight) && newPosition.follows(this.borderLowerLeft)){
            this.position = newPosition;
        }
        else{
            Random rand = new Random();
            int randomDirection = rand.nextInt(8);
            this.direction = MoveDirections.fromRadians((direction.toRadians() + 180)%360);
        }
        this.genes.nextGene();

    }

    public boolean canEat(MapElement element){
        return (element instanceof Animal) && this.position.equals((element).getPosition());
    }

    public Genes getGenes(){
        return this.genes;
    }


    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public void addObserver(PositionChangeObserver observer) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwlBear ob = (OwlBear) o;
        return this.direction == ob.direction && this.position.equals(ob.position) && this.genes.equals(ob.genes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, direction, genes);
    }





}
