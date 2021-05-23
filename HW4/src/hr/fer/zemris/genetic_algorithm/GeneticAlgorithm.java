package hr.fer.zemris.genetic_algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class GeneticAlgorithm implements IGeneticAlgorithm {

	protected ISelection selection;
	protected ICrossover crossover;
	protected IMutation mutation;
	
	public GeneticAlgorithm(ISelection selection, ICrossover crossover, IMutation mutation) {
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
	}
	
	@Override
	public Chromosome findSolution(ICostFunction function, int numOfIterations, int populationSize) {
		List<Chromosome> population = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			List<Double> genes = new ArrayList<>();
			for (int j = 0; j < 5; j++) {
				genes.add(ThreadLocalRandom.current().nextDouble(-4, 4));
			}
			Chromosome chromosome = new Chromosome(genes);
			chromosome.setCostScore(function.calculateValue(chromosome.getGenes()));
			population.add(chromosome);
		}
		return doAlgorithm(function, numOfIterations, population);
	}
	
	protected void printBestSolution(int iteration, Chromosome bestSolution) {
		System.out.println("ITERATION: " + iteration + " BEST SOLUTION: " + bestSolution);
	}
	
	protected abstract Chromosome doAlgorithm(ICostFunction function, int numOfIterations, List<Chromosome> population);
		
}
