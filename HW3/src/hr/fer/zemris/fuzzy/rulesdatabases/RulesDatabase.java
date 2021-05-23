package hr.fer.zemris.fuzzy.rulesdatabases;

import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.fuzzy.IDomain;
import hr.fer.zemris.fuzzy.IInference;
import hr.fer.zemris.fuzzy.IRule;

public abstract class RulesDatabase {
	
	protected List<IRule> rules;
	protected IDomain domain;
	
	public RulesDatabase(IInference inferenceMethod) {
		rules = new ArrayList<>();
		createDatabase(inferenceMethod);
	}

	public List<IRule> getRules() {
		return rules;
	}
	
	public IDomain getDomain() {
		return domain;
	}
	
	protected abstract void createDatabase(IInference inferenceMethod);
	
}
