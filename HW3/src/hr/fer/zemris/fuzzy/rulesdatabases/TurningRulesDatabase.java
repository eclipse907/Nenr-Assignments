package hr.fer.zemris.fuzzy.rulesdatabases;

import hr.fer.zemris.fuzzy.IInference;
import hr.fer.zemris.fuzzy.domains.Domains;
import hr.fer.zemris.fuzzy.rules.TurnHardLeftRule;
import hr.fer.zemris.fuzzy.rules.TurnHardRightRule;

public class TurningRulesDatabase extends RulesDatabase {

	public TurningRulesDatabase(IInference inferenceMethod) {
		super(inferenceMethod);
	}

	@Override
	protected void createDatabase(IInference inferenceMethod) {
		domain = Domains.TURN_DOMAIN;
		rules.add(new TurnHardLeftRule(inferenceMethod));
		rules.add(new TurnHardRightRule(inferenceMethod));
	}

}
