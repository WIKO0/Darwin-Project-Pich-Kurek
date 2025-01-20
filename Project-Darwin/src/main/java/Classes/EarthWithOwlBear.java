package Classes;

import AbstractClasses.AbstractMap;

import java.util.HashMap;
import java.util.Map;

public class EarthWithOwlBear extends AbstractMap {
    final Vector2D owlBearLowerLeft; // both corners belong to the owlBear territory
    final Vector2D owlBearUpperRight;
    private OwlBear owlBear;

    public EarthWithOwlBear(int mapHeight, int mapWidth, int numberOfGenes) {
        super(mapHeight, mapWidth);

        // designating owlBear borders
        int squareSide = (int) Math.floor(Math.sqrt((double) this.mapHeight * (double) this.mapWidth * 0.2)); // sqrt((a*b)/5)
        int territoryHeight = squareSide;
        int territoryWidth = squareSide;
        int outsideHalfHeight = (int) Math.floor((this.mapHeight - squareSide) * 0.5);
        int outsideHalfWidth = (int) Math.floor((this.mapWidth - squareSide) * 0.5);

        while (territoryHeight + (2 * outsideHalfHeight) < this.mapHeight) {
            territoryHeight++;
        }
        while (territoryHeight + (2 * outsideHalfHeight) > this.mapHeight) {
            territoryHeight--;
        }
        while (territoryWidth + (2 * outsideHalfWidth) < this.mapWidth) {
            territoryWidth++;
        }
        while (territoryWidth + (2 * outsideHalfWidth) > this.mapWidth) {
            territoryWidth--;
        }

        int leftX = outsideHalfWidth;
        int rightX = outsideHalfWidth + territoryWidth - 1;
        int upperY = outsideHalfHeight;
        int lowerY = outsideHalfHeight + territoryHeight - 1;

        this.owlBearLowerLeft = new Vector2D(leftX, lowerY);
        this.owlBearUpperRight = new Vector2D(rightX, upperY);

        // choosing owlBear spawn point and spawning it
        Vector2D spawnPoint = new Vector2D((int) (this.mapWidth * 0.5), (int) (this.mapHeight * 0.5));
        while (this.isInOwlBearBoundaries(spawnPoint) == false ) {
            spawnPoint.setX(spawnPoint.getX() + 1);
            spawnPoint.setY(spawnPoint.getY() + 1);
        }
        this.owlBear = new OwlBear(spawnPoint, numberOfGenes);
    }

    public boolean isInOwlBearBoundaries(Vector2D position) {
        int x = position.getX();
        int y = position.getY();
        int lower = this.owlBearLowerLeft.getY();
        int upper = this.owlBearUpperRight.getY();
        int left = this.owlBearLowerLeft.getX();
        int right = this.owlBearUpperRight.getX();
        if (x >= left && x <= right && y >= lower && y <= upper) {
            return true;
        }
        else {
            return false;
        }
    }

    public OwlBear getOwlBear() {
        return owlBear;
    }

    public void setOwlBear(OwlBear owlBear) {
        this.owlBear = owlBear;
    }
}
