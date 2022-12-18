import java.util.Random;

public class Individual {
    public String strPuzzle;
    public int[] genes;
    public int fitness;

    public int size;
    public int[][] puzzles;
    public int[][] solutions;
    public int numberCount;

    public Individual(String strPuzzle) {
        this.strPuzzle = strPuzzle;
        this.size = (int) Math.sqrt(this.strPuzzle.length());

        Random random = new Random();

        this.genes = new int[strPuzzle.length()];
        for(int i = 0; i < this.genes.length; i++) {
            this.genes[i] = Math.abs(random.nextInt() % 2);
        }

        this.fitness = 0;

        this.setPuzzle();
        this.setSolution();
    }

    public void setPuzzle() {
        this.puzzles = new int[this.size][this.size];
        for(int i = 0; i < this.puzzles.length; i++) {
            for(int j = 0; j < this.puzzles[0].length; j++) {
                try {
                    this.puzzles[i][j] = Integer.parseInt(this.strPuzzle.substring(i * this.size + j, i * this.size + j + 1));
                    this.numberCount++;
                } catch (Exception e) {
                    this.puzzles[i][j] = -1;
                }
            }
        }
    }

    public void setSolution() {
        this.solutions = new int[this.size][this.size];
        for(int i = 0; i < this.solutions.length; i++) {
            for(int j = 0; j < this.solutions[0].length; j++) {
                this.solutions[i][j] = this.genes[i * this.size + j];
            }
        }
    }

    public void calculateFitness() {
        double totalRate = 0.0;
        for(int i = 0 ; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                if(this.puzzles[i][j] > -1) {
                    double currentRate = 0.0;
                    int neighbourCount = 0;
                    try {
                        if(solutions[i-1][j-1] == 1) {
                            neighbourCount++;
                        }
                    } catch (Exception e) {}
                    try {
                        if(solutions[i-1][j] == 1) {
                            neighbourCount++;
                        }
                    } catch (Exception e) {}
                    try {
                        if(solutions[i+1][j] == 1) {
                            neighbourCount++;
                        }
                    } catch (Exception e) {}
                    try {
                        if(solutions[i][j-1] == 1) {
                            neighbourCount++;
                        }
                    } catch (Exception e) {}
                    try {
                        if(solutions[i][j] == 1) {
                            neighbourCount++;
                        }
                    } catch (Exception e) {}
                    try {
                        if(solutions[i][j+1] == 1) {
                            neighbourCount++;
                        }
                    } catch (Exception e) {}
                    try {
                        if(solutions[i+1][j-1] == 1) {
                            neighbourCount++;
                        }
                    } catch (Exception e) {}
                    try {
                        if(solutions[i+1][j] == 1) {
                            neighbourCount++;
                        }
                    } catch (Exception e) {}
                    try {
                        if(solutions[i+1][j+1] == 1) {
                            neighbourCount++;
                        }
                    } catch (Exception e) {}

                    if(puzzles[i][j] == neighbourCount) {
                        currentRate = 1.0;
                    }
                    else if(puzzles[i][j] > neighbourCount) {
                        currentRate = (neighbourCount + 1.0) / (puzzles[i][j] + 1.0);
                    }
                    else if(puzzles[i][j] < neighbourCount) {
                        currentRate = (puzzles[i][j] + 1.0) / (neighbourCount + 1.0);
                    }
                    totalRate += currentRate;
                }
            }
        }
        totalRate = totalRate / numberCount;
        this.fitness = (int) (totalRate * 100);
    }

}
