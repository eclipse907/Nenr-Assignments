package hr.fer.zemris.fuzzy.rules;

import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.IInference;
import hr.fer.zemris.fuzzy.MutableFuzzySet;
import hr.fer.zemris.fuzzy.domains.Domains;
import hr.fer.zemris.fuzzy.variables.Variables;

public class TurnHardRightRule extends Rule {

	public TurnHardRightRule(IInference inferenceMethod) {
		super(inferenceMethod);
	}

	@Override
	public IFuzzySet infereRuleFromValues(int L, int D, int LK, int DK, int V, int S) {
		List<Double> antecedentValues = new ArrayList<>();
		antecedentValues.add(Variables.VERY_CLOSE_DISTANCE_VARIABLE.getValueAt(DomainElement.of(LK)));
		antecedentValues.add(Variables.VERY_CLOSE_DISTANCE_VARIABLE.getValueAt(DomainElement.of(L)));
		MutableFuzzySet set = new MutableFuzzySet(Domains.TURN_DOMAIN);
		for (DomainElement element : Domains.TURN_DOMAIN) {
			set.set(element, inferenceMethod.calculateImplication(antecedentValues,
					Variables.TURN_HARD_RIGHT_VARIABLE.getValueAt(element)));
		}
		return set;
	}
	
	@Override
	public String getName() {
		return "Turn hard right rule";
	}

}
