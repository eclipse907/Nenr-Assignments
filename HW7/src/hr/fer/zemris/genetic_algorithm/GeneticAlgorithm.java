package hr.fer.zemris.genetic_algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import hr.fer.zemris.neuralnet.Dataset;
import hr.fer.zemris.neuralnet.NeuralNet;

public abstract class GeneticAlgorithm implements IGeneticAlgorithm {

	protected ISelection selection;
	protected ICrossover[] crossovers;
	protected IMutation[] mutations;
	
	public GeneticAlgorithm(ISelection selection, ICrossover[] crossovers, IMutation[] mutations) {
		this.selection = selection;
		this.crossovers = crossovers;
		this.mutations = mutations;
	}
	
	@Override
	public Chromosome findSolution(NeuralNet net, Dataset dataset, int numOfIterations, int populationSize) {
		List<Chromosome> population = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			List<Double> genes = new ArrayList<>();
			for (int j = 0; j < net.numOfParamsInNetwork(); j++) {
				genes.add(ThreadLocalRandom.current().nextDouble(-1, 1));
			}
			Chromosome chromosome = new Chromosome(genes);
			Double[] genesArray = new Double[chromosome.getGenes().size()];
			chromosome.setCostScore(-net.calcError(dataset, chromosome.getGenes().toArray(genesArray)));
			population.add(chromosome);
		}
		return doAlgorithm(net, dataset, numOfIterations, population);
	}
	
	protected void printBestSolution(int iteration, Chromosome bestSolution) {
		System.out.println("ITERATION: " + iteration + " BEST SOLUTION: " + bestSolution);
	}
	
	protected abstract Chromosome doAlgorithm(NeuralNet net, Dataset dataset, int numOfIterations, List<Chromosome> population);
		
}
