package hr.fer.zemris.neural_net;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NeuralNetwork {
	
	private List<NeuronLayer> neuronLayers;
	private List<List<Double>> outputOfLayers;
	private List<List<Double>> errorOfLayers;
	
	public NeuralNetwork(int... neuralNetArchitecture) {
		neuronLayers = new ArrayList<>();
		for (int i = 1; i < neuralNetArchitecture.length; i++) {
			neuronLayers.add(new NeuronLayer(neuralNetArchitecture[i], neuralNetArchitecture[i - 1]));
		}
		createOutputAndErrorOfLayers();
	}
	
	public NeuralNetwork(NeuronLayer... layers) {
		neuronLayers = Arrays.asList(layers);
		createOutputAndErrorOfLayers();
	}
	
	private void createOutputAndErrorOfLayers() {
		outputOfLayers = new ArrayList<>();
		errorOfLayers = new ArrayList<>();
		for (int i = 0; i < neuronLayers.size(); i++) {
			outputOfLayers.add(new ArrayList<>());
			errorOfLayers.add(new ArrayList<>());
		}
	}
	
	public List<Double> forwardPass(List<Double> sample) {
		if (sample.size() != neuronLayers.get(0).getNeurons().get(0).getNumOfWeights() - 1) {
			throw new IllegalArgumentException("Sample has wrong dimensions.");
		}
		List<Double> layerOutput = outputOfLayers.get(0);
		layerOutput.clear();
		for (Neuron neuron : neuronLayers.get(0).getNeurons()) {
			double sum = neuron.getWeights().get(0);
			for (int i = 1; i < neuron.getNumOfWeights(); i++) {
				sum += neuron.getWeights().get(i) * sample.get(i - 1);
			}
			layerOutput.add(1 / (1 + Math.exp(-sum)));
		}
		for (int layerIndex = 1; layerIndex < neuronLayers.size(); layerIndex++) {
			layerOutput = outputOfLayers.get(layerIndex);
			layerOutput.clear();
			for (Neuron neuron : neuronLayers.get(layerIndex).getNeurons()) {
				double sum = neuron.getWeights().get(0);
				for (int i = 1; i < neuron.getNumOfWeights(); i++) {
					sum += neuron.getWeights().get(i) * outputOfLayers.get(layerIndex - 1).get(i - 1);
				}
				layerOutput.add(1 / (1 + Math.exp(-sum)));
			}
		}
		return Collections.unmodifiableList(outputOfLayers.get(neuronLayers.size() - 1));
	}
	
	private void backwardPass(List<Double> correctOutput) {
		List<Double> layerError = errorOfLayers.get(neuronLayers.size() - 1);
		layerError.clear();
		for (int i = 0; i < neuronLayers.get(neuronLayers.size() - 1).getNumOfNeurons(); i++) {
			double y = outputOfLayers.get(neuronLayers.size() - 1).get(i);
			layerError.add(y * (1 - y) * (correctOutput.get(i) - y));
		}
		for (int layerIndex = neuronLayers.size() - 2; layerIndex >= 0; layerIndex--) {
			layerError = errorOfLayers.get(layerIndex);
			layerError.clear();
			for (int i = 0; i < neuronLayers.get(layerIndex).getNumOfNeurons(); i++) {
				double y = outputOfLayers.get(layerIndex).get(i);
				double sum = 0;
				for (int j = 0; j < neuronLayers.get(layerIndex + 1).getNumOfNeurons(); j++) {
					sum += errorOfLayers.get(layerIndex + 1).get(j) * neuronLayers.get(layerIndex + 1).getNeurons().get(j).getWeights().get(i + 1);
				}
				layerError.add(y * (1 - y) * sum);
			}
		}
	}
	
	public void trainNeuralNet(List<AbstractMap.SimpleImmutableEntry<List<Double>, List<Double>>> dataset, double learningRate,
							   int batchSize, double epsilon, int maxIter) {
		List<List<List<Double>>> gradients = createNewGradientsList();
		int iter = 0;
		int nextSampleIndex = 0;
		int currentBatchSize = 0;
		while (iter < maxIter) {
			if (nextSampleIndex >= dataset.size()) {
				nextSampleIndex = 0;
			}
			List<List<List<Double>>> newGradients = calculateGradientsForSample(dataset.get(nextSampleIndex++));
			for (int layerIndex = 0; layerIndex < neuronLayers.size(); layerIndex++) {
				for (int neuronIndex = 0; neuronIndex < neuronLayers.get(layerIndex).getNumOfNeurons(); neuronIndex++) {
					for (int weightIndex = 0; weightIndex < neuronLayers.get(layerIndex).getNeurons().get(neuronIndex).getNumOfWeights(); weightIndex++) {
						 Double weightGrad = gradients.get(layerIndex).get(neuronIndex).get(weightIndex);
						 weightGrad += newGradients.get(layerIndex).get(neuronIndex).get(weightIndex);
						 gradients.get(layerIndex).get(neuronIndex).set(weightIndex, weightGrad);
					}
				}
			}
			currentBatchSize++;
			if (currentBatchSize % batchSize == 0) {
				for (int layerIndex = 0; layerIndex < neuronLayers.size(); layerIndex++) {
					for (int neuronIndex = 0; neuronIndex < neuronLayers.get(layerIndex).getNumOfNeurons(); neuronIndex++) {
						for (int weightIndex = 0; weightIndex < neuronLayers.get(layerIndex).getNeurons().get(neuronIndex).getNumOfWeights(); weightIndex++) {
							 Double weightGrad = gradients.get(layerIndex).get(neuronIndex).get(weightIndex);
							 Double weight = neuronLayers.get(layerIndex).getNeurons().get(neuronIndex).getWeights().get(weightIndex);
							 neuronLayers.get(layerIndex).getNeurons().get(neuronIndex).getWeights().set(weightIndex, weight + learningRate * weightGrad);
						}
					}
				}
				gradients = createNewGradientsList();
				iter++;
				currentBatchSize = 0;
				double error = calculateErrorForDataset(dataset);
				if (error <= epsilon) {
					break;
				}
				if (iter % 10 == 0) {
					System.out.println("Iteration: " + iter + ", Error: " + error);
				}
			}
		}
		System.out.println("Final error is: " + calculateErrorForDataset(dataset));
	}
	
	private List<List<List<Double>>> createNewGradientsList() {
		List<List<List<Double>>> gradients = new ArrayList<>();
		for (int layerIndex = 0; layerIndex < neuronLayers.size(); layerIndex++) {
			List<List<Double>> layerGradients = new ArrayList<>();
			for (int neuronIndex = 0; neuronIndex < neuronLayers.get(layerIndex).getNumOfNeurons(); neuronIndex++) {
				List<Double> neuronGradients = new ArrayList<>();
				for (int weightIndex = 0; weightIndex < neuronLayers.get(layerIndex).getNeurons().get(neuronIndex).getNumOfWeights(); weightIndex++) {
					neuronGradients.add(0.0);
				}
				layerGradients.add(neuronGradients);
			}
			gradients.add(layerGradients);
		}
		return gradients;
	}
	
	private List<List<List<Double>>> calculateGradientsForSample(AbstractMap.SimpleImmutableEntry<List<Double>, List<Double>> sample) {
		forwardPass(sample.getKey());
		backwardPass(sample.getValue());
		List<List<List<Double>>> gradients = new ArrayList<>();
		List<List<Double>> layerGradients = new ArrayList<>();
		for (int neuronIndex = 0; neuronIndex < neuronLayers.get(0).getNumOfNeurons(); neuronIndex++) {
			List<Double> neuronGradients = new ArrayList<>();
			neuronGradients.add(errorOfLayers.get(0).get(neuronIndex));
			for (int weightIndex = 1; weightIndex < neuronLayers.get(0).getNeurons().get(neuronIndex).getNumOfWeights(); weightIndex++) {
				neuronGradients.add(errorOfLayers.get(0).get(neuronIndex) * sample.getKey().get(weightIndex - 1));
			}
			layerGradients.add(neuronGradients);
		}
		gradients.add(layerGradients);
		for (int layerIndex = 1; layerIndex < neuronLayers.size(); layerIndex++) {
			layerGradients = new ArrayList<>();
			for (int neuronIndex = 0; neuronIndex < neuronLayers.get(layerIndex).getNumOfNeurons(); neuronIndex++) {
				List<Double> neuronGradients = new ArrayList<>();
				neuronGradients.add(errorOfLayers.get(layerIndex).get(neuronIndex));
				for (int weightIndex = 1; weightIndex < neuronLayers.get(layerIndex).getNeurons().get(neuronIndex).getNumOfWeights(); weightIndex++) {
					neuronGradients.add(errorOfLayers.get(layerIndex).get(neuronIndex) * outputOfLayers.get(layerIndex - 1).get(weightIndex - 1));
				}
				layerGradients.add(neuronGradients);
			}
			gradients.add(layerGradients);
		}
		return gradients;
	}
	
	public double calculateErrorForDataset(List<AbstractMap.SimpleImmutableEntry<List<Double>, List<Double>>> dataset) {
		double errorSum = 0;
		for (AbstractMap.SimpleImmutableEntry<List<Double>, List<Double>> sample : dataset) {
			List<Double> output = forwardPass(sample.getKey());
			double sum = 0;
			for (int i = 0; i < output.size(); i++) {
				sum += Math.pow(sample.getValue().get(i) - output.get(i), 2);
			}
			errorSum += sum;
		}
		return errorSum / (2 * dataset.size());
	}
	
}
