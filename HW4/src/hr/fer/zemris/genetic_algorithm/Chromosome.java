package hr.fer.zemris.genetic_algorithm;

import java.util.List;

public class Chromosome implements Comparable<Chromosome> {
	
	private List<Double> genes;
	private double costScore;

	public Chromosome(List<Double> genes) {
		this.genes = genes;
	}

	public List<Double> getGenes() {
		return genes;
	}
	
	public double getCostScore() {
		return costScore;
	}

	public void setCostScore(double costScore) {
		this.costScore = costScore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genes == null) ? 0 : genes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chromosome other = (Chromosome) obj;
		if (genes == null) {
			if (other.genes != null)
				return false;
		} else if (!genes.equals(other.genes))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Parameters are: ");
		for (int i = 0; i < genes.size(); i++) {
			sb.append("Beta " + i + ": " + genes.get(i) + " ");
		}
		sb.append("Cost score is: " + costScore);
		return sb.toString();
	}

	@Override
	public int compareTo(Chromosome o) {
		return -Double.compare(costScore, o.getCostScore());
	}
	
}
