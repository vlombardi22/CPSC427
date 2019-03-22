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

            System.out.print(0 + "\t");
            tsp.Evolve();
            for (int i = 1; i < 4; i++)
            {
                tsp = new TSP(args[0], Integer.parseInt(args[1]));
                System.out.print(i + "\t");
                tsp.Evolve();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
