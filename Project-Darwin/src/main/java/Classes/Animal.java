package Classes;

import AbstractClasses.AbstractAnimal;


import EnumClasses.MoveDirections;
import Interfaces.MapElement;
import Interfaces.PositionChangeObserver;
import java.util.Objects;
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
    private int minMutation;
    private int maxMutation;
    private int EnergyRequirment;
    private int EnergyUsedToMate;
    private int grassEaten;
    private boolean isDead = false;

    public Animal(Vector2D pos, int N, int energy,int age, int paceOfAging,int minMutation,int maxMutation,int EnergyRequirment,int EnergyUsedToMate) {
        this.position = pos;
        this.N=N;
        Random rand = new Random();
        this.direction = MoveDirections.fromRadians(rand.nextInt(8) * 45);
        this.genes = new Genes(N);
        this.energy = energy;
        this.numberOfMoves = 0;
        this.paceOfAging = paceOfAging;
        this.startEnergy = energy;
        this.children = 0;
        this.age = age;
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
        this.EnergyRequirment = EnergyRequirment;
        this.EnergyUsedToMate = EnergyUsedToMate;
        this.grassEaten = 0;
    }

    public Animal(Vector2D pos, int energy, Genes parentsGenes, int paceOfAging,int minMutation,int maxMutation,int EnergyRequirment,int EnergyUsedToMate) {
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
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
        this.EnergyRequirment = EnergyRequirment;
        this.EnergyUsedToMate = EnergyUsedToMate;
        this.grassEaten = 0;
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
        this.incrementNumberOfMoves();
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
        if(this.energy >= this.EnergyRequirment){
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
        n = (int)(Math.ceil(this.genes.getSize() * (energyRatioAnimal)));
        m = (int)(Math.floor(mate.getGenes().getSize() * (energyRatioMate)));
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

                    for (int i = 0; i < n; i++) {
                        newGenes.addGene(this.genes.getGenes().get(i));
                    }
                    ;
                    for (int j = (mateGenesSize-m); j< mateGenesSize; j++) {
                        newGenes.addGene(mate.getGenes().getGenes().get(j));
                    }


            }
        }

        return newGenes;
    };


    public Animal copulate(Animal mate){
        Random rand = new Random();
//        System.out.println("Geny rodzica 1: ");
//        System.out.println(this.genes.getGenes().size());
//        System.out.println("Geny rodzica 2: ");
//        System.out.println(mate.getGenes().getGenes().size());
        int side = rand.nextInt(2);// 0 - lewa, 1 - prawa
        Genes newGenes = this.createOffspringGene(mate, side);
        mate.setEnergy(Math.min(mate.getEnergy() - EnergyUsedToMate,0));
        this.energy = Math.max(this.energy - EnergyUsedToMate,0);
        this.children ++;
        mate.incrementChild();
        newGenes.setCurrentGene();
        int childEnergy = this.energy + mate.getEnergy();
        Animal child = new Animal(position,childEnergy,newGenes,paceOfAging,this.minMutation,this.maxMutation,this.EnergyRequirment,this.EnergyUsedToMate);
//        System.out.println("geny dziecka: ");
//        System.out.println(child.getGenes().getGenes().size());
        child.getGenes().mutates(this.minMutation,this.maxMutation);

        return child;
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

    public void incrementGrassEaten(){
        this.grassEaten += 1;
    }

    public int getGrassEaten(){
        return this.grassEaten;
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

    public boolean getIsDead(){
        return this.isDead;
    }

    public void setIsDead(boolean isDead){ this.isDead = isDead;}

}
