/*
 * Class: CPSC 427
 * Team Member 1: Maxwell Sherman
 * Team Member 2: Vincent Lombardi
 * Submitted by Maxwell Sherman
 * GU Username: msherman3
 * File Name: TSPTst.java
 * Uses the genetic algorithm to solve the travelling salesperson problem
 * Reference: http://www.cs.gonzaga.edu/faculty/depalma/handouts/cpsc427/ga-depalma-chapter.pdf
 * Usage: java WordGuess params.dat 0
 *     Where params.dat is the parameters file, and 0 is the type of algorithm (see TSP.java)
 */

import java.io.FileNotFoundException;

public class TSPTst
{
    public static void main(String[] args)
    {
        try
        {
            TSP tsp = new TSP(args[0], Integer.parseInt(args[1]));
            System.out.println();
            tsp.DisplayParams();
            System.out.println();
            System.out.println("Type\t# Generations\tCircuit Produced\tCost of Circuit");

            System.out.print(args[1] + "\t");
            tsp.Evolve();
            for (int i = 1; i < 4; i++)
            {
                tsp = new TSP(args[0], Integer.parseInt(args[1]));
                System.out.print(args[1] + "\t");
                tsp.Evolve();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
