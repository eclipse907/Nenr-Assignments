package hr.fer.zemris.neural_net;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.AbstractMap.SimpleImmutableEntry;
import javax.swing.SwingUtilities;
import hr.fer.zemris.sample_testing.PatternClassify;

public class Main {

	public static void main(String[] args) {
		try(Scanner sc = new Scanner(System.in)) {
			System.out.println("Enter file name with train data:");
			String fileName = sc.nextLine().trim();
			System.out.println("Enter number of representative points for each gesture:");
			int M = Integer.parseInt(sc.nextLine().trim());
			int[] networkArchitecture;
			while (true) {
				System.out.println("Enter number of neurons in each layer separated by comma:");
				String[] lineData = sc.nextLine().trim().split(",");
				networkArchitecture = new int[lineData.length];
				for (int i = 0; i < lineData.length; i++) {
					networkArchitecture[i] = Integer.parseInt(lineData[i]);
				}
				if (networkArchitecture[0] == 2 * M && networkArchitecture[networkArchitecture.length - 1] == 5) {
					break;
				} else {
					System.out.println("Wrong architecture entered. First layer must have 2*M neurons, last layer must have 5 neurons.");
				}					
			}
			int algorithm;
			while (true) {
				System.out.println("Please enter learning algorithm (number before algorithm name):" +
			                       "\n1 - Batch learning\n2 - Online learning\n3 - Mini-batch learning");
				algorithm = Integer.parseInt(sc.nextLine().trim());
				if (algorithm == 1 || algorithm == 2 || algorithm == 3) {
					break;
				} else {
					System.out.println("Please enter 1, 2 or 3.");
				}
			}
			List<AbstractMap.SimpleImmutableEntry<List<Double>, List<Double>>> dataset = loadDataset(fileName);
			NeuralNetwork network = new NeuralNetwork(networkArchitecture);
			if (algorithm == 1) {
				network.trainNeuralNet(dataset, 0.1, dataset.size(), 10E-5, 10000);
			} else if (algorithm == 2) {
				network.trainNeuralNet(dataset, 0.1, 1, 10E-5, 10000);
			} else {
				network.trainNeuralNet(formatDatasetForMinibatchLearning(dataset), 0.1, 10, 10E-5, 10000);
			}
			SwingUtilities.invokeLater(() -> {
				new PatternClassify(network).setVisible(true);
			});
		} catch (NumberFormatException ex) {
			System.out.println("Error while parsing argument.");
			System.exit(1);
		}
	}
	
	private static List<AbstractMap.SimpleImmutableEntry<List<Double>, List<Double>>> loadDataset(String fileName) {
		List<AbstractMap.SimpleImmutableEntry<List<Double>, List<Double>>> dataset = new ArrayList<>();
		try(BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				String[] data = line.trim().split("->");
				List<Double> points = new ArrayList<>();
				for (String point : data[0].split(",")) {
					points.add(Double.parseDouble(point));
				}
				List<Double> label = new ArrayList<>();
				for (String lb : data[1].split(",")) {
					label.add(Double.parseDouble(lb));
				}
				dataset.add(new SimpleImmutableEntry<>(points, label));
			}
		} catch (IOException | NumberFormatException ex) {
			System.out.println("Error while loading dataset from file.");
			System.exit(1);
		}
		return dataset;
	}
	
	private static List<AbstractMap.SimpleImmutableEntry<List<Double>, List<Double>>> formatDatasetForMinibatchLearning(List<AbstractMap.SimpleImmutableEntry<List<Double>, List<Double>>> dataset) {
		List<AbstractMap.SimpleImmutableEntry<List<Double>, List<Double>>> newDataset = new ArrayList<>();
		for(int i = 0; i < 20; i += 2) {
			newDataset.add(dataset.get(i));
			newDataset.add(dataset.get(i + 1));
			newDataset.add(dataset.get(20 + i));
			newDataset.add(dataset.get(20 + i + 1));
			newDataset.add(dataset.get(40 + i));
			newDataset.add(dataset.get(40 + i + 1));
			newDataset.add(dataset.get(60 + i));
			newDataset.add(dataset.get(60 + i + 1));
			newDataset.add(dataset.get(80 + i));
			newDataset.add(dataset.get(80 + i + 1));
		}
		return newDataset;
	}
	
}
