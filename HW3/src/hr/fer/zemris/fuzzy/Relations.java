package hr.fer.zemris.fuzzy;

public class Relations {
	
	public static boolean isUtimesURelation(IFuzzySet relation) {
		IDomain firstDomain = relation.getDomain().getComponent(0);
		IDomain secondDomain = relation.getDomain().getComponent(1);
		if (firstDomain.getCardinality() != secondDomain.getCardinality()) {
			return false;
		}
		for (int i = 0; i < firstDomain.getCardinality(); i++) {
			if (!firstDomain.elementForIndex(i).equals(secondDomain.elementForIndex(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isSymmetric(IFuzzySet relation) {
		if (!isUtimesURelation(relation)) {
			return false;
		}
		for (DomainElement y : relation.getDomain().getComponent(1)) {
			for (DomainElement x : relation.getDomain().getComponent(0)) {
				int[] xy = fillWithTwoDomainElementComponents(x, y);
				int[] yx = fillWithTwoDomainElementComponents(y, x);
				if (relation.getValueAt(new DomainElement(xy)) != relation.getValueAt(new DomainElement(yx))) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean isReflexive(IFuzzySet relation) {
		if (!isUtimesURelation(relation)) {
			return false;
		}
		for (DomainElement x : relation.getDomain().getComponent(0)) {
			if (relation.getValueAt(new DomainElement(fillWithTwoDomainElementComponents(x, x))) != 1) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isMaxMinTransitive(IFuzzySet relation) {
		if (!isUtimesURelation(relation)) {
			return false;
		}
		for (DomainElement element : relation.getDomain()) {
			int x = element.getComponentValue(0);
			int z = element.getComponentValue(1);
			double maxMinimum = Double.NEGATIVE_INFINITY;
			for (DomainElement yElement : relation.getDomain().getComponent(0)) {
				int y = yElement.getComponentValue(0); 
				double min = Math.min(relation.getValueAt(DomainElement.of(x, y)), relation.getValueAt(DomainElement.of(y, z)));
				if (min > maxMinimum) {
					maxMinimum = min;
				}
			}
			if (relation.getValueAt(element) < maxMinimum) {
				return false;
			}
		}
		return true;
	}
	
	public static IFuzzySet compositionOfBinaryRelations(IFuzzySet r1, IFuzzySet r2) {
		IDomain r1V = r1.getDomain().getComponent(1);
		IDomain r2V = r2.getDomain().getComponent(0);
		if (r1V.getCardinality() != r2V.getCardinality()) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < r1V.getCardinality(); i++) {
			if (!r1V.elementForIndex(i).equals(r2V.elementForIndex(i))) {
				throw new IllegalArgumentException();
			}
		}
		return new IFuzzySet() {
			
			@Override
			public double getValueAt(DomainElement element) {
				int x = element.getComponentValue(0);
				int z = element.getComponentValue(1);
				double maxMinimum = Double.NEGATIVE_INFINITY;
				for (DomainElement yElement : r1V) {
					int y = yElement.getComponentValue(0);
					double min = Math.min(r1.getValueAt(DomainElement.of(x, y)), r2.getValueAt(DomainElement.of(y, z)));
					if (min > maxMinimum) {
						maxMinimum = min;
					}
				}
				return maxMinimum;
			}
			
			@Override
			public IDomain getDomain() {
				return new CompositeDomain(
											(SimpleDomain)r1.getDomain().getComponent(0),
											(SimpleDomain)r2.getDomain().getComponent(1)
										  );
			}
			
		};
	}
	
	public static boolean isFuzzyEquivalence(IFuzzySet r) {
		return isReflexive(r) && isSymmetric(r) && isMaxMinTransitive(r);
	}
	
	private static int[] fillWithTwoDomainElementComponents(DomainElement first, DomainElement second) {
		int[] toFill = new int[first.getNumberOfComponents() + second.getNumberOfComponents()];
		for (int i = 0; i < first.getNumberOfComponents(); i++) {
			toFill[i] = first.getComponentValue(i);
		}
		for (int i = 0; i < second.getNumberOfComponents(); i++) {
			toFill[first.getNumberOfComponents() + i] = second.getComponentValue(i);
		}
		return toFill;
	}
	
}
