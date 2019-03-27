/*
 * Class: CPSC 427
 * Team Member 1: Maxwell Sherman
 * Team Member 2: Vincent Lombardi
 * Submitted by Maxwell Sherman
 * GU Username: msherman3
 * File Name: Parameters.java
 * Contains parameters for genetic algorithm
 * Reference: Dr. Paul De Palma
 */

import java.io.Serializable;
public class Parameters implements Serializable
{
    private int P_numChromesI, P_numChromes, P_numGenes, P_numIterations;
    private double P_mutFact;

    public Parameters(int numChromesI,int numChromes, int numGenes, double mutFact,
                      int numIterations)
    {
        P_numChromesI   = numChromesI;
        P_numChromes    = numChromes;
        P_numGenes      = numGenes;
        P_mutFact       = mutFact;
        P_numIterations = numIterations;
    }

    public int GetNumChromesI()
    {
        return P_numChromesI;
    }

    public int GetNumChromes()
    {
        return P_numChromes;
    }

    public int GetNumGenes()
    {
        return P_numGenes;
    }

    public double GetMutFact()
    {
        return P_mutFact;
    }

    public int GetNumIterations()
    {
        return P_numIterations;
    }

}