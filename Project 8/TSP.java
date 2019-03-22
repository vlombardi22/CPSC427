/**
 * this is supposed to read data from a matrix
 * TSP stands for traveling sales person
 */
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.*;
import java.lang.*;

public class TSP extends GA
{
    private int[][] GA_distance_table;
    private int GA_config;
    private final int CIRCUIT_LENGTH = 8; //length of Hamiltonian circuit

    //config can be 0, 1, 2, or 3
    //0: top-down pairing, partially-matched crossover mating
    //1: top-down pairing, ordered crossover mating
    //2: tournament pairing, partially-matched crossover mating
    //3: tournament pairing, ordered crossover mating

    public TSP(String filename, int config) throws FileNotFoundException
    {
        super(filename, "");
        GA_numGenes = CIRCUIT_LENGTH;
        GA_config = config;

        Scanner input = new Scanner(new File("TSPInput.txt"));
        int[][] dataMatrix = new int[8][8];
        for (int x = 0; x < CIRCUIT_LENGTH; x++)
        {
            for (int y = 0; y < CIRCUIT_LENGTH; y++)
            {
                dataMatrix[x][y] = input.nextInt();
            }
        }
        GA_distance_table   = dataMatrix;

    InitPop();
    }

    public void DisplayParams()
    {
        super.DisplayParams();
        System.out.print("Pairing Algorithm: ");
        if (GA_config / 2 == 0)
        {
            System.out.println("Top-Down");
        }
        else
        {
            System.out.println("Tournament");
        }
        System.out.print("Mating algorithm: ");
        if (GA_config % 2 == 0)
        {
            System.out.println("Partially-Matched Crossover");
        }
        else
        {
            System.out.println("Ordered Crossover");
        }
    }

    protected void DisplayChromosome(Chromosome chrome)
    {
        chrome.DisplayGenes();
        System.out.print("\t\t");
        System.out.print(chrome.GetCost());
    }

    protected void Mutate()
    {
        int totalGenes  = (GA_numGenes * GA_numChromes);
        int numMutate   = (int) (totalGenes * GA_mutFact);
        Random rnum     = new Random();

        for (int i = 0; i < numMutate; i++)
        {
            //position of chromosome to mutate--but not the first one
            //the number generated is in the range: [1..GA_numChromes)

            int chromMut = 1 + (rnum.nextInt(GA_numChromes - 1));
            chromMut = chromMut / 2; // I added this line for tournament pairing
            int geneMut = rnum.nextInt(GA_numGenes); //pos of mutated gene
            int geneMut2 = rnum.nextInt(GA_numGenes);
            if (geneMut == geneMut2)
            {
                if (geneMut == 0)
                {
                    geneMut2++;
                }
                else
                {
                    geneMut2--;
                }
            }

            Chromosome newChromosome = GA_pop.remove(chromMut); //get chromosome

            // swaps the genes
            char temp = newChromosome.GetGene(geneMut);
            newChromosome.SetGene(geneMut,newChromosome.GetGene(geneMut2));
            newChromosome.SetGene(geneMut2,temp);
            GA_pop.add(newChromosome); //add mutated chromosome at the end
        }
    }

    public void InitPop()
    {
        Random rnum = new Random();
        char letter;
        for (int index = 0; index < GA_numChromesInit; index++)
        {
            Chromosome Chrom = new Chromosome(GA_numGenes);
            ArrayList<Character> genePool = new ArrayList<>();
            int unusedGenes = GA_numGenes;
            for(int counter = 0; counter < GA_numGenes; counter++){ // this prevents repeate
                genePool.add(((char) (counter + 97)));
            }

            for (int j = 0; j < GA_numGenes; j++)
            {
                //letter = (char) (rnum.nextInt(26) + 97); //97 is the value 'a'
                //letter = (char) (rnum.nextInt(8) + 97);
                int rindex = rnum.nextInt(unusedGenes);
                letter = genePool.get(rindex);
                Chrom.SetGene(j, letter);
                genePool.remove(rindex);
                unusedGenes--;
            }
            Chrom.SetCost(0);
            GA_pop.add(Chrom);
        }

        ComputeCost();
        SortPop();
        TidyUp();
    }

    protected void ComputeCost()
    {
        for (int i = 0; i < GA_pop.size(); i++)
        {
            int cost = 0;
            Chromosome chrom = GA_pop.remove(i);
            for (int j = 0; j < chrom.GetNumGenes() - 1; j++) //counting distances btwn letters
            {
                //converting chars a, b, c... to ints 0, 1, 2... via ASCII values
                //used as indices in the table
                int firstIndex = ((int) chrom.GetGene(j)) - 'a';
                int secondIndex = ((int) chrom.GetGene(j + 1)) - 'a';
                cost += GA_distance_table[firstIndex][secondIndex];
            }
            cost += GA_distance_table[((int) chrom.GetGene(0)) - 'a']
                    [((int) chrom.GetGene(CIRCUIT_LENGTH - 1)) - 'a']; //compute distance btwn start and end
            chrom.SetCost(cost);
            GA_pop.add(i,chrom);
        }
    }

    protected void Evolve()
    {
        int iterationCt = 0;
        Pair pairs      = new Pair(GA_pop);
        int numPairs;
        if (GA_config / 2 == 0)
        {
            numPairs = pairs.SimplePair();
        }
        else
        {
            numPairs                  = GA_pop.size();
            GA_pop                    = pairs.TournamentPair();
        }
        Chromosome bestChromosome = new Chromosome(8); //best option, must be initialized to something
        bestChromosome.SetCost(50000); //50000 is just a really big number so that any cost is lower
        int bestChromosomeIteration = 0;

        while (iterationCt < GA_numIterations)
        {
            Mate mate = new Mate(GA_pop,GA_numGenes,GA_numChromes);
            if (GA_config % 2 == 0)
            {
                //GA_pop = mate.PartiallyMatchedCrossover(GA_pop,numPairs);
                GA_pop = mate.CycleCrossover(GA_pop, numPairs);
            }
            else
            {
                GA_pop = mate.OrderedCrossover(GA_pop,numPairs);
            }

            Mutate();
            ComputeCost();
            SortPop();

            Chromosome chrome = GA_pop.get(0); //get the best option
            //System.out.println(chrome.GetCost() + ", " + bestChromosome.GetCost());
            if (iterationCt == 0 || chrome.GetCost() < bestChromosome.GetCost())
            {
                bestChromosome = new Chromosome(chrome);
                bestChromosomeIteration = iterationCt;
            }

            //DisplayBest(iterationCt); //print it

            ++iterationCt;
        }

        /*
        System.out.print("Best option: ");
        System.out.print(bestChromosomeIteration);
        System.out.print("\t");
        DisplayChromosome(bestChromosome);
        System.out.println();
        */
        System.out.print(bestChromosomeIteration + "\t\t");
        DisplayChromosome(bestChromosome);
        System.out.println();
    }
}

