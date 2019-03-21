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

public class TSP extends GA{


    private String WG_target;
    private int[][] distance_table;

    public TSP(String filename, String target) throws FileNotFoundException {

        super(filename, target);
        WG_target = target;
        GA_numGenes = WG_target.length();
        if (WG_target.length() != GA_numGenes)
        {
            System.out.println("Error: Target size differs from number of genes");
            DisplayParams();
            System.exit(1);
        }


        Scanner input = new Scanner(new File("TSPInput.txt"));
        int[][] dataMatrix = new int[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                dataMatrix[x][y] = input.nextInt();
            }
        }
        distance_table   = dataMatrix;

    InitPop();

    }


    public void InitPop()
    {
        super.InitPop();
        ComputeCost();
        SortPop();
        TidyUp();
    }

    public void DisplayParams()
    {
        System.out.print("Target: ");
        System.out.println(WG_target);
        super.DisplayParams();
    }



    protected void ComputeCost()
    {

        for (int i = 0; i < GA_pop.size(); i++)
        {
            int cost = 0;
            Chromosome chrom = GA_pop.remove(i);
            for (int j = 0; j < GA_numGenes; j++)
                if (chrom.GetGene(j) != WG_target.charAt(j))
                    cost++;
            chrom.SetCost(cost);
            GA_pop.add(i,chrom);
        }
        
        /**
        for (int i = 0; i < GA_pop.size(); i++)
        {
            int cost = 0;
            Chromosome chrom = GA_pop.remove(i);
            for (int j = 0; j < GA_numGenes - 1; j++) {
                int x = (int) chrom.GetGene(j) - 97;
                int y = (int) chrom.GetGene(j + 1) - 97;
                cost += distance_table[x][y];
            }
            System.out.println();
            chrom.SetCost(cost);
            GA_pop.add(i,chrom);
        }
        */

    }
}

