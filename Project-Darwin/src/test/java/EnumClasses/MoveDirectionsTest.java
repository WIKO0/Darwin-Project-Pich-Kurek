package EnumClasses;

import Classes.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveDirectionsTest {
    @Test
    void testToVector() {
        Vector2D forward = new Vector2D(0,1);
        MoveDirections f = MoveDirections.FORWARD;
        Vector2D backward = new Vector2D(0,-1);
        MoveDirections b = MoveDirections.BACKWARD;
        Vector2D left = new Vector2D(-1,0);
        MoveDirections l = MoveDirections.LEFT;
        Vector2D right = new Vector2D(1,0);
        MoveDirections r = MoveDirections.RIGHT;
        Vector2D forwardRight = new Vector2D(1,1);
        MoveDirections fr = MoveDirections.FORWARD_RIGHT;
        Vector2D backwardRight = new Vector2D(1,-1);
        MoveDirections br = MoveDirections.BACKWARD_RIGHT;

        assertEquals(forward, f.toVector());
        assertEquals(backward, b.toVector());
        assertEquals(left, l.toVector());
        assertEquals(right, r.toVector());
        assertEquals(forwardRight, fr.toVector());
        assertEquals(backwardRight, br.toVector());
    }

    @Test
    void testToRadians(){
        assertEquals(0,MoveDirections.FORWARD.toRadians());
        assertEquals(45,MoveDirections.FORWARD_RIGHT.toRadians());
        assertEquals(90,MoveDirections.RIGHT.toRadians());
        assertEquals(135,MoveDirections.BACKWARD_RIGHT.toRadians());
        assertEquals(180,MoveDirections.BACKWARD.toRadians());
        assertEquals(225,MoveDirections.BACKWARD_LEFT.toRadians());
        assertEquals(270,MoveDirections.LEFT.toRadians());
        assertEquals(315,MoveDirections.FORWARD_LEFT.toRadians());
    }
}