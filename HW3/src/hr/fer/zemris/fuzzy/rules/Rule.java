package hr.fer.zemris.fuzzy.rules;

import hr.fer.zemris.fuzzy.IInference;
import hr.fer.zemris.fuzzy.IRule;

public abstract class Rule implements IRule {
	
	protected IInference inferenceMethod;

	public Rule(IInference inferenceMethod) {
		this.inferenceMethod = inferenceMethod;
	}
		
}
