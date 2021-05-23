package hr.fer.zemris.fuzzy;

import java.util.Scanner;
import hr.fer.zemris.fuzzy.controlsystems.AccelerationFuzzyControlSystem;
import hr.fer.zemris.fuzzy.controlsystems.TurningFuzzyControlSystem;
import hr.fer.zemris.fuzzy.defuzzifiers.COADefuzzifier;
import hr.fer.zemris.fuzzy.inferences.MinimumInference;
import hr.fer.zemris.fuzzy.inferences.ProductInference;

public class Main {

	public static void main(String[] args) {
		IInference inference = new ProductInference();
		IDefuzzifier defuzzifier = new COADefuzzifier();
		IFuzzyControlSystem accelerationFcs = new AccelerationFuzzyControlSystem(inference, defuzzifier);
		IFuzzyControlSystem turningFcs = new TurningFuzzyControlSystem(inference, defuzzifier);
		Scanner sc = new Scanner(System.in);
		while (true) {
			String line = sc.nextLine();
			if (line.equals("KRAJ")) {
				break;
			}
			String[] data = line.split(" ");
			int L = Integer.parseInt(data[0]);
			int D = Integer.parseInt(data[1]);
			int LK = Integer.parseInt(data[2]);
			int DK = Integer.parseInt(data[3]);
			int V = Integer.parseInt(data[4]);
			int S = Integer.parseInt(data[5]);
			int A = accelerationFcs.decide(L, D, LK, DK, V, S);
			int K = turningFcs.decide(L, D, LK, DK, V, S);
			System.out.println(Integer.toString(A) + " " + Integer.toString(K));
			System.out.flush();
		}
		sc.close();
	}
	
}
