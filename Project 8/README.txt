Find the following files:

1. SetParams.java (creates the parameter file)
2. GetParams.java (used interally by GA.java to read the parameter file and by SetParams.java to create it)
3. params.dat (the file created by running SetParams with a specific parameter set)
4. WordGuessTst.java (starts the process of guessing a word)
5. GA.java (The abstract genetic algorithm)
6. WordGuess.java (The application of the GA to word guessing)
7. Pair.java (The algorithm that decides which chromosomes will be mated)
8. Mate.java (The mating algorithm)

To Compile:
1. Copy all eight files to the same directory
2. Be sure that JDK is installed and accessible from the directory where the WordGuess files are found
3. Type: javac SetParams.java
4. Type: javac WordGuessTst.java

To create the parameter file.
1. Decide upon a parameter set, for example:
   --128 intitial population members
   --64 actural population members
   --5 size of the word to be guessed
   --.1 mutation factor
   --1000 maximum number of generations
2. Type: java SetParams params.dat 128 64 5 .1 1000

To run WordGuess:
1. Type: java WordGuessTst <parameter file> <word to be guessed>
    For example, if the parameter file is "params.dat" and the word to be guessed is "genetic"
    java WordGuessTst params.dat genetic
2. WordGuess displays the best guess from each generation until it guesses the word itself or 
   until the maximum number of generations is reached.

