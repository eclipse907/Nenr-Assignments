package hr.fer.zemris.genetic_algorithm;

import java.util.List;

@FunctionalInterface
public interface ISelection {
	
	List<Chromosome> selectChromosomes(List<Chromosome> population);
	
}
