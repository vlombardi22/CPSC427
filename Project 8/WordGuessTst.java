/*
 * Class: CPSC 427
 * Team Member 1: Maxwell Sherman
 * Team Member 2: Vincent Lombardi
 * Submitted by Maxwell Sherman
 * GU Username: msherman3
 * File Name: WordGuessTst.java
 * Uses the genetic algorithm to generate words until a target is reached
 * Reference: http://www.cs.gonzaga.edu/faculty/depalma/handouts/cpsc427/ga-depalma-chapter.pdf
 * Usage: java WordGuess params.dat alexandria
 *     Where params.dat is the parameters file, and alexandria is the target word
 */

import java.lang.*;

public class WordGuessTst
{

    public static void main(String args[])
    {

        WordGuess WG1 = new WordGuess(args[0],args[1]);

        System.out.println();
        //WG1.DisplayParams(); Uncomment to display the contents of the parameter file
        //WG1.DisplayPop(); Uncomment to display the population before evolution
        WG1.Evolve();
        //WG1.DisplayPop(); Uncomment to display the population after evolution
        System.out.println();
    }
}