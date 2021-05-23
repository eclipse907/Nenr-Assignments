package hr.fer.zemris.fuzzy;

import java.util.List;

import hr.fer.zemris.fuzzy.rulesdatabases.RulesDatabase;

public interface IInference {

	double calculateImplication(List<Double> antecedentValues, double y);
	IFuzzySet calculateFinalConclusion(int L, int D, int LK, int DK, int V, int S, RulesDatabase database);
	
}
