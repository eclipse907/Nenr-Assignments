package hr.fer.zemris.fuzzy.variables;

import hr.fer.zemris.fuzzy.CalculatedFuzzySet;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.StandardFuzzySets;
import hr.fer.zemris.fuzzy.domains.Domains;

public class Variables {
	
	public static final IFuzzySet VERY_CLOSE_DISTANCE_VARIABLE = new CalculatedFuzzySet(
			Domains.DISTANCE_DOMAIN, StandardFuzzySets.lFunction(60, 100));
	

	
	public static final IFuzzySet TO_SLOW_VARIABLE = new CalculatedFuzzySet(
			Domains.SPEED_DOMAIN, StandardFuzzySets.lFunction(40, 50));
	
	public static final IFuzzySet TO_FAST_VARIABLE = new CalculatedFuzzySet(
			Domains.SPEED_DOMAIN, StandardFuzzySets.gammaFunction(50, 100));
	
	

	public static final IFuzzySet NEGATIVE_ACCELERATION_VARIABLE = new CalculatedFuzzySet(
			Domains.ACCELERATION_DOMAIN, StandardFuzzySets.lFunction(-40, 10));
	
	public static final IFuzzySet NORMAL_ACCELERATION_VARIABLE = new CalculatedFuzzySet(
			Domains.ACCELERATION_DOMAIN, StandardFuzzySets.gammaFunction(0, 40));
	
	
	
	public static final IFuzzySet TURN_HARD_RIGHT_VARIABLE = new CalculatedFuzzySet(
			Domains.TURN_DOMAIN, StandardFuzzySets.lFunction(-90, -70));
	
	public static final IFuzzySet TURN_HARD_LEFT_VARIABLE = new CalculatedFuzzySet(
			Domains.TURN_DOMAIN, StandardFuzzySets.gammaFunction(70, 90));
	
}
