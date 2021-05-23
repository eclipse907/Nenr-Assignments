package hr.fer.zemris.genetic_algorithm;

import hr.fer.zemris.neuralnet.Dataset;
import hr.fer.zemris.neuralnet.NeuralNet;

public interface IGeneticAlgorithm {

	Chromosome findSolution(NeuralNet net, Dataset dataset, int numOfIterations, int populationSize);
	
}
