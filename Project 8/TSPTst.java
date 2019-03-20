import java.util.ArrayList;

public class TSPTst
{
    public static void main(String[] args)
    {
        ArrayList<Chromosome> l = new ArrayList<Chromosome>();
        l.add(new Chromosome(6));
        l.get(0).SetGene(0, '4');
        l.get(0).SetGene(1, '1');
        l.get(0).SetGene(2, '5');
        l.get(0).SetGene(3, '3');
        l.get(0).SetGene(4, '2');
        l.get(0).SetGene(5, '6');
        l.add(new Chromosome(6));
        l.get(1).SetGene(0, '3');
        l.get(1).SetGene(1, '4');
        l.get(1).SetGene(2, '6');
        l.get(1).SetGene(3, '2');
        l.get(1).SetGene(4, '1');
        l.get(1).SetGene(5, '5');
        l.add(new Chromosome(6));
        l.get(2).SetGene(0, '4');
        l.get(2).SetGene(1, '1');
        l.get(2).SetGene(2, '5');
        l.get(2).SetGene(3, '3');
        l.get(2).SetGene(4, '2');
        l.get(2).SetGene(5, '6');
        l.add(new Chromosome(6));
        l.get(3).SetGene(0, '3');
        l.get(3).SetGene(1, '4');
        l.get(3).SetGene(2, '6');
        l.get(3).SetGene(3, '2');
        l.get(3).SetGene(4, '1');
        l.get(3).SetGene(5, '5');

        System.out.println("START");
        for (Chromosome c : l)
        {
            c.DisplayGenes();
            System.out.println();
        }
        System.out.println();

        Mate mate = new Mate(l, 6, 4);
        l = mate.PartiallyMatchedCrossover(l, 1);


        System.out.println("END");
        for (Chromosome c : l)
        {
            c.DisplayGenes();
            System.out.println();
        }
        System.out.println();
    }
}
