package hr.fer.zemris.genetic_algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RouletteWheelSelection implements ISelection {

	@Override
	public List<Chromosome> selectChromosomes(List<Chromosome> population) {
		double sum = 0;
		for (Chromosome chromosome : population) {
			sum += chromosome.getCostScore();
		}
		double selector = ThreadLocalRandom.current().nextDouble(0, sum);
		double area = 0;
		List<Chromosome> selected = new ArrayList<>();
		for (Chromosome chromosome : population) {
			area += chromosome.getCostScore();
			if (selector <= area) {
				selected.add(chromosome);
				break;
			}
		}
		return selected;
	}

}
