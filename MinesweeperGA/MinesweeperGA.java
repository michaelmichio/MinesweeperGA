import java.util.Random;

public class MinesweeperGA {

    public int generationCount;
    public int populationSize;
    public double crossoverRate;
    public double mutationRate;
    public double elitismRate;

    public String strPuzzle;

    public Population population;

    public Individual fittest;
    public Individual secondFittest;

    public Individual bestFit;

    public MinesweeperGA(int generationCount, int populationSize, double crossoverRate, double mutationRate, double elitismRate) {
        this.generationCount = generationCount;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitismRate = elitismRate;
    }

    public void generate(String strPuzzle) {
        this.strPuzzle = strPuzzle;
        int i = 0;
        this.population = new Population(populationSize, this.strPuzzle);
        this.population.calculateFitness();
        this.bestFit = this.population.getFittest();
        System.out.println("Generation: " + i + " Fittest: " + this.population.fittest);
        while(this.population.fittest <= 100 && i < this.generationCount) {
            i++;

            this.selection();
            this.crossover();
            this.mutation();
            this.elitism();

            this.population.calculateFitness();

            if(this.bestFit.fitness < this.fittest.fitness) {
                this.bestFit = this.fittest;
            }

            System.out.println("Generation: " + i + " Fittest: " + this.population.fittest);

//            System.out.println("Generation: " + i + " Fittest: " + this.population.fittest + "%");
//            for(int j = 0; j < this.population.getFittest().solutions.length; j++) {
//                for(int k = 0; k < this.population.getFittest().solutions[0].length; k++) {
//                    System.out.print(this.population.getFittest().solutions[j][k]);
//                }
//                System.out.println();
//            }
        }

        System.out.println("Solution found in generation " + i);
        this.printResult();
    }

    public void selection() {
        this.fittest = this.population.getFittest();
        this.secondFittest = this.population.getSecondFittest();
    }

    public void crossover() {
        Random random = new Random();
        int number = random.nextInt(100);

        if(number <= (int) (this.crossoverRate * 100)) {
//            int crossOverPoint = random.nextInt(this.population.individuals[0].genes.length);
//
//            for (int i = 0; i < crossOverPoint; i++) {
//                int temp = this.fittest.genes[i];
//                this.fittest.genes[i] = this.secondFittest.genes[i];
//                this.secondFittest.genes[i] = temp;
//            }
            int length1 = this.fittest.genes.length / 2;
            int[] temp1 = new int[this.fittest.genes.length];
            int[] temp2 = new int[this.fittest.genes.length];
            for(int i = 0; i < length1; i++) {
                temp1[i] = this.fittest.genes[i];
                temp2[i] = this.secondFittest.genes[i];
            }
            for(int i = length1; i < this.fittest.genes.length; i++) {
                temp1[i] = this.secondFittest.genes[i];
                temp2[i] = this.fittest.genes[i];
            }
            for(int i = length1; i < this.fittest.genes.length; i++) {
                this.fittest.genes[i] = temp1[i];
                this.secondFittest.genes[i] = temp2[i];
            }
        }
    }

    public void mutation() {
        Random random = new Random();
        int number = random.nextInt(100);

        if(number <= (int) (this.mutationRate * 100)) {
            int mutationPoint = random.nextInt(this.population.individuals[0].genes.length);

            if (this.fittest.genes[mutationPoint] == 0) {
                this.fittest.genes[mutationPoint] = 1;
            }
            else {
                this.fittest.genes[mutationPoint] = 0;
            }

            mutationPoint = random.nextInt(population.individuals[0].genes.length);

            if (this.secondFittest.genes[mutationPoint] == 0) {
                this.secondFittest.genes[mutationPoint] = 1;
            }
            else {
                this.secondFittest.genes[mutationPoint] = 0;
            }
        }
    }

    public void elitism() {
        Random random = new Random();
        int number = random.nextInt(100);

        if(number <= (int) (this.elitismRate * 100)) {
            this.addFittestOffspring();
        }
        else {
            for(int i = 0; i < this.population.individuals.length; i++) {
                this.population.individuals[i] = new Individual(this.strPuzzle);
            }
        }
    }

    public Individual getFittestOffspring() {
        if(this.fittest.fitness > this.secondFittest.fitness) {
            return fittest;
        }
        return secondFittest;
    }

    public void addFittestOffspring() {
        this.fittest.calculateFitness();
        this.secondFittest.calculateFitness();

        int leastFittestIndex = this.population.getLeastFittestIndex();

        this.population.individuals[leastFittestIndex] = getFittestOffspring();

        for(int i = 0; i < this.population.individuals.length; i++) {
            if(i != leastFittestIndex) {
                this.population.individuals[i] = new Individual(this.strPuzzle);
            }
        }
    }

    public void printResult() {
        System.out.println("Fitness " + this.bestFit.fitness + "%");
        System.out.println("Solution: ");

        for(int i = 0; i < this.bestFit.solutions.length; i++) {
            for(int j = 0; j < this.bestFit.solutions[0].length; j++) {
                System.out.print(this.bestFit.solutions[i][j]);
            }
            System.out.println();
        }

    }

}
