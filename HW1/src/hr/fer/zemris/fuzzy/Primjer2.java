package hr.fer.zemris.fuzzy;

public class Primjer2 {
	
	public static void main(String[] args) {
		IDomain d = Domain.intRange(0, 11);
		
		IFuzzySet set1 = new MutableFuzzySet(d)
				.set(DomainElement.of(0), 1.0)
				.set(DomainElement.of(1), 0.8)
				.set(DomainElement.of(2), 0.6)
				.set(DomainElement.of(3), 0.4)
				.set(DomainElement.of(4), 0.2);
		Debug.printFuzzySet(set1, "Set1:");
		
		IFuzzySet notSet1 = Operations.unaryOperation(
						  set1, Operations.zadehNot());
		Debug.printFuzzySet(notSet1, "notSet1:");
		
		IFuzzySet union = Operations.binaryOperation(
						  set1, notSet1, Operations.zadehOr());
		Debug.printFuzzySet(union, "Set1 union notSet1:");
		
		IFuzzySet hinters = Operations.binaryOperation(
						  set1, notSet1, Operations.hamacherTNorm(1.0));
		Debug.printFuzzySet(hinters, "Set1 intersection with notSet1 using parameterised Hamacher T norm with parameter 1.0:");
	}
	
}
