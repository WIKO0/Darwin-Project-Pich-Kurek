import Classes.Animal;
import Classes.Genes;
import Classes.Vector2D;

public class World {
    public static void main(String[] args) {
        System.out.println("return 0");
        Vector2D position1 = new Vector2D(0,0);
        int size1 = 9;
        int energy1 = 200;
        int age1 = 10;
        int pace1 = 5;

        Animal animal1 = new Animal(position1,size1,energy1,age1,pace1);


        Vector2D position2 = new Vector2D(0,0);
        int size2 = 18;
        int energy2 = 100;
        int age2 = 10;
        int pace2 = 5;

        Animal animal2 = new Animal(position2,size2,energy2,age2,pace2);

        Genes kidGenes = animal1.createOffspringGene(animal2,0);

        System.out.println(animal1.getGenes().toString());
        System.out.println(animal2.getGenes().toString());
        System.out.println(kidGenes.toString());
    }
}
