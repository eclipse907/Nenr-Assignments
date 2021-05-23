package hr.fer.zemris.genetic_algorithm;

@FunctionalInterface
public interface ICrossover {

	Chromosome doCrossover(Chromosome firstParent, Chromosome secondParent); 
	
}
