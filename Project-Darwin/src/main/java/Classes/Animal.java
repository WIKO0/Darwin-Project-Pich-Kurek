package Classes;

import AbstractClasses.AbstractAnimal;


import EnumClasses.MoveDirections;
import Interfaces.MapElement;
import Interfaces.PositionChangeObserver;
import java.util.Random;

public class Animal extends AbstractAnimal {
    private int energy;

    private final int startEnergy;
    private int numberOfMoves;
    private int age;
    private final int paceOfAging;
    private final int N;
    private int children;
    private int side;

        this.position = pos;
        this.N=N;
        Random rand = new Random();
        this.energy = energy;
        this.numberOfMoves = 0;
        this.paceOfAging = paceOfAging;
        this.startEnergy = energy;
        this.children = 0;
        this.age = age;
    }

    public Animal(Vector2D pos, int energy, Genes parentsGenes, int paceOfAging) {
        this.position = pos;
        this.energy = energy;
        this.startEnergy = energy;
        this.numberOfMoves = 0;
        this.age = 0;
        this.genes = parentsGenes;
        this.paceOfAging = paceOfAging;
        this.N = genes.getSize();
        this.children = 0;
        Random rand = new Random();
        this.direction = MoveDirections.fromRadians(rand.nextInt(8) * 45);
    }

    public void incrementChild(){
        this.children++;
    }

    public int getAge(){
        return age;
    }

    public int getChildren() {
        return this.children;
    }

    public Genes getGenes(){
        return this.genes;
    }

    public boolean canEat(MapElement element){
        return (element instanceof Grass) && this.position.equals(element.getPosition());
    }

    @Override
    public boolean canMove() {
        energy -= 1;
        Random rand = new Random();
        int randomNum = rand.nextInt(100);
        if(randomNum < Math.min(age,79)){
            move();
            return true;
        };
        return false;
    }

    public void incrementNumberOfMoves() {
        this.numberOfMoves += 1;
        this.aging();
    }

    public void aging(){
        if (this.numberOfMoves % this.paceOfAging == 0) {
            this.age ++;
        }
    }

    public int getEnergy() {
        return energy;
    }
    

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void incrementEnergy(int energy) {
        this.energy = Math.min(startEnergy, energy + this.energy);
    }

    boolean canMate(){
        if(this.energy >= this.startEnergy/2){
            return true;
        }
        return false;
    }

    public int getSide(){
        return this.side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    boolean dominates(Animal rival){
        if(energy > rival.getEnergy()){
            return true;
        }
        else if(energy == rival.getEnergy()){
            if(age > rival.age){
                return true;
            }
            else if(age == rival.age){
                if(children > rival.children){
                    return true;
                }
                else if(children == rival.children){
                    Random rand = new Random();
                    int randomNum = rand.nextInt(2);
                    return randomNum == 0;
                }
                return false;
            }
            return false;
        }
        return false;
    }


    public Genes createOffspringGene(Animal mate, int randomNum){
        float energyRatioAnimal = (float) (this.energy) / (this.energy + mate.getEnergy());
        float energyRatioMate = (float) (mate.getEnergy()) / (this.energy + mate.getEnergy());
        int n,m;
        n = (int)(this.genes.getSize() * (energyRatioAnimal));
        m = (int)(mate.getGenes().getSize() * (energyRatioMate));
        int animalGenesSize = this.genes.getSize();
        int mateGenesSize = mate.getGenes().getSize();
        Genes newGenes = new Genes();
        if(this.energy > mate.getEnergy()){
            if(randomNum == 0){
                for (int i = 0; i < n; i++) {
                    newGenes.addGene(this.genes.getGenes().get(i));
                };

                for(int j = (mateGenesSize-m); j< mateGenesSize; j++){
                    newGenes.addGene(mate.getGenes().getGenes().get(j));
                }
            }
            else{
                for(int i = (animalGenesSize-n); i < animalGenesSize; i++){
                    newGenes.addGene(this.genes.getGenes().get(i));
                }
                for(int j = 0; j < m; j++){
                    newGenes.addGene(mate.getGenes().getGenes().get(j));
                }
            }
        }
        else {
            if (randomNum == 0) {
                for (int i = (animalGenesSize-n); i < animalGenesSize; i++) {
                    newGenes.addGene(this.genes.getGenes().get(i));
                }
                for (int j = 0; j < m; j++) {
                    newGenes.addGene(mate.getGenes().getGenes().get(j));
                }
            } else {
                if (randomNum == 0) {
                    for (int i = 0; i < n; i++) {
                        newGenes.addGene(this.genes.getGenes().get(i));
                    }
                    ;
                    for (int j = (mateGenesSize-m); j< mateGenesSize; j++) {
                        newGenes.addGene(mate.getGenes().getGenes().get(j));
                    }
                }

            }
        }

        return newGenes;
    };


    public Animal copulate(Animal mate){
        Random rand = new Random();
        int side = rand.nextInt(2);// 0 - lewa, 1 - prawa
        Genes newGenes = this.createOffspringGene(mate, side);
        mate.setEnergy(mate.getEnergy()/2);
        this.energy = this.energy/2;
        this.children ++;
        mate.incrementChild();
        newGenes.setCurrentGene();
        return new Animal(position,energy,newGenes,paceOfAging);
    };






    @Override
    public void addObserver(PositionChangeObserver observer) {

    }

    @Override
    public String toString() {
        switch(this.direction){
            case MoveDirections.FORWARD -> {
                return "N";
            }
            case MoveDirections.BACKWARD -> {
                return "S";
            }
            case MoveDirections.LEFT -> {
                return "W";
            }
            case MoveDirections.RIGHT -> {
                return "E";
            }
            case  MoveDirections.FORWARD_RIGHT -> {
                return "NE";
            }
            case MoveDirections.BACKWARD_RIGHT -> {
                return "SE";
            }
            case MoveDirections.BACKWARD_LEFT -> {
                return "SW";
            }
            case MoveDirections.FORWARD_LEFT -> {
                return "NW";
            }
            default -> {
                return "";
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return energy == animal.energy && startEnergy == animal.startEnergy && numberOfMoves == animal.numberOfMoves && age == animal.age && paceOfAging == animal.paceOfAging && N == animal.N && children == animal.children;
    }

    @Override
    public int hashCode() {
        return Objects.hash(energy, startEnergy, numberOfMoves, age, paceOfAging, N, children);
    }

}
