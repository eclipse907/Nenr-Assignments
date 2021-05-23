package hr.fer.zemris.genetic_algorithm;

@FunctionalInterface
public interface IMutation {

	Chromosome mutate(Chromosome chromosome);
	
}
