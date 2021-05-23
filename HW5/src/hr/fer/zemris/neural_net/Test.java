package hr.fer.zemris.neural_net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.AbstractMap.SimpleImmutableEntry;

public class Test {

	public static void main(String[] args) {
		List<SimpleImmutableEntry<List<Double>, List<Double>>> trainData = new ArrayList<>();
		trainData.add(new SimpleImmutableEntry<>(Arrays.asList(-1.0), Arrays.asList(1.0)));
		trainData.add(new SimpleImmutableEntry<>(Arrays.asList(-0.8), Arrays.asList(0.64)));
		trainData.add(new SimpleImmutableEntry<>(Arrays.asList(-0.6), Arrays.asList(0.36)));
		trainData.add(new SimpleImmutableEntry<>(Arrays.asList(-0.4), Arrays.asList(0.16)));
		trainData.add(new SimpleImmutableEntry<>(Arrays.asList(-0.2), Arrays.asList(0.04)));
		trainData.add(new SimpleImmutableEntry<>(Arrays.asList(0.0), Arrays.asList(0.0)));
		trainData.add(new SimpleImmutableEntry<>(Arrays.asList(0.2), Arrays.asList(0.04)));
		trainData.add(new SimpleImmutableEntry<>(Arrays.asList(0.4), Arrays.asList(0.16)));
		trainData.add(new SimpleImmutableEntry<>(Arrays.asList(0.6), Arrays.asList(0.36)));
		trainData.add(new SimpleImmutableEntry<>(Arrays.asList(0.8), Arrays.asList(0.64)));
		trainData.add(new SimpleImmutableEntry<>(Arrays.asList(1.0), Arrays.asList(1.0)));
		NeuralNetwork network = new NeuralNetwork(1, 6, 6, 6, 4, 1);
		network.trainNeuralNet(trainData, 0.1, trainData.size(), 10E-6, 10000);
	}
	
}
