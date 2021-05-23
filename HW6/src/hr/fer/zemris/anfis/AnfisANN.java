package hr.fer.zemris.anfis;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AnfisANN {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in);
			 BufferedWriter errorWriter = Files.newBufferedWriter(Paths.get("error_sgd_big.txt"))) {
			int numOfRules;
			System.out.println("Enter number of rules for ANFIS neural network:");
			while (true) {
				String input = sc.nextLine();
				try {
					numOfRules = Integer.parseInt(input.trim());
					break;
				} catch (NumberFormatException ex) {
					System.out.println("Please enter a whole number.");
				}
			}
			double[] a = new double[numOfRules];
			double[] b = new double[numOfRules];
			double[] c = new double[numOfRules];
			double[] d = new double[numOfRules];
			
			double[] p = new double[numOfRules];
			double[] q = new double[numOfRules];
			double[] r = new double[numOfRules];
			
			double randomScale = 1.0;
			
			for (int i = 0; i < numOfRules; i++) {
				a[i] = 2 * randomScale * Math.random() - randomScale;
				b[i] = 2 * randomScale * Math.random() - randomScale;
				c[i] = 2 * randomScale * Math.random() - randomScale;
				d[i] = 2 * randomScale * Math.random() - randomScale;
				p[i] = 2 * randomScale * Math.random() - randomScale;
				q[i] = 2 * randomScale * Math.random() - randomScale;
				r[i] = 2 * randomScale * Math.random() - randomScale;
			}
			
			double[] ak = new double[a.length];
			double[] bk = new double[b.length];
			double[] ck = new double[c.length];
			double[] dk = new double[d.length];
			
			double[] pk = new double[p.length];
			double[] qk = new double[q.length];
			double[] rk = new double[r.length];
			
			int numOfIterations = 100000;
			int numOfSamplesInBatch = 1;
			double fuzzyLearningRate = 0.0001;
			double functionLearningRate = 0.001;
			
			Map<Integer, AbstractMap.SimpleEntry<Double[], Double>> dataset = createDataset();
			double[] ok = new double[dataset.size()];
			
			for (int cnt = 0; cnt < numOfIterations; cnt++) {
				for (int dataIndex = 0; dataIndex < dataset.size(); dataIndex++) {
					double x = dataset.get(dataIndex).getKey()[0];
					double y = dataset.get(dataIndex).getKey()[1];
					
					double[] alpha = new double[a.length];
					double[] beta = new double[c.length];
					double[] wk = new double[alpha.length];
					double sumOfWk = 0;
					for (int i = 0; i < wk.length; i++) {
						alpha[i] = 1 / (1 + Math.exp(b[i] * (x - a[i])));
						beta[i] = 1 / (1 + Math.exp(d[i] * (y - c[i])));
						wk[i] = alpha[i] * beta[i];
						sumOfWk += wk[i];
					}
					
					double[] normWk = new double[wk.length];
					for (int i = 0; i < wk.length; i++) {
						normWk[i] = wk[i] / sumOfWk;
					}
					
					double[] fk = new double[normWk.length];
					ok[dataIndex] = 0;
					double sum = 0;
					for (int i = 0; i < fk.length; i++) {
						fk[i] = p[i] * x + q[i] * y + r[i];
						sum += wk[i] * fk[i];
						ok[dataIndex] += normWk[i] * fk[i];
					}
					
					for (int i = 0; i < numOfRules; i++) {
						double error = dataset.get(dataIndex).getValue() - ok[dataIndex];
						double temp = error * (fk[i] * sumOfWk - sum) / (sumOfWk * sumOfWk);
						
						ak[i] += temp * beta[i] * alpha[i] * (1 - alpha[i]) * b[i];
						bk[i] += temp * beta[i] * alpha[i] * (1 - alpha[i]) * (a[i] - x);
						ck[i] += temp * alpha[i] * beta[i] * (1 - beta[i]) * d[i];
						dk[i] += temp * alpha[i] * beta[i] * (1 - beta[i]) * (c[i] - y);
						
						pk[i] += error * (wk[i] / sumOfWk) * x;
						qk[i] += error * (wk[i] / sumOfWk) * y;
						rk[i] += error * (wk[i] / sumOfWk);
					}
					if (dataIndex % numOfSamplesInBatch == 0 || dataIndex + 1 >= dataset.size()) {
						for (int i = 0; i < numOfRules; i++) {
							a[i] += fuzzyLearningRate * ak[i];
							b[i] += fuzzyLearningRate * bk[i];
							c[i] += fuzzyLearningRate * ck[i];
							d[i] += fuzzyLearningRate * dk[i];
							
							p[i] += functionLearningRate * pk[i];
							q[i] += functionLearningRate * qk[i];
							r[i] += functionLearningRate * rk[i];
							
							ak[i] = 0;
							bk[i] = 0;
							ck[i] = 0;
							dk[i] = 0;
							
							pk[i] = 0;
							qk[i] = 0;
							rk[i] = 0;
						}
					}
				}
				double totalError = 0;
				for (int i = 0; i < dataset.size(); i++) {
					totalError += Math.pow((dataset.get(i).getValue() - ok[i]), 2);
				}
				totalError /= 2 * dataset.size();
				if ((cnt + 1) % 1000 == 0) {
					System.out.println("Number of iterations: " + Integer.toString(cnt + 1) + ", Total error: " + Double.toString(totalError));
					errorWriter.write(Integer.toString(cnt + 1) + "," + Double.toString(totalError) + "\n");
				}
			}
			try (BufferedWriter bw1 = Files.newBufferedWriter(Paths.get("naucena_funkcija_1uzorak_10pravila.txt"));
				 BufferedWriter bw2 = Files.newBufferedWriter(Paths.get("funkcija_greske_1uzorak_10pravila.txt"))) {
					for (int i = 0; i < dataset.size(); i++) {
						bw1.write(dataset.get(i).getKey()[0].toString() + "," + dataset.get(i).getKey()[1].toString() + "," +
								  Double.toString(ok[i]) + "\n");
						bw2.write(dataset.get(i).getKey()[0].toString() + "," + dataset.get(i).getKey()[1].toString() + "," +
								  Double.toString(dataset.get(i).getValue() - ok[i]) + "\n");
					}
				} catch (Exception ex) {
					System.out.println("Error while writing data.");
					System.exit(1);
				}
			try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("sigmoide_sgd.txt"))) {
				for (int i = 0; i < numOfRules; i++) {
					bw.write(Double.toString(a[i]) + "," + Double.toString(b[i]) + "," + Double.toString(c[i]) + "," + Double.toString(d[i]) + "\n");
				}
			} catch (Exception ex) {
				System.out.println("Error while writing data.");
				System.exit(1);
			}
		} catch (Exception ex) {
			System.out.println("An error occurred:\n" + ex.getMessage() + "\nProgram will exit.");
		}
	}
	
	private static Map<Integer, AbstractMap.SimpleEntry<Double[], Double>> createDataset() {
		Map<Integer, AbstractMap.SimpleEntry<Double[], Double>> dataset = new HashMap<>();
		int counter = 0;
		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("funkcija.txt"))) {
			for (int x = -4; x <= 4; x++) {
				for (int y = -4; y <= 4; y++) {
					Double[] key = new Double[2];
					key[0] = Double.valueOf(x);
					key[1] = Double.valueOf(y);
					double value = (Math.pow(key[0] - 1, 2) + Math.pow(key[1] + 2, 2) - 5 * key[0] * key[1] + 3) * Math.pow(Math.cos(key[0] / 5), 2);
					bw.write(key[0].toString() + "," + key[1].toString() + "," + Double.toString(value) + "\n");
					dataset.put(counter++, new AbstractMap.SimpleEntry<>(key, value));
				}
			}
		} catch (Exception ex) {
			System.out.println("Error while creating data.");
			System.exit(1);
		}
		return dataset;
	}
	
}
