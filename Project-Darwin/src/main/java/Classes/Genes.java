package Classes;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Genes {
    private ArrayList<Integer> genes;
    private int size;
    private int currentGene;

    public Genes(int size) {
        this.size = size;
        this.genes = new ArrayList<>();
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

    public ArrayList<Integer> getGenes() {
        return genes;
    }

    public void addGene(int gene) {
        genes.add(gene);
        size += 1;
    }

    public void setCurrentGene() {
        Random rand = new Random();
        if (size == 0) {size++;};
        this.currentGene = rand.nextInt(size);
    }
    public int getCurrentGene() {
        if(genes.size() <= currentGene) {return 0;}
        return genes.get(currentGene);
    }

    public void nextGene(){
        if(currentGene + 1 >= size){
            currentGene = 0;
        };
        currentGene+=1;
    }

    @Override
    public String toString() {
        String result = "";
        for(int i = 0; i < size; i++) {
            result += genes.get(i).toString() + " ";
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genes genes1 = (Genes) o;
        return size == genes1.size && Objects.equals(genes, genes1.genes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genes, size);
    }
}
