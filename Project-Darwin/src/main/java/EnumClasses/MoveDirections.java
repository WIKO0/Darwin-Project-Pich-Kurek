package EnumClasses;

import Classes.Vector2D;

public enum MoveDirections {
    FORWARD,
    FORWARD_RIGHT,
    FORWARD_LEFT,
    BACKWARD,
    RIGHT,
    LEFT,
    BACKWARD_RIGHT,
    BACKWARD_LEFT;


    public Vector2D toVector(){
        switch (this) {
            case FORWARD: return new Vector2D(0,1);
            case FORWARD_RIGHT: return new Vector2D(1,1);
            case FORWARD_LEFT: return new Vector2D(-1,1);
            case RIGHT: return new Vector2D(1,0);
            case LEFT: return new Vector2D(-1,0);
            case BACKWARD: return new Vector2D(0,-1);
            case BACKWARD_RIGHT: return new Vector2D(1,-1);
            case BACKWARD_LEFT: return new Vector2D(-1,-1);
            default: return null;
        }
    }

    public int toRadians(){
        switch (this) {
            case FORWARD: return 0;
            case FORWARD_RIGHT: return 45;
            case FORWARD_LEFT: return 315;
            case RIGHT: return 90;
            case LEFT: return 270;
            case BACKWARD: return 180;
            case BACKWARD_RIGHT: return 135;
            case BACKWARD_LEFT: return 225;
            default: return 0;
        }
    }

    public static MoveDirections fromRadians(int n){
        switch (n) {
            case 0: return FORWARD;
            case 45: return FORWARD_RIGHT;
            case 90: return RIGHT;
            case 135: return BACKWARD_RIGHT;
            case 180: return BACKWARD;
            case 225: return BACKWARD_LEFT;
            case 270: return LEFT;
            case 315: return FORWARD_LEFT;
            default: return null;
        }
    }


    public MoveDirections OppositeDirection (MoveDirections direction) {
        switch (direction) {
            case BACKWARD: return FORWARD;
            case BACKWARD_LEFT: return FORWARD_RIGHT;
            case LEFT: return RIGHT;
            case FORWARD_LEFT: return BACKWARD_RIGHT;
            case FORWARD: return BACKWARD;
            case FORWARD_RIGHT: return BACKWARD_LEFT;
            case RIGHT: return LEFT;
            case BACKWARD_RIGHT: return FORWARD_LEFT;
            default: return null;
        }
    }

}
