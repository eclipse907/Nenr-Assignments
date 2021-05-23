package hr.fer.zemris.fuzzy;

public interface IRule {
	
	IFuzzySet infereRuleFromValues(int L, int D, int LK, int DK, int V, int S);
	String getName();
	
}
