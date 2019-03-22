/*
 * Class: CPSC 427
 * Team Member 1: Maxwell Sherman
 * Team Member 2: Vincent Lombardi
 * Submitted by Maxwell Sherman
 * GU Username: msherman3
 * File Name: Pair.java
 * Contains pairing algorithms for genetic algorithm
 * Reference: https://docs.google.com/presentation/d/17fC96aTLSxBnPXLxcWwIBXQ_j2tO_ke-vIrRZCnB930/edit#slide=id.p22
 */

import java.util.*;
import java.lang.*;

public class Pair
{
    private ArrayList<Chromosome> PR_pop;

    public Pair(ArrayList<Chromosome> population)
    {
        PR_pop = population;
    }

    public int SimplePair()
    {
        return (PR_pop.size() / 4);//the number of mating pairs
    }

    public ArrayList<Chromosome> TournamentPair()
    {
        ArrayList<Chromosome> matingPairs = new ArrayList<>();
        Random rnum = new Random();
        int subsetSize = rnum.nextInt((PR_pop.size() - 2) + 1) + 2;
        while (matingPairs.size() < 32)
        {
            for(int count = 0; count < 2; count++)
            {
                ArrayList<Chromosome> tempList = new ArrayList<>();
                for (int x = 0; x < subsetSize; x++)
                {
                    Chromosome temp = PR_pop.get(rnum.nextInt(PR_pop.size()));
                    tempList.add(temp);
                }
                matingPairs.add(tempList.get(rnum.nextInt(tempList.size())));
            }
        }
        return matingPairs;
    }
}
