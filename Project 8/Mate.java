import java.util.*;
import java.lang.*;

public class Mate
{
    private    Chromosome MT_father, MT_mother, MT_child1, MT_child2;
    private    int MT_posChild1, MT_posChild2, MT_posLastChild,MT_posFather, MT_posMother,
            MT_numGenes, MT_numChromes;

    public Mate(ArrayList<Chromosome> population, int numGenes, int numChromes)
    {
        MT_numGenes     = numGenes;
        MT_numChromes   = numChromes;

        MT_posChild1    = population.size()/2;
        MT_posChild2    = MT_posChild1 + 1;
        MT_posLastChild= population.size() - 1;

        for (int i = MT_posLastChild; i >= MT_posChild1; i--)
            population.remove(i);

        MT_posFather = 0;
        MT_posMother = 1;
    }

    //Single-Point Crossover
    public ArrayList<Chromosome> SinglePointCrossover(ArrayList<Chromosome> population, int numPairs)
    {
        for (int j = 0; j < numPairs; j++)
        {
            MT_father       =  population.get(MT_posFather);
            MT_mother       =  population.get(MT_posMother);
            MT_child1       = new Chromosome(MT_numGenes);
            MT_child2       = new Chromosome(MT_numGenes);
            Random rnum     = new Random();
            int crossPoint  = rnum.nextInt(MT_numGenes);

            //left side
            for (int i = 0; i < crossPoint; i++)
            {
                MT_child1.SetGene(i, MT_father.GetGene(i));
                MT_child2.SetGene(i, MT_mother.GetGene(i));
            }

            //right side
            for (int i = crossPoint; i < MT_numGenes; i++)
            {
                MT_child1.SetGene(i, MT_mother.GetGene(i));
                MT_child2.SetGene(i, MT_father.GetGene(i));
            }

            population.add(MT_posChild1,MT_child1);
            population.add(MT_posChild2,MT_child2);

            MT_posChild1    = MT_posChild1 + 2;
            MT_posChild2    = MT_posChild2 + 2;
            MT_posFather    = MT_posFather + 2;
            MT_posMother    = MT_posMother + 2;
        }
        return population;
    }

