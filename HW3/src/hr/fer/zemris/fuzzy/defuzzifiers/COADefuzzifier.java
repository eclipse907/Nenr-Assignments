package hr.fer.zemris.fuzzy.defuzzifiers;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IDefuzzifier;
import hr.fer.zemris.fuzzy.IFuzzySet;

public class COADefuzzifier implements IDefuzzifier {

	@Override
	public int defuzzify(IFuzzySet set) {
		double numerator = 0;
		double denominator = 0;
		for (DomainElement element : set.getDomain()) {
			numerator += set.getValueAt(element) * element.getComponentValue(0);
			denominator += set.getValueAt(element);
		}
		return (int)(numerator / denominator);
	}

}
