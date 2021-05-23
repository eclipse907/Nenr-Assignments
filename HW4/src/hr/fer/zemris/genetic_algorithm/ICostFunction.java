package hr.fer.zemris.genetic_algorithm;

import java.util.List;

@FunctionalInterface
public interface ICostFunction {

	double calculateValue(List<Double> params);
	
}
