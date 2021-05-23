package hr.fer.zemris.genetic_algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import hr.fer.zemris.neuralnet.Dataset;
import hr.fer.zemris.neuralnet.NeuralNet;

public class SteadyStateGeneticAlgorithm extends GeneticAlgorithm {

	public SteadyStateGeneticAlgorithm(ISelection selection, ICrossover[] crossovers, IMutation[] mutations) {
		super(selection, crossovers, mutations);
	}
	
	@Override
	protected Chromosome doAlgorithm(NeuralNet net, Dataset dataset, int numOfIterations, List<Chromosome> population) {
		List<Chromosome> sortedPopulation = new ArrayList<>(population);
		Collections.sort(sortedPopulation);
		Chromosome currentBestChromosome = sortedPopulation.get(sortedPopulation.size() - 1);
		printBestSolution(0, currentBestChromosome);
		for (int i = 0; i < numOfIterations; i++) {
			List<Chromosome> selected = selection.selectChromosomes(population);
			Chromosome child = crossovers[ThreadLocalRandom.current().nextInt(0, crossovers.length)].doCrossover(selected.get(2), selected.get(1));
			child = selectMutation().mutate(child);
			Double[] genesArray = new Double[child.getGenes().size()];
			child.setCostScore(-net.calcError(dataset, child.getGenes().toArray(genesArray)));
			population.set(population.indexOf(selected.get(0)), child);
			sortedPopulation = new ArrayList<>(population);
			Collections.sort(sortedPopulation);
			if (!currentBestChromosome.equals(sortedPopulation.get(sortedPopulation.size() - 1))) {
				currentBestChromosome = sortedPopulation.get(sortedPopulation.size() - 1);
				printBestSolution(i + 1, currentBestChromosome);
			}
			if (Math.abs(currentBestChromosome.getCostScore()) < Math.pow(10, -7)) {
				break;
			}
		}
		Collections.sort(population);
		return population.get(population.size() - 1);
	}
	
	private IMutation selectMutation() {
		double sum = 0;
		for (IMutation mutation : mutations) {
			sum += mutation.getMethodProbability();
		}
		double selector = ThreadLocalRandom.current().nextDouble(0, sum);
		double area = 0;
		IMutation selectedMutation = null;
		for (IMutation mutation : mutations) {
			area += mutation.getMethodProbability();
			if (selector <= area) {
				selectedMutation = mutation;
				break;
			}
		}
		return selectedMutation;
	}
	
}
