package hr.fer.zemris.fuzzy;

@FunctionalInterface
public interface IDefuzzifier {

	int defuzzify(IFuzzySet set);
	
}
