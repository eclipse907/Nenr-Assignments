package hr.fer.zemris.fuzzy;

import java.util.Scanner;
import hr.fer.zemris.fuzzy.defuzzifiers.COADefuzzifier;
import hr.fer.zemris.fuzzy.inferences.MinimumInference;
import hr.fer.zemris.fuzzy.inferences.ProductInference;
import hr.fer.zemris.fuzzy.rulesdatabases.AccelerationRulesDatabase;
import hr.fer.zemris.fuzzy.rulesdatabases.RulesDatabase;
import hr.fer.zemris.fuzzy.rulesdatabases.TurningRulesDatabase;

public class TestOneRuleMain {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Choose database:\n1-Direction\n2-Acceleration\nInput:");
			int databaseChoice = Integer.parseInt(sc.nextLine().trim());
			RulesDatabase database;
			System.out.println("Choose inference method:\n1-Minimum\n2-Product\nInput:");
			int inferenceChoice = Integer.parseInt(sc.nextLine().trim());
			IInference inference;
			if (inferenceChoice == 2) {
				inference = new ProductInference();
			} else {
				inference = new MinimumInference();
			}
			if (databaseChoice == 2) {
				database = new AccelerationRulesDatabase(inference);
			} else {
				database = new TurningRulesDatabase(inference);
			}
			System.out.println("Database rules:");
			int i = 1;
			for (IRule rule : database.getRules()) {
				System.out.println("Rule " + Integer.toString(i) + ": " + rule.getName());
			}
			System.out.println("Choose one:");
			int ruleChoice = Integer.parseInt(sc.nextLine().trim()) - 1;
			System.out.println("Enter L, D, LK, DK, V and S seperated by space:");
			String[] data = sc.nextLine().trim().split(" ");
			int L = Integer.parseInt(data[0]);
			int D = Integer.parseInt(data[1]);
			int LK = Integer.parseInt(data[2]);
			int DK = Integer.parseInt(data[3]);
			int V = Integer.parseInt(data[4]);
			int S = Integer.parseInt(data[5]);
			IFuzzySet set = database.getRules().get(ruleChoice).infereRuleFromValues(L, D, LK, DK, V, S);
			System.out.println("Rule fuzzy set:");
			for (DomainElement element : set.getDomain()) {
				System.out.println("f(" + element + ")=" + set.getValueAt(element));
			}
			System.out.println("Rule defuzzified value: " + new COADefuzzifier().defuzzify(set));
		}
	}
	
}
