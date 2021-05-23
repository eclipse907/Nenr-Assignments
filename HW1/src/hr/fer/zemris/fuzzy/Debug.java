package hr.fer.zemris.fuzzy;

public class Debug {
	
	public static void printDomain(IDomain domain, String headingText) {
		if(headingText != null) {
			System.out.println(headingText);
		}
		for(DomainElement e : domain) {
			System.out.println("Element domene: " + e);
		}
		System.out.println("Kardinalitet domene je: " + domain.getCardinality());
		System.out.println();
	}
	
	public static void printFuzzySet(IFuzzySet fuzzySet, String headingText) {
		if(headingText != null) {
			System.out.println(headingText);
		}
		for(DomainElement e : fuzzySet.getDomain()) {
			System.out.println("d(" + e + ")=" + fuzzySet.getValueAt(e));
		}
		System.out.println();
	}

}
