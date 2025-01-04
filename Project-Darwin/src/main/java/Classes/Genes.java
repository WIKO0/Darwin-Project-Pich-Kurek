package Classes;

import java.util.ArrayList;
import java.util.Random;

public class Genes {
    private ArrayList<Integer> genes = new ArrayList<Integer>();
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

    public int getSize() {
        return size;
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
