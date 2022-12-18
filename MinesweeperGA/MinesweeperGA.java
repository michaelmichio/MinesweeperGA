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
    public int bestFitGeneration;


    public MinesweeperGA(int generationCount, int populationSize, double crossoverRate, double mutationRate, double elitismRate) {
        this.generationCount = generationCount;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitismRate = elitismRate;
    }

    public void generate(String strPuzzle) {
        this.strPuzzle = strPuzzle;
        int i = 1;

        // Initialize population
        this.population = new Population(populationSize, this.strPuzzle);

        // Calculate fitness of each individual
        this.population.calculateFitness();

        // Store best fitness
        this.bestFit = this.population.getFittest();
        System.out.println("Generation: " + i + " Fittest: " + this.population.fittest + "%");

        while(this.bestFit.fitness < 100 && i < this.generationCount) {
            i++;

            // Do selection
            this.selection();
            // Do crossover
            this.crossover();
            // Do mutation
            this.mutation();
            // Do elitism
            this.elitism();

            // Calculate new fitness value
            this.population.calculateFitness();

            // Update best fitness
            if(this.bestFit.fitness < this.fittest.fitness) {
                this.bestFit = this.fittest;
                this.bestFitGeneration = i;
            }

            System.out.println("Generation: " + i + " Fittest: " + this.population.fittest + "%");
        }

        System.out.println("Solution found in generation " + this.bestFitGeneration);
        this.printResult();
    }

    public void selection() {
        // Select the fittest individual
        this.fittest = this.population.getFittest();
        // Select the second most fit individual
        this.secondFittest = this.population.getSecondFittest();
    }

    public void crossover() {
        // Do crossover with probability
        Random random = new Random();
        int number = random.nextInt(100);

        if(number <= (int) (this.crossoverRate * 100)) {
            int length1 = this.fittest.genes.length / 2;
            int[] temp1 = new int[this.fittest.genes.length];
            int[] temp2 = new int[this.fittest.genes.length];
            // divide chromosome into 2 parts, left part and right part
            for(int i = 0; i < length1; i++) {
                // fill left temp1 chromosome with left fittest chromosome
                temp1[i] = this.fittest.genes[i];
                // fill left temp2 chromosome with left secondFittest chromosome
                temp2[i] = this.secondFittest.genes[i];
            }
            for(int i = length1; i < this.fittest.genes.length; i++) {
                // fill right temp1 chromosome with right secondFittest chromosome
                temp1[i] = this.secondFittest.genes[i];
                // fill right temp2 chromosome with right fittest chromosome
                temp2[i] = this.fittest.genes[i];
            }
            // replace old chromosome with temp
            for(int i = length1; i < this.fittest.genes.length; i++) {
                this.fittest.genes[i] = temp1[i];
                this.secondFittest.genes[i] = temp2[i];
            }
        }
    }

    public void mutation() {
        // Do mutation with probability
        Random random = new Random();
        int number = random.nextInt(100);

        if(number <= (int) (this.mutationRate * 100)) {
            // Select a random mutation point
            int mutationPoint = random.nextInt(this.population.individuals[0].genes.length);

            // Flip values at the mutation point
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
        // Do elitism with probability
        Random random = new Random();
        int number = random.nextInt(100);

        if(number <= (int) (this.elitismRate * 100)) {
            // Set the fittest individual to new population
            this.addFittestOffspring();
        }
        else {
            // Replace all individual on population to new random individual
            for(int i = 0; i < this.population.individuals.length; i++) {
                this.population.individuals[i] = new Individual(this.strPuzzle);
            }
        }
    }

    public Individual getFittestOffspring() {
        // Get fittest offspring
        if(this.fittest.fitness > this.secondFittest.fitness) {
            return fittest;
        }
        return secondFittest;
    }

    public void addFittestOffspring() {
        // Update fitness values of offspring
        this.fittest.calculateFitness();
        this.secondFittest.calculateFitness();

        // Get index of the least fit individual
        int leastFittestIndex = this.population.getLeastFittestIndex();

        // Replace least fittest individual from most fittest offspring
        this.population.individuals[leastFittestIndex] = getFittestOffspring();

        for(int i = 0; i < this.population.individuals.length; i++) {
            // Replace every individual on population except the fittest
            if(i != leastFittestIndex) {
                this.population.individuals[i] = new Individual(this.strPuzzle);
            }
        }
    }

    public void printResult() {
        // Print the best result
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
