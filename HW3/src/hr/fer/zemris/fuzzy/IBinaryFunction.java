package hr.fer.zemris.fuzzy;

@FunctionalInterface
public interface IBinaryFunction {

	double valueAt(double first, double second);
	
}
