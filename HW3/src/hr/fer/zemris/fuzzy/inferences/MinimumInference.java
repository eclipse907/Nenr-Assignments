package hr.fer.zemris.fuzzy.inferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.IInference;
import hr.fer.zemris.fuzzy.IRule;
import hr.fer.zemris.fuzzy.MutableFuzzySet;
import hr.fer.zemris.fuzzy.rulesdatabases.RulesDatabase;

public class MinimumInference implements IInference {

	@Override
	public double calculateImplication(List<Double> antecedentValues, double y) {
		return Math.min(Collections.min(antecedentValues), y);
	}

	@Override
	public IFuzzySet calculateFinalConclusion(int L, int D, int LK, int DK, int V, int S, RulesDatabase database) {
		List<IFuzzySet> rules = new ArrayList<>();
		for (IRule rule : database.getRules()) {
			rules.add(rule.infereRuleFromValues(L, D, LK, DK, V, S));
		}
		List<Double> values = new ArrayList<>();
		MutableFuzzySet set = new MutableFuzzySet(database.getDomain());
		for (DomainElement element : database.getDomain()) {
			values.clear();
			for (IFuzzySet rule : rules) {
				values.add(rule.getValueAt(element));
			}
			set.set(element, Collections.max(values));
		}
		return set;
	}

}
