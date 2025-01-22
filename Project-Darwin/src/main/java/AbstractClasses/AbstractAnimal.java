package AbstractClasses;
import Classes.Genes;
import Classes.Vector2D;

import EnumClasses.MoveDirections;
import Interfaces.MapElement;


public abstract class AbstractAnimal implements MapElement {
    protected  Vector2D position;
    protected Vector2D borderUpperRight;
    protected Vector2D borderLowerLeft;
    protected Genes genes;
    protected MoveDirections direction;

    public void move(){
        int currRadians = this.direction.toRadians();
        int newRadians = (currRadians + this.genes.getCurrentGene() * 45) % 360;
        this.direction = MoveDirections.fromRadians(newRadians);

        Vector2D vec = this.direction.toVector();
        Vector2D newPosition = this.position.add(vec);
        if(newPosition.precedes(this.borderUpperRight) && newPosition.follows(this.borderLowerLeft)){
            this.position = newPosition;
        }
        else {
           if(this.borderLowerLeft.getX() > newPosition.getX()){
                newPosition.setX(borderUpperRight.getX());
           }
           else if(this.borderUpperRight.getX() < newPosition.getX()){
               newPosition.setX(borderLowerLeft.getX());
           }
           else{
               direction = MoveDirections.fromRadians((direction.toRadians() + 180)%360);
           }
        }

        genes.nextGene();

    }

    // funkcja prawdopodobnie potrzebna tylko dla testów
    public void setDirection(MoveDirections newDirection){
        this.direction = newDirection;
    }

    public MoveDirections getDirection(){
        return this.direction;
    }


    public Vector2D getPosition() {
        return position;
    }

    public void setBorder(Vector2D upperRight, Vector2D lowerLeft) {
        this.borderUpperRight = upperRight;
        this.borderLowerLeft = lowerLeft;
    }





}
