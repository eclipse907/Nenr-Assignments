package hr.fer.zemris.genetic_algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SteadyStateGeneticAlgorithm extends GeneticAlgorithm {

	public SteadyStateGeneticAlgorithm(ISelection selection, ICrossover crossover, IMutation mutation) {
		super(selection, crossover, mutation);
	}
	
	@Override
	protected Chromosome doAlgorithm(ICostFunction function, int numOfIterations, List<Chromosome> population) {
		List<Chromosome> sortedPopulation = new ArrayList<>(population);
		Collections.sort(sortedPopulation);
		Chromosome currentBestChromosome = sortedPopulation.get(sortedPopulation.size() - 1);
		printBestSolution(0, currentBestChromosome);
		for (int i = 0; i < numOfIterations; i++) {
			List<Chromosome> selected = selection.selectChromosomes(population);
			Chromosome child = crossover.doCrossover(selected.get(2), selected.get(1));
			child = mutation.mutate(child);
			child.setCostScore(function.calculateValue(child.getGenes()));
			population.set(population.indexOf(selected.get(0)), child);
			sortedPopulation = new ArrayList<>(population);
			Collections.sort(sortedPopulation);
			if (!currentBestChromosome.equals(sortedPopulation.get(sortedPopulation.size() - 1))) {
				currentBestChromosome = sortedPopulation.get(sortedPopulation.size() - 1);
				printBestSolution(i + 1, currentBestChromosome);
			}
		}
		Collections.sort(population);
		return population.get(population.size() - 1);
	}
	
}
