package Classes;

import AbstractClasses.AbstractAnimal;
import Interfaces.PositionChangeObserver;

import java.util.Random;

public class Animal extends AbstractAnimal {
    private int energy;
    private final int startEnergy;
    private int numberOfMoves;
    private int age;
    private int paceOfAging;
    private int N;
    private int children;

    public Animal(Vector2D pos, int N, int energy,int age, int paceOfAging) {
        this.position = pos;
        this.N=N;
        Random rand = new Random();
        this.direction = direction.fromRadians(rand.nextInt(8) * 45);
        this.genes = new Genes(N);
        this.energy = energy;
        this.numberOfMoves = 0;
        this.paceOfAging = paceOfAging;
        this.startEnergy = energy;
        this.children = 0;
    }

    public Animal(Vector2D pos, int energy, Genes parentsGenes, int paceOfAging) {
        this.position = pos;
        this.energy = energy;
        this.startEnergy = energy;
        this.numberOfMoves = 0;
        this.age = 0;
        this.genes = new Genes(numberOfMoves);
        this.paceOfAging = paceOfAging;
        this.N = genes.getSize();
        this.children = 0;
        Random rand = new Random();
        this.direction = direction.fromRadians(rand.nextInt(8) * 45);
    }

    public void incrementChild(){
        children++;
    }

    public int getChildren() {
        return children;
    }

    @Override
    public boolean canMove() {
        numberOfMoves++;
        energy -= 1;
        Random rand = new Random();
        int randomNum = rand.nextInt(100);
        if(randomNum < Math.min(age,79)){
            move();
            return true;
        };
        return false;
    }

    public void aging(){
        if (numberOfMoves % paceOfAging == 0) {
            age ++;
        }
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    boolean canMate(){
        if(energy >= startEnergy/2){
            return true;
        }
        return false;
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
                    if (randomNum == 0){
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }


    public Animal copulate(Animal mate){
        int sumEnergy = energy + mate.getEnergy();
        energy = energy/2;
        Random rand = new Random();
        int side = rand.nextInt(2);// 0 - lewa, 1 - prawa
        Genes newGenes = new Genes();
        if(energy > mate.getEnergy()){
            int n;
            int m;
            if (side == 0){
                n = genes.getSize() * (energy/sumEnergy);
                for (int i = 0; i < n; i++) {
                    newGenes.addGene(genes.getGenes().get(i));
                }
                m = mate.genes.getSize() - (mate.genes.getSize() * (mate.getEnergy()/sumEnergy));
                for (int j = m; j < mate.genes.getSize(); j++) {
                    newGenes.addGene(genes.getGenes().get(j));
                }
            }
            else{
                n = genes.getSize() - (genes.getSize() * (energy/sumEnergy));
                for (int i = n; i < genes.getSize(); i++) {
                    newGenes.addGene(genes.getGenes().get(i));
                }
                m = mate.genes.getSize() * (mate.getEnergy()/sumEnergy);
                for (int j = 0; j < m; j++) {
                    newGenes.addGene(genes.getGenes().get(j));
                }
            }
        }
        mate.setEnergy(mate.getEnergy()/2);
        children ++;
        mate.incrementChild();
        newGenes.setCurrentGene();
        return new Animal(position,energy,newGenes,paceOfAging);
    };


    @Override
    public void addObserver(PositionChangeObserver observer) {

    }
}
