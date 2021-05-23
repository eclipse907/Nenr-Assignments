package hr.fer.zemris.neural_net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeuronLayer {
	
	private List<Neuron> neurons;
	private int numOfNeurons;
	
	public NeuronLayer(int numOfNeurons, int numOfNeuronsInPreviousLayer) {
		neurons = new ArrayList<>();
		for (int i = 0; i < numOfNeurons; i++) {
			neurons.add(new Neuron(numOfNeuronsInPreviousLayer + 1));
		}
		this.numOfNeurons = numOfNeurons;
	}
	
	public NeuronLayer(Neuron... neurons) {
		this.neurons = Arrays.asList(neurons);
	}

	public List<Neuron> getNeurons() {
		return neurons;
	}

	public int getNumOfNeurons() {
		return numOfNeurons;
	}
		
}
