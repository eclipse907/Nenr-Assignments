package hr.fer.zemris.genetic_algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ThreeWayTournamentSelection implements ISelection {
	
	private Random randomGenerator;
	
	public ThreeWayTournamentSelection() {
		randomGenerator = new Random();
	}

	@Override
	public List<Chromosome> selectChromosomes(List<Chromosome> population) {
		List<Chromosome> selected = new ArrayList<>();
		while (selected.size() < 3) {
			Chromosome selectedChromosome = population.get(randomGenerator.nextInt(population.size()));
			if (!selected.contains(selectedChromosome)) {
				selected.add(selectedChromosome);
			}
		}
		Collections.sort(selected);
		return selected;
	}

}