    //Partially Matched Crossover
    public ArrayList<Chromosome> PartiallyMatchedCrossover(ArrayList<Chromosome> population, int numPairs)
    {
        for (int j = 0; j < numPairs; j++)
        {
            MT_father       =  population.get(MT_posFather);
            MT_mother       =  population.get(MT_posMother);
            MT_child1       = new Chromosome(MT_numGenes);
            MT_child2       = new Chromosome(MT_numGenes);
            Random rnum     = new Random();
            int crossPoint1 = rnum.nextInt(MT_numGenes);
            int crossPoint2 = rnum.nextInt(MT_numGenes);

            //copy mother to child1, father to child2
            for (int i = 0; i < MT_numGenes; i++)
            {
                MT_child1.SetGene(i, MT_mother.GetGene(i));
                MT_child2.SetGene(i, MT_father.GetGene(i));
            }

            //which values will be swapped
            char MT_mother_gene1 = MT_mother.GetGene(crossPoint1);
            char MT_mother_gene2 = MT_mother.GetGene(crossPoint2);
            char MT_father_gene1 = MT_father.GetGene(crossPoint1);
            char MT_father_gene2 = MT_father.GetGene(crossPoint2);

            if (MT_mother_gene1 != MT_father_gene1 &&
                    MT_mother_gene2 != MT_father_gene2)
            {
                //where the matching genes are in the other parent
                int MT_mother_loc1 = 0; //index of father gene1 in mother
                for (int i = 0; i < MT_mother.GetNumGenes(); i++)
                {
                    if (MT_mother.GetGene(i) == MT_father_gene1)
                    {
                        MT_mother_loc1 = i;
                        break;
                    }
                }
                int MT_mother_loc2 = 0; //index of father gene2 in mother
                for (int i = 0; i < MT_mother.GetNumGenes(); i++)
                {
                    if (MT_mother.GetGene(i) == MT_father_gene2)
                    {
                        MT_mother_loc2 = i;
                        break;
                    }
                }
                int MT_father_loc1 = 0; //index of mother gene1 in father
                for (int i = 0; i < MT_father.GetNumGenes(); i++)
                {
                    if (MT_father.GetGene(i) == MT_mother_gene1)
                    {
                        MT_father_loc1 = i;
                        break;
                    }
                }
                int MT_father_loc2 = 0; //index of mother gene1 in father
                for (int i = 0; i < MT_father.GetNumGenes(); i++)
                {
                    if (MT_father.GetGene(i) == MT_mother_gene2)
                    {
                        MT_father_loc2 = i;
                        break;
                    }
                }

                //swap gene1 in child1 and child2
                MT_child1.SetGene(crossPoint1, MT_father.GetGene(crossPoint1));
                MT_child2.SetGene(crossPoint1, MT_mother.GetGene(crossPoint1));

                //swap gene2 in child1 and child2
                MT_child1.SetGene(crossPoint2, MT_father.GetGene(crossPoint2));
                MT_child2.SetGene(crossPoint2, MT_mother.GetGene(crossPoint2));

                if (MT_child1.GetGene(MT_mother_loc1) != MT_child2.GetGene(crossPoint1) &&
                        MT_child1.GetGene(MT_mother_loc2) != MT_child2.GetGene(crossPoint2) &&
                        MT_child2.GetGene(MT_father_loc1) != MT_child1.GetGene(crossPoint1) &&
                        MT_child2.GetGene(MT_father_loc2) != MT_child1.GetGene(crossPoint2))
                {
                    //swap the other versions of those genes
                    MT_child1.SetGene(MT_mother_loc1, MT_child2.GetGene(crossPoint1));
                    MT_child1.SetGene(MT_mother_loc2, MT_child2.GetGene(crossPoint2));
                    MT_child2.SetGene(MT_father_loc1, MT_child1.GetGene(crossPoint1));
                    MT_child2.SetGene(MT_father_loc2, MT_child1.GetGene(crossPoint2));
                }
            }

            /*
            System.out.print(crossPoint1);
            System.out.print(", ");
            System.out.println(crossPoint2);
            System.out.print("mother: ");
            MT_mother.DisplayGenes();
            System.out.println();
            System.out.print("father: ");
            MT_father.DisplayGenes();
            System.out.println();
            System.out.print("child1: ");
            MT_child1.DisplayGenes();
            System.out.println();
            System.out.print("child2: ");
            MT_child2.DisplayGenes();
            System.out.println("\n");
            */

            population.add(MT_posChild1,MT_child1);
            population.add(MT_posChild2,MT_child2);

            MT_posChild1 = MT_posChild1 + 2;
            MT_posChild2 = MT_posChild2 + 2;
            MT_posFather = MT_posFather + 2;
            MT_posMother = MT_posMother + 2;
        }
        return population;
    }

