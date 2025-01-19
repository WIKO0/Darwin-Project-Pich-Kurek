package Classes;

import EnumClasses.MoveDirections;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwlBearTest {
    @Test
    void testOwlBear() {
        Vector2D position = new Vector2D(0,0);
        int size = 10;
        OwlBear beast = new OwlBear(position,size);
        assertTrue(beast instanceof OwlBear);
    }

    @Test
    void testOwlBearMove(){
        Vector2D position = new Vector2D(2,2);
        int size = 10;
        OwlBear beast = new OwlBear(position,size);
        Vector2D lowerLeft = new Vector2D(0,0);
        Vector2D upperRight = new Vector2D(5,5);
        beast.setBorder(upperRight,lowerLeft);
        beast.setDirection(MoveDirections.FORWARD);
        beast.move();


        switch(beast.getGenes().getCurrentGene()){
            case 0 -> {
                Vector2D endPosition = new Vector2D(2,3);
                assertEquals(beast.getDirection(), MoveDirections.FORWARD);
                assertEquals(beast.getPosition(), endPosition);
            }
            case 1 -> {
                Vector2D endPosition = new Vector2D(3,3);
                assertEquals(beast.getDirection(), MoveDirections.FORWARD_RIGHT);
                assertEquals(beast.getPosition(), endPosition);

            }
            case 2 -> {
                Vector2D endPosition = new Vector2D(3,2);
                assertEquals(beast.getDirection(), MoveDirections.RIGHT);
                assertEquals(beast.getPosition(), endPosition);
            }
            case 3 -> {
                Vector2D endPosition = new Vector2D(3,1);
                assertEquals(beast.getDirection(), MoveDirections.BACKWARD_RIGHT);
                assertEquals(beast.getPosition(), endPosition);

            }
            case 4 -> {
                Vector2D endPosition = new Vector2D(2,1);
                assertEquals(beast.getDirection(), MoveDirections.BACKWARD);
                assertEquals(beast.getPosition(), endPosition);
            }
            case 5 -> {
                Vector2D endPosition = new Vector2D(1,1);
                assertEquals(beast.getDirection(), MoveDirections.BACKWARD_LEFT);
                assertEquals(beast.getPosition(), endPosition);
            }
            case 6 -> {
                Vector2D endPosition = new Vector2D(1,2);
                assertEquals(beast.getDirection(), MoveDirections.LEFT);
                assertEquals(beast.getPosition(), endPosition);
            }
            case 7 -> {

                Vector2D endPosition = new Vector2D(1,3);
                assertEquals(beast.getDirection(), MoveDirections.FORWARD_LEFT);
                assertEquals(beast.getPosition(), endPosition);
            }
        }
    }

}