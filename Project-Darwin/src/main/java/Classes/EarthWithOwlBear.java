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
        int lowerY = outsideHalfHeight;
        int upperY = outsideHalfHeight + territoryHeight - 1;

        this.owlBearLowerLeft = new Vector2D(leftX, lowerY);
        this.owlBearUpperRight = new Vector2D(rightX, upperY);
        // choosing owlBear spawn point and spawning it
        Vector2D spawnPoint = new Vector2D((int) (this.mapWidth * 0.5), (int) (this.mapHeight * 0.5));
        while (!this.isInOwlBearBoundaries(spawnPoint)) {
            spawnPoint.setX(spawnPoint.getX() + 1);
            spawnPoint.setY(spawnPoint.getY() + 1);
        }
        this.owlBear = new OwlBear(spawnPoint, numberOfGenes);
        owlBear.setBorder(this.owlBearUpperRight, this.owlBearLowerLeft);
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

    public Vector2D getOwlBearPosition() {
        return this.owlBear.getPosition();
    }

    public void setOwlBear(OwlBear owlBear) {
        this.owlBear = owlBear;
    }

    public void killAll(Vector2D position) {
//        System.out.println("przed petla");
        if(this.animals.get(position) != null) {
            for(int i = 0; i < this.animals.get(position).size(); i++){
                ((Animal)this.animals.get(position).get(i)).setIsDead(true);
                //System.out.println("Zwierzak zabity od owl Beara: "+((Animal)this.animals.get(position).get(i)).getIsDead());
            }
        }
        this.animals.remove(position);
    }

    public Vector2D getOwlBearLowerLeft() {
        return owlBearLowerLeft;
    }

    public Vector2D getOwlBearUpperRight() {
        return owlBearUpperRight;
    }

    public int getNumberOfUnoccupiedFields() {
        return super.getNumberOfUnoccupiedFields() - 1;
    }
}