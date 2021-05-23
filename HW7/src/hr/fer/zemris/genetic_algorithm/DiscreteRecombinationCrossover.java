package hr.fer.zemris.genetic_algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DiscreteRecombinationCrossover implements ICrossover {

	@Override
	public Chromosome doCrossover(Chromosome firstParent, Chromosome secondParent) {
		List<Double> genes = new ArrayList<>();
		for (int i = 0; i < firstParent.getGenes().size(); i++) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, 2);
			if (randomNum == 0) {
				genes.add(firstParent.getGenes().get(i));
			} else {
				genes.add(secondParent.getGenes().get(i));
			}
		}
		return new Chromosome(genes);
	}

}
