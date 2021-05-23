package hr.fer.zemris.genetic_algorithm;

public interface IGeneticAlgorithm {

	Chromosome findSolution(ICostFunction function, int numOfIterations, int populationSize);
	
}
