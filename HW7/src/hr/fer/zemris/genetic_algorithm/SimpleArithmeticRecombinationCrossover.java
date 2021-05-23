package hr.fer.zemris.genetic_algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleArithmeticRecombinationCrossover implements ICrossover {

	@Override
	public Chromosome doCrossover(Chromosome firstParent, Chromosome secondParent) {
		List<Double> genes = new ArrayList<>();
		int crossoverPoint = ThreadLocalRandom.current().nextInt(0, firstParent.getGenes().size());
		for (int i = 0; i < crossoverPoint; i++) {
			genes.add(firstParent.getGenes().get(i));
		}
		for (int i = crossoverPoint; i < secondParent.getGenes().size(); i++) {
			genes.add((firstParent.getGenes().get(i) + secondParent.getGenes().get(i)) / 2.0f);
		}
		return new Chromosome(genes);
	}

}