    //Ordered Crossover
    public ArrayList<Chromosome> OrderedCrossover(ArrayList<Chromosome> population, int numPairs) {
        for (int j = 0; j < numPairs; j++)
        {
            MT_father = population.get(MT_posFather);
            MT_mother = population.get(MT_posMother);
            MT_child1 = new Chromosome(MT_numGenes);
            MT_child2 = new Chromosome(MT_numGenes);

            Random rnum = new Random();
            //calculates crossover points so that they are an acceptable distance from each other
            //and the beginning and end of the list
            int crossPoint1 = rnum.nextInt(MT_numGenes - 2);
            if (crossPoint1 == 0)
            {
                crossPoint1 = 2;
            }
            int difference = MT_numGenes - crossPoint1;
            if (difference > 5)
            {
                difference = 5;
            }

            if (difference < 2)
            {
                difference = 2;
            }
            int x = rnum.nextInt(difference) + 1;
            int crossPoint2 = crossPoint1 + x; // todo simplify this
            ArrayList<Character> child1 = new ArrayList<>();
            ArrayList<Character> child2 = new ArrayList<>();
            ArrayList<Character> temp1 = new ArrayList<>();
            ArrayList<Character> temp2 = new ArrayList<>();
            //puts genes into an array
            for (int i = 0; i < MT_numGenes; i++)
            {
                child1.add(MT_father.GetGene(i));
                child2.add(MT_mother.GetGene(i));
            }
            //puts elements that need to be swapped into separate array lists
            for (int i = crossPoint1; i < crossPoint2; i++)
            {
                temp1.add(MT_mother.GetGene(i));
                //child1.remove(MT_father.GetGene(i));
                temp2.add(MT_father.GetGene(i));
                //child2.remove(MT_mother.GetGene(i));
            }

            //deletes repeat elements from child arrays
            for (int i = 0; i < temp1.size(); i++)
            {
                if(child1.contains(temp1.get(i))){
                    //System.out.println(child1);
                    child1.remove(temp1.get(i));
                    //System.out.println(child1);
                }
                if(child2.contains(temp2.get(i))){
                    child2.remove(temp2.get(i));
                }
            }

            int count1 = 0;
            int count2 = 0;

            //puts all elements before crossoverpoint 1 back into child1 and child2
            for (int i = 0; i < crossPoint1; i++)
            {
                MT_child1.SetGene(count1, child1.get(i));
                MT_child2.SetGene(count2, child2.get(i));
                count1++;
                count2++;
            }
            //puts crossover elements into child1 and child2
            for (int i = 0; i < temp1.size(); i++)
            {
                MT_child1.SetGene(count1, temp1.get(i));
                MT_child2.SetGene(count2, temp2.get(i));
                count1++;
                count2++;
            }

            //puts all elements after crossover points back into child1 and child2
            for (int i = crossPoint1; i < child1.size(); i++)
            {
                MT_child1.SetGene(count1, child1.get(i));
                MT_child2.SetGene(count2, child2.get(i));
                count1++;
                count2++;
            }

            /*
            System.out.println("child1");
            System.out.println("");
            for (int i = 0; i < 8; i++){
               System.out.print(MT_child1.GetGene(i));
            }
            System.out.println("");
            System.out.println("child2");
            for (int i = 0; i < 8; i++){
               System.out.print(MT_child2.GetGene(i));
            }
            System.out.println("");
            */

            population.add(MT_posChild1,MT_child1);
            population.add(MT_posChild2,MT_child2);
            MT_posChild1    = MT_posChild1 + 2;
            MT_posChild2    = MT_posChild2 + 2;
            MT_posFather    = MT_posFather + 2;
            MT_posMother    = MT_posMother + 2;
        }
     return population;
    }

    // Cycle Crossover
    public ArrayList<Chromosome> CycleCrossover(ArrayList<Chromosome> population, int numPairs) {
        for (int j = 0; j < numPairs; j++)
        {
            MT_father = population.get(MT_posFather);
            MT_mother = population.get(MT_posMother);
            MT_child1 = new Chromosome(MT_numGenes);
            MT_child2 = new Chromosome(MT_numGenes);
            ArrayList<Character> child1 = new ArrayList<>();
            ArrayList<Character> child2 = new ArrayList<>();

            //puts parent elements into arrays
            for (int i = 0; i < MT_numGenes; i++)
            {
                child1.add(MT_father.GetGene(i));
                child2.add(MT_mother.GetGene(i));
            }
            //perform initial swap
            char temp;
            temp = child1.get(0);
            child1.set(0, child2.get(0));
            child2.set(0, temp);
            int index = 0;

            //cycle through child lists swapping repeat elements till there are none
            while(index != -1)
            {
                temp = child1.get(index);
                child1.set(index, child2.get(index));
                child2.set(index, temp);
                index = repeatChecker(child1);
            }
            //put elements back into child object
            for(int i = 0; i < child1.size(); i++)
            {
                MT_child1.SetGene(i, child1.get(i));
                MT_child2.SetGene(i, child2.get(i));
            }

            /*
            System.out.println("child1");
            //System.out.println("");
            for (int i = 0; i < 8; i++){
                System.out.print(MT_child1.GetGene(i));
            }
            System.out.println("");
            System.out.println("child2");
            for (int i = 0; i < 8; i++){
                System.out.print(MT_child2.GetGene(i));
            }
            System.out.println("");
            */

            population.add(MT_posChild1,MT_child1);
            population.add(MT_posChild2,MT_child2);
            MT_posChild1    = MT_posChild1 + 2;
            MT_posChild2    = MT_posChild2 + 2;
            MT_posFather    = MT_posFather + 2;
            MT_posMother    = MT_posMother + 2;
        }
        return population;
    }

    //helper function that checks for repeats
    private int repeatChecker(ArrayList<Character> input)
    {
        for (int x = 0; x < input.size(); x++)
        {
            for (int y = x + 1; y < input.size(); y++)
            {
                if (input.get(x) == input.get(y))
                {
                    return y;
                }
            }
        }
        return -1;
    }
}
