package hr.fer.zemris.neural_net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Neuron {
	
	private List<Double> weights;
	private int numOfWeights;
	
	public Neuron(int numOfWeights) {
		weights = new ArrayList<>();
		for (int i = 0; i < numOfWeights; i++) {
			weights.add(2 * Math.random() - 1);
		}
		this.numOfWeights = numOfWeights;
	}
	
	public Neuron(Double... weights) {
		this.weights = Arrays.asList(weights);
	}

	public List<Double> getWeights() {
		return weights;
	}

	public int getNumOfWeights() {
		return numOfWeights;
	}
	
}
