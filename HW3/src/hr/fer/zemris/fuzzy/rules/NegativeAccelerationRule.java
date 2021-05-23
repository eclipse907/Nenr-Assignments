package hr.fer.zemris.fuzzy.rules;

import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.IInference;
import hr.fer.zemris.fuzzy.MutableFuzzySet;
import hr.fer.zemris.fuzzy.domains.Domains;
import hr.fer.zemris.fuzzy.variables.Variables;

public class NegativeAccelerationRule extends Rule {

	public NegativeAccelerationRule(IInference inferenceMethod) {
		super(inferenceMethod);
	}

	@Override
	public IFuzzySet infereRuleFromValues(int L, int D, int LK, int DK, int V, int S) {
		List<Double> antecedentValues = new ArrayList<>();
		antecedentValues.add(Variables.TO_FAST_VARIABLE.getValueAt(DomainElement.of(V)));
		MutableFuzzySet set = new MutableFuzzySet(Domains.ACCELERATION_DOMAIN);
		for (DomainElement element : Domains.ACCELERATION_DOMAIN) {
			set.set(element, inferenceMethod.calculateImplication(antecedentValues,
					Variables.NEGATIVE_ACCELERATION_VARIABLE.getValueAt(element)));
		}
		return set;
	}
	
	@Override
	public String getName() {
		return "Negative acceleration rule";
	}
	
}
