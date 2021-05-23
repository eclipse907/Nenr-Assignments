package hr.fer.zemris.fuzzy.controlsystems;

import hr.fer.zemris.fuzzy.IDefuzzifier;
import hr.fer.zemris.fuzzy.IInference;
import hr.fer.zemris.fuzzy.rulesdatabases.TurningRulesDatabase;

public class TurningFuzzyControlSystem extends FuzzyControlSystem {

	public TurningFuzzyControlSystem(IInference inferenceMethod, IDefuzzifier defuzzifier) {
		super(inferenceMethod, defuzzifier);
	}

	@Override
	protected void createRulesDatabase() {
		database = new TurningRulesDatabase(inferenceMethod);
	}

}
