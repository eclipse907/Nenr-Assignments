package hr.fer.zemris.genetic_algorithm;

import java.util.ArrayList;
import java.util.List;

public class WholeArithmeticRecombinationCrossover implements ICrossover {

	@Override
	public Chromosome doCrossover(Chromosome firstParent, Chromosome secondParent) {
		List<Double> genes = new ArrayList<>();
		for (int i = 0; i < firstParent.getGenes().size(); i++) {
			genes.add((firstParent.getGenes().get(i) + secondParent.getGenes().get(i)) / 2);
		}
		return new Chromosome(genes);
	}
	
}
