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
		List<Chromosome> selectedChromosomes = new ArrayList<>();
		List<Integer> selectedIndexes = new ArrayList<>();
		while (selectedChromosomes.size() < 3) {
			int index = randomGenerator.nextInt(population.size());
			if (!selectedIndexes.contains(index)) {
				selectedChromosomes.add(population.get(index));
				selectedIndexes.add(index);
			}
		}
		Collections.sort(selectedChromosomes);
		return selectedChromosomes;
	}

}
