package hr.fer.zemris.fuzzy;

import hr.fer.zemris.fuzzy.rulesdatabases.RulesDatabase;

public interface IFuzzyControlSystem {

	int decide(int L, int D, int LK, int DK, int V, int S);
	RulesDatabase getDatabase();
	
}
