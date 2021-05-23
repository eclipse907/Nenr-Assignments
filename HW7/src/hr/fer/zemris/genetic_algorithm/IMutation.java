package hr.fer.zemris.genetic_algorithm;

public interface IMutation {

	Chromosome mutate(Chromosome chromosome);
	double getMethodProbability();
	
}
