package hr.fer.zemris.genetic_algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerationalGeneticAlgorithm extends GeneticAlgorithm {
	
	private boolean isElitist;

	public GenerationalGeneticAlgorithm(ISelection selection, ICrossover crossover,
										IMutation mutation, boolean isElitist) {
		super(selection, crossover, mutation);
		this.isElitist = isElitist;
	}

	@Override
	protected Chromosome doAlgorithm(ICostFunction function, int numOfIterations, List<Chromosome> population) {
		List<Chromosome> sortedPopulation = new ArrayList<>(population);
		Collections.sort(sortedPopulation);
		Chromosome currentBestChromosome = sortedPopulation.get(sortedPopulation.size() - 1);
		printBestSolution(0, currentBestChromosome);
		for (int i = 0; i < numOfIterations; i++) {
			List<Chromosome> nextGeneration = new ArrayList<>();
			sortedPopulation = new ArrayList<>(population);
			Collections.sort(sortedPopulation);
			if (isElitist) {
				nextGeneration.add(sortedPopulation.get(sortedPopulation.size() - 1));
			}
			while (nextGeneration.size() < population.size()) {
				Chromosome firstParent = selection.selectChromosomes(population).get(0);
				Chromosome secondParent = selection.selectChromosomes(population).get(0);
				while (firstParent.equals(secondParent)) {
					secondParent = selection.selectChromosomes(population).get(0);
				}
				Chromosome child = crossover.doCrossover(firstParent, secondParent);
				child = mutation.mutate(child);
				child.setCostScore(function.calculateValue(child.getGenes()));
				nextGeneration.add(child);
			}
			if (!currentBestChromosome.equals(sortedPopulation.get(sortedPopulation.size() - 1))) {
				currentBestChromosome = sortedPopulation.get(sortedPopulation.size() - 1);
				printBestSolution(i + 1, currentBestChromosome);
			}
			population = nextGeneration;
		}
		Collections.sort(population);
		return population.get(population.size() - 1);
	}
	
}
