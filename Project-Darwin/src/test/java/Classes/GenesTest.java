package Classes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GenesTest {
    @Test
    void testGeneConstructor(){
        int size = 10;
        Genes genes = new Genes(size);

        assertEquals(size, genes.getGenes().size());

        for(int i = 0; i < genes.getGenes().size(); i++){
            assertTrue(genes.getGenes().get(i) <= 7, "Gen " + i + " jest większy niż 7");
        }
    }
    @Test
    void testGeneConstructor2(){
        Genes genes = new Genes();
        assertEquals(0, genes.getGenes().size());
    }

    @Test
    void testAddGene(){
        Genes genes = new Genes();
        genes.addGene(1);
        genes.addGene(2);
        genes.addGene(3);

        assertEquals(3, genes.getGenes().size());
        assertEquals(genes.getGenes().get(0),1);
        assertEquals(genes.getGenes().get(1),2);
        assertEquals(genes.getGenes().get(2),3);
    }

    @Test
    void testNextGene(){
        Genes genes = new Genes();
        genes.addGene(1);
        genes.addGene(2);
        genes.addGene(3);

        if(genes.getCurrentGene() == 1){
            genes.nextGene();
            assertEquals(2,genes.getCurrentGene());
        }
        else if(genes.getCurrentGene() == 2){
            genes.nextGene();
            assertEquals(3,genes.getCurrentGene());
        }
        else{
            genes.nextGene();
            assertEquals(1,genes.getCurrentGene());
        }

    }
}
