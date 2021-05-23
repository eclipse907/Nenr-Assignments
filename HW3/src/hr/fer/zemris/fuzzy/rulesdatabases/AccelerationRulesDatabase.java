package hr.fer.zemris.fuzzy.rulesdatabases;

import hr.fer.zemris.fuzzy.IInference;
import hr.fer.zemris.fuzzy.domains.Domains;
import hr.fer.zemris.fuzzy.rules.NegativeAccelerationRule;
import hr.fer.zemris.fuzzy.rules.NormalAccelerationRule;

public class AccelerationRulesDatabase extends RulesDatabase {

	public AccelerationRulesDatabase(IInference inferenceMethod) {
		super(inferenceMethod);
	}

	@Override
	protected void createDatabase(IInference inferenceMethod) {
		domain = Domains.ACCELERATION_DOMAIN;
		rules.add(new NegativeAccelerationRule(inferenceMethod));
		rules.add(new NormalAccelerationRule(inferenceMethod));
	}

}
