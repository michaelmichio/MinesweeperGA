import java.util.Scanner;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        /**
         *
         * Example puzzle: (size = 5 x 5)
         * - - 4 4 4
         * 2 - 4 - -
         * - - 3 - 5
         * - - - 3 -
         * 3 - - - -
         *
         * String input: (length = 25)
         * --4442-4----3-5---3-3----
         *
         */
        System.out.print("String puzzle (length must n*n): ");
        String strPuzzle = scanner.next();

        // check if the puzzle is perfectly square
        int n = (int) Math.sqrt(strPuzzle.length());

        if(Math.pow(n, 2) == strPuzzle.length()) {

            System.out.print("Generation count (1 to n): ");
            int generationCount = scanner.nextInt();

            System.out.print("Population size (1 to n): ");
            int populationSize = scanner.nextInt();

            System.out.print("Crossover rate (0.0 to 1.0): ");
            double crossoverRate = scanner.nextDouble();

            System.out.print("Mutation rate (0.0 to 1.0): ");
            double mutationRate = scanner.nextDouble();

            System.out.print("Elitism rate (0.0 to 1.0): ");
            double elitismRate = scanner.nextDouble();

            // check if the input has a valid value
            if(generationCount >= 0 && populationSize >= 0 &&
                    crossoverRate >=  0 && crossoverRate <= 1 &&
                    mutationRate >= 0 && mutationRate <= 1 &&
                    elitismRate >= 0 && elitismRate <= 1) {
                MinesweeperGA minesweeperGA = new MinesweeperGA(generationCount, populationSize, crossoverRate, mutationRate, elitismRate);
                minesweeperGA.generate(strPuzzle);
            }
            else {
                System.out.println("Invalid value");
            }
        }
        else {
            System.out.println("Puzzle size is " + strPuzzle.length() + " (not square)");
        }

        scanner.close();
    }

}
