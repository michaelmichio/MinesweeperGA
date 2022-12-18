public class Population {
    public Individual[] individuals;
    public int fittest;

    public Population(int populationSize, String strPuzzle) {

        this.individuals = new Individual[populationSize];

        for(int i = 0; i < this.individuals.length; i++) {
            this.individuals[i] = new Individual(strPuzzle);
        }
    }

    // Get the fittest individual
    public Individual getFittest() {
        int maxFitValue = Integer.MIN_VALUE;
        int maxFitIndex = 0;

        for(int i = 0; i < this.individuals.length; i++) {
            if(maxFitValue <= this.individuals[i].fitness) {
                maxFitValue = this.individuals[i].fitness;
                maxFitIndex = i;
            }
        }

        this.fittest = this.individuals[maxFitIndex].fitness;

        return this.individuals[maxFitIndex];
    }

    // Get the second most fittest individual
    public Individual getSecondFittest() {
        int fittestIndex = 0;
        int secondFittestIndex = 0;

        for(int i = 0; i < this.individuals.length; i++) {
            if(this.individuals[i].fitness > this.individuals[fittestIndex].fitness) {
                secondFittestIndex = fittestIndex;
                fittestIndex = i;
            }
            else if(this.individuals[i].fitness > this.individuals[secondFittestIndex].fitness) {
                secondFittestIndex = i;
            }
        }

        return this.individuals[secondFittestIndex];
    }

    // Get index of least fittest individual
    public int getLeastFittestIndex() {
        int minFitValue = Integer.MAX_VALUE;
        int minFitIndex = 0;

        for(int i = 0; i < this.individuals.length; i++) {
            if(this.individuals[i].fitness <= minFitValue) {
                minFitValue = this.individuals[i].fitness;
                minFitIndex = i;
            }
        }

        return minFitIndex;
    }

    // Calculate fitness of each individual
    public void calculateFitness() {
        for(int i = 0; i < individuals.length; i++) {
            this.individuals[i].calculateFitness();
        }
        this.getFittest();
    }

}
