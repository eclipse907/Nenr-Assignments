package hr.fer.zemris.neuralnet;

import java.util.AbstractMap;
import java.util.Arrays;
import hr.fer.zemris.genetic_algorithm.Chromosome;
import hr.fer.zemris.genetic_algorithm.DiscreteRecombinationCrossover;
import hr.fer.zemris.genetic_algorithm.ICrossover;
import hr.fer.zemris.genetic_algorithm.IGeneticAlgorithm;
import hr.fer.zemris.genetic_algorithm.IMutation;
import hr.fer.zemris.genetic_algorithm.ISelection;
import hr.fer.zemris.genetic_algorithm.Mutation1;
import hr.fer.zemris.genetic_algorithm.Mutation2;
import hr.fer.zemris.genetic_algorithm.SimpleArithmeticRecombinationCrossover;
import hr.fer.zemris.genetic_algorithm.SteadyStateGeneticAlgorithm;
import hr.fer.zemris.genetic_algorithm.ThreeWayTournamentSelection;
import hr.fer.zemris.genetic_algorithm.WholeArithmeticRecombinationCrossover;

public class Main {

	public static void main(String[] args) {
		NeuralNet net = new NeuralNet(2, 8, 4, 3);
		Dataset dataset = new Dataset("zad7-dataset.txt");
		ISelection selection = new ThreeWayTournamentSelection();
		ICrossover[] crossovers = new ICrossover[3];
		crossovers[0] = new WholeArithmeticRecombinationCrossover();
		crossovers[1] = new DiscreteRecombinationCrossover();
		crossovers[2] = new SimpleArithmeticRecombinationCrossover();
		IMutation[] mutations = new IMutation[3];
		mutations[0] = new Mutation1(18, 0.02, 0.2);
		mutations[1] = new Mutation1(4, 0.02, 0.4);
		mutations[2] = new Mutation2(2, 0.02, 0.5);
		IGeneticAlgorithm algorithm = new SteadyStateGeneticAlgorithm(selection, crossovers, mutations);
		Chromosome chromosome = algorithm.findSolution(net, dataset, 500000, 10);
		boolean allTrue = true;
		for (AbstractMap.SimpleImmutableEntry<Double[], Double[]> entry : dataset) {
			Double[] genesArray = new Double[chromosome.getGenes().size()];
			Double[] output = net.calcOutput(chromosome.getGenes().toArray(genesArray), entry.getKey());
			for (int i = 0; i < output.length; i++) {
				output[i] = output[i] >= 0.5 ? 1.0 : 0;
			}
			if (allTrue && !Arrays.equals(output, entry.getValue())) {
				allTrue = false;
			}
			System.out.println("Expected output: " + Arrays.toString(entry.getValue()) + "  Calculated value: " + Arrays.toString(output));
		}
		System.out.println(allTrue ? "All correctly classified." : "All are not correctly classified.");
		char[] symbols = {'x', 'y'};
		System.out.println("Layer 1");
		for (int neuronIndex = 0; neuronIndex < net.getSizeOfLayers()[1]; neuronIndex++) {
			StringBuilder sb = new StringBuilder();
			sb.append("Neuron " + Integer.toString(neuronIndex + 1) + ": ");
			for (int i = 0, pos = 0; i < 2; i++, pos += 2) {
				sb.append("w" + symbols[i] + "=" + chromosome.getGenes().get(neuronIndex * 2 * 2 + pos) +
						  " s" + symbols[i] + "=" + chromosome.getGenes().get(neuronIndex * 2 * 2 + pos + 1) + " ");
			}
			System.out.println(sb);
		}
		int layerWeightsStartIndex = net.getSizeOfLayers()[1] * 2 * 2;
		for (int layerIndex = 2; layerIndex < net.getSizeOfLayers().length; layerIndex++) {
			System.out.println("Layer " + layerIndex);
			for (int neuronIndex = 0; neuronIndex < net.getSizeOfLayers()[layerIndex]; neuronIndex++) {
				StringBuilder sb = new StringBuilder();
				sb.append("Neuron " + Integer.toString(neuronIndex + 1) + ": ");
				for (int i = net.getSizeOfLayers()[layerIndex - 1], j = 0; i >= 0 && j <= net.getSizeOfLayers()[layerIndex - 1]; i--, j++) {
					sb.append("w" + j + "=" +
							  chromosome.getGenes().get(layerWeightsStartIndex + neuronIndex * (net.getSizeOfLayers()[layerIndex - 1] + 1) + j) + " ");
				}
				System.out.println(sb);
			}
			layerWeightsStartIndex += net.getSizeOfLayers()[layerIndex] * (net.getSizeOfLayers()[layerIndex - 1] + 1);
		}
	}
	
}
