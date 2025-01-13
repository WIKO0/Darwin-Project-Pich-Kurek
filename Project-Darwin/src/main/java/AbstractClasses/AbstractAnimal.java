package AbstractClasses;
import Classes.Genes;
import Classes.Vector2D;

import EnumClasses.MoveDirections;
import Interfaces.MapElement;


public abstract class AbstractAnimal implements MapElement {
    protected Vector2D position;
    protected Vector2D borderUpperRight;
    protected Vector2D borderLowerLeft;
    protected Genes genes;
    protected MoveDirections direction;

    public void move(){
        int currRadians = direction.toRadians();
        int newRadians = (currRadians + genes.getCurrentGene() * 45) % 360;
        direction = direction.fromRadians(newRadians);

        Vector2D vec = direction.toVector();
        Vector2D newPosition = position.add(vec);
        if(newPosition.precedes(borderUpperRight) && newPosition.follows(borderLowerLeft)){
            position = newPosition;
        }
        else {
           if(borderLowerLeft.getX() > newPosition.getX()){
                newPosition.setX(borderUpperRight.getX());
           }
           else if(borderUpperRight.getX() < newPosition.getX()){
               newPosition.setX(borderLowerLeft.getX());
           }
           else{
               direction = direction.fromRadians((direction.toRadians() + 180)%360);
           }
        }

        genes.nextGene();

    }

    public Vector2D getPosition() {
        return position;
    }

    public void setBorder(Vector2D upperRight, Vector2D lowerLeft) {
        this.borderUpperRight = upperRight;
        this.borderLowerLeft = lowerLeft;
    }


}
