package hr.fer.zemris.fuzzy.controlsystems;

import hr.fer.zemris.fuzzy.IDefuzzifier;
import hr.fer.zemris.fuzzy.IInference;
import hr.fer.zemris.fuzzy.rulesdatabases.AccelerationRulesDatabase;

public class AccelerationFuzzyControlSystem extends FuzzyControlSystem {

	public AccelerationFuzzyControlSystem(IInference inferenceMethod, IDefuzzifier defuzzifier) {
		super(inferenceMethod, defuzzifier);
	}

	@Override
	protected void createRulesDatabase() {
		database = new AccelerationRulesDatabase(inferenceMethod);
	}

}
