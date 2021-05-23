package hr.fer.zemris.genetic_algorithm;

import java.util.Random;

public class Mutation1 implements IMutation {
	
	private double methodProbability;
	private double mutationProbability;
	private double sigma;
	private Random randomGenerator;
	
	public Mutation1(double methodProbability, double mutationProbability, double sigma) {
		this.methodProbability = methodProbability;
		this.mutationProbability = mutationProbability;
		this.sigma = sigma;
		randomGenerator = new Random();
	}

	@Override
	public Chromosome mutate(Chromosome chromosome) {
		for (int i = 0; i < chromosome.getGenes().size(); i++) {
			if (randomGenerator.nextDouble() < mutationProbability) {
				chromosome.getGenes().set(i, chromosome.getGenes().get(i) + randomGenerator.nextGaussian() * sigma);
			}
		}
		return chromosome;
	}

	@Override
	public double getMethodProbability() {
		return methodProbability;
	}

}
