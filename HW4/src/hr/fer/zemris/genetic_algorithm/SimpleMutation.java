package hr.fer.zemris.genetic_algorithm;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleMutation implements IMutation {

	private Random randomGenerator;
	private double mutationProbability;
	
	public SimpleMutation(double mutationProbability) {
		randomGenerator = new Random();
		this.mutationProbability = mutationProbability;
	}
	
	@Override
	public Chromosome mutate(Chromosome chromosome) {
		for (int i = 0; i < chromosome.getGenes().size(); i++) {
			if (randomGenerator.nextDouble() < mutationProbability) {
				chromosome.getGenes().set(i, ThreadLocalRandom.current().nextDouble(-4, 4));
			}
		}
		return chromosome;
	}

}
