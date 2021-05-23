package hr.fer.zemris.genetic_algorithm;

import java.util.List;

public class CostFunction implements ICostFunction {
	
	private List<Double> x;
	private List<Double> y;
	private List<Double> realValues;
	
	public CostFunction(List<Double> x, List<Double> y, List<Double> realValues) {
		this.x = x;
		this.y = y;
		this.realValues = realValues;
	}

	@Override
	public double calculateValue(List<Double> params) {
		double sum = 0;
		for (int i = 0; i < realValues.size(); i++) {
			sum += Math.pow(realValues.get(i) -
							(Math.sin(params.get(0) + params.get(1) * x.get(i)) +
							 params.get(2) * Math.cos(x.get(i) * (params.get(3) + y.get(i))) *
							 (1 / (1 + Math.exp(Math.pow(x.get(i) - params.get(4), 2))))), 2);
		}
		return sum / realValues.size();
	}
	
}
