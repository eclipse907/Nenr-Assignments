package hr.fer.zemris.fuzzy;

public class CalculatedFuzzySet implements IFuzzySet {
	
	private IDomain domain;
	private IIntUnaryFunction membershipFunction;

	public CalculatedFuzzySet(IDomain domain, IIntUnaryFunction membershipFunction) {
		this.domain = domain;
		this.membershipFunction = membershipFunction;
	}

	@Override
	public IDomain getDomain() {
		return domain;
	}

	@Override
	public double getValueAt(DomainElement element) {
		return membershipFunction.valueAt(element.getComponentValue(0));
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (DomainElement element : domain) {
			sb.append(element + " = " + membershipFunction.valueAt(domain.indexOfElement(element)) + "\n");
		}
		return sb.toString();
	}

}
