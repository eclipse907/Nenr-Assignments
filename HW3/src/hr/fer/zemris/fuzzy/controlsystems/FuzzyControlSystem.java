package hr.fer.zemris.fuzzy.controlsystems;

import hr.fer.zemris.fuzzy.IDefuzzifier;
import hr.fer.zemris.fuzzy.IFuzzyControlSystem;
import hr.fer.zemris.fuzzy.IInference;
import hr.fer.zemris.fuzzy.rulesdatabases.RulesDatabase;

public abstract class FuzzyControlSystem implements IFuzzyControlSystem {
	
	protected IInference inferenceMethod;
	private IDefuzzifier defuzzifier;
	protected RulesDatabase database;
	
	public FuzzyControlSystem(IInference inferenceMethod, IDefuzzifier defuzzifier) {
		this.inferenceMethod = inferenceMethod;
		this.defuzzifier = defuzzifier;
		createRulesDatabase();
	}

	@Override
	public int decide(int L, int D, int LK, int DK, int V, int S) {
		return defuzzifier.defuzzify(inferenceMethod.calculateFinalConclusion(L, D, LK, DK, V, S, database));
	}
	
	@Override
	public RulesDatabase getDatabase() {
		return database;
	}
	
	protected abstract void createRulesDatabase();
	
}
