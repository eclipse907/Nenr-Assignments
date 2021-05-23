package hr.fer.zemris.neuralnet;

import java.util.AbstractMap;

public class NeuralNet {
	
	private int[] sizeOfLayers;
	private double[] neuronOutputs;

	public NeuralNet(int... sizeOfLayers) {
		if (sizeOfLayers.length < 3) {
			throw new IllegalArgumentException("Neural network must have at least three layers.");
		}
		this.sizeOfLayers = sizeOfLayers;
		int numOfNeuronOutputs = 0;
		for (int i = 1; i < sizeOfLayers.length; i++) {
			numOfNeuronOutputs += sizeOfLayers[i];
		}
		neuronOutputs = new double[numOfNeuronOutputs];
	}
	
	public int numOfParamsInNetwork() {
		int numOfParams = sizeOfLayers[1] * sizeOfLayers[0] * 2;
		for (int i = 2; i < sizeOfLayers.length; i++) {
			numOfParams += sizeOfLayers[i] * (sizeOfLayers[i - 1] + 1); 
		}
		return numOfParams;
	}
	
	public int[] getSizeOfLayers() {
		return sizeOfLayers;
	}

	public Double[] calcOutput(Double[] weights, Double[] input) {
		if (weights.length != numOfParamsInNetwork()) {
			throw new IllegalArgumentException("Wrong number of weights given.");
		}
		for (int neuronIndex = 0; neuronIndex < sizeOfLayers[1]; neuronIndex++) {
			double sum = 0;
			for (int i = 0, pos = 0; i < input.length; i++, pos += 2) {
				sum += Math.abs(input[i] - weights[neuronIndex * input.length * 2 + pos]) / Math.abs(weights[neuronIndex * input.length * 2 + pos + 1]);
			}
			neuronOutputs[neuronIndex] = 1.0f / (1.0f + sum);
		}
		int layerOutputStartIndex = sizeOfLayers[1];
		int layerWeightsStartIndex = sizeOfLayers[1] * input.length * 2;
		for (int layerIndex = 2; layerIndex < sizeOfLayers.length; layerIndex++) {
			for (int neuronIndex = 0; neuronIndex < sizeOfLayers[layerIndex]; neuronIndex++) {
				double sum = weights[layerWeightsStartIndex + neuronIndex * (sizeOfLayers[layerIndex - 1] + 1)];
				for (int i = sizeOfLayers[layerIndex - 1], j = 1; i >= 0 && j <= sizeOfLayers[layerIndex - 1]; i--, j++) {
					sum += weights[layerWeightsStartIndex + neuronIndex * (sizeOfLayers[layerIndex - 1] + 1) + j] * neuronOutputs[layerOutputStartIndex - i];
				}
				neuronOutputs[layerOutputStartIndex + neuronIndex] = 1.0f / (1.0f + Math.exp(-sum));
			}
			layerOutputStartIndex += sizeOfLayers[layerIndex];
			layerWeightsStartIndex += sizeOfLayers[layerIndex] * (sizeOfLayers[layerIndex - 1] + 1);
		}
		Double[] output = new Double[3];
		output[0] = neuronOutputs[neuronOutputs.length - 3];
		output[1] = neuronOutputs[neuronOutputs.length - 2];
		output[2] = neuronOutputs[neuronOutputs.length - 1];
		return output;
	}
	
	public double calcError(Dataset dataset, Double[] weights) {
		if (weights.length != numOfParamsInNetwork()) {
			throw new IllegalArgumentException("Wrong number of weights given.");
		}
		double error = 0;
		for (AbstractMap.SimpleImmutableEntry<Double[], Double[]> entry : dataset) {
			Double[] output = calcOutput(weights, entry.getKey());
			for (int i = 0; i < output.length; i++) {
				error += Math.pow(entry.getValue()[i] - output[i], 2);
			}
		}
		return error / dataset.numOfData();
	}
	
}
