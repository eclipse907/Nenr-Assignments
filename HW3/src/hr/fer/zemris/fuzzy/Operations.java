package hr.fer.zemris.fuzzy;

public class Operations {

	public static IFuzzySet unaryOperation(IFuzzySet fuzzySet, IUnaryFunction function) {
		return new IFuzzySet() {
			
			@Override
			public double getValueAt(DomainElement element) {
				return function.valueAt(fuzzySet.getValueAt(element));
			}
			
			@Override
			public IDomain getDomain() {
				return fuzzySet.getDomain();
			}
			
		};
	}
	
	public static IFuzzySet binaryOperation(IFuzzySet firstFuzzySet, IFuzzySet secondFuzzySet, IBinaryFunction function) {
		return new IFuzzySet() {
			
			@Override
			public double getValueAt(DomainElement element) {
				return function.valueAt(firstFuzzySet.getValueAt(element), secondFuzzySet.getValueAt(element));
			}
			
			@Override
			public IDomain getDomain() {
				return firstFuzzySet.getDomain();
			}
			
		};
	}
	
	public static IUnaryFunction zadehNot() {
		return membershipValue -> 1 - membershipValue;
	}
	
	public static IBinaryFunction zadehAnd() {
		return (firstMembershipValue, secondMembershipValue) -> Math.min(firstMembershipValue, secondMembershipValue);
	}
	
	public static IBinaryFunction zadehOr() {
		return (firstMembershipValue, secondMembershipValue) -> Math.max(firstMembershipValue, secondMembershipValue);
	}
	
	public static IBinaryFunction hamacherTNorm(double param) {
		return (a, b) -> a * b / (param + (1 - param) * (a + b - a * b));
	}
	
	public static IBinaryFunction hamacherSNorm(double param) {
		return (a, b) -> (a + b - (2 - param) * a * b) / (1 - (1 - param) * a * b);
	}
	
}
