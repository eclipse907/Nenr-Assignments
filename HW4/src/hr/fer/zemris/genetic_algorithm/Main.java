package hr.fer.zemris.genetic_algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		try (
				BufferedReader br = Files.newBufferedReader(Paths.get("zad4-dataset1.txt"));
				Scanner sc = new Scanner(System.in);
			) {
			List<Double> x = new ArrayList<>();
			List<Double> y = new ArrayList<>();
			List<Double> realValues = new ArrayList<>();
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				String[] data = line.trim().split("\\s+");
				x.add(Double.parseDouble(data[0]));
				y.add(Double.parseDouble(data[1]));
				realValues.add(Double.parseDouble(data[2]));
			}
			// Best values for data without noise.
			// With noise change num of iters to 20000.
			System.out.println("Enter population size:"); // best value 50
			int popSize = Integer.parseInt(sc.nextLine().trim());
			System.out.println("Enter number of iterations:"); // best value 10000
			int numOfIter = Integer.parseInt(sc.nextLine().trim());
			System.out.println("Enter mutation probability:"); // best value 0.1
			double mutationProbability = Double.parseDouble(sc.nextLine().trim());
			ICostFunction function = new CostFunction(x, y, realValues);
			ISelection selection = new ThreeWayTournamentSelection(); // best selection three way tournament 
			ICrossover crossover = new WholeArithmeticRecombinationCrossover();
			IMutation mutation = new SimpleMutation(mutationProbability);
			IGeneticAlgorithm algorithm = new SteadyStateGeneticAlgorithm(selection, crossover, mutation); // best algorithm steady state
			System.out.println("SOLUTION IS: " + algorithm.findSolution(function, numOfIter, popSize));
		} catch (IOException | NumberFormatException ex) {
			System.out.println("Error while opening and parsing text file with data or while parsing user input.");
		}
	}

}
