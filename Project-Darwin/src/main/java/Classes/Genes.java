package Classes;

import java.util.ArrayList;
import java.util.Random;

public class Genes {
    private ArrayList<Integer> genes;
    private int size;
    private int currentGene;

    public Genes(int size) {
        this.size = size;
        Random rand = new Random();
        for(int i = 0; i < size; i++) {
            genes.add(rand.nextInt(8));
        }
        this.currentGene = rand.nextInt(size);
    }

    public Genes() {
        this.genes = new ArrayList<>();
        this.size = 0;
        this.currentGene = 0;
    }

    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public ArrayList<Integer> getGenes() {
        return genes;
    }

    public void addGene(int gene) {
        genes.add(gene);
        size += 1;
    }

    public void setCurrentGene() {
        Random rand = new Random();
        this.currentGene = rand.nextInt(size);
    }

    public int getCurrentGene() {
        return genes.get(currentGene);
    }

    public void nextGene(){
        if(currentGene + 1 >= size){
            currentGene = 0;
        };
        currentGene+=1;
    }

}
