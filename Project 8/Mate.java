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
            MT_child1.SetGene(crossPoint1, MT_mother.GetGene(crossPoint1));
            MT_child2.SetGene(crossPoint1, MT_father.GetGene(crossPoint1));

            //swap gene2 in child1 and child2
            MT_child1.SetGene(crossPoint2, MT_mother.GetGene(crossPoint2));
            MT_child2.SetGene(crossPoint2, MT_father.GetGene(crossPoint2));

            //swap the other versions of those genes
            MT_child1.SetGene(MT_mother_loc1, MT_child2.GetGene(crossPoint1));
            MT_child1.SetGene(MT_mother_loc2, MT_child2.GetGene(crossPoint2));
            MT_child2.SetGene(MT_father_loc1, MT_child1.GetGene(crossPoint1));
            MT_child2.SetGene(MT_father_loc2, MT_child1.GetGene(crossPoint2));

            population.add(MT_posChild1,MT_child1);
            population.add(MT_posChild2,MT_child2);

            MT_posChild1    = MT_posChild1 + 2;
            MT_posChild2    = MT_posChild2 + 2;
            MT_posFather    = MT_posFather + 2;
            MT_posMother    = MT_posMother + 2;
        }
        return population;
    }
}