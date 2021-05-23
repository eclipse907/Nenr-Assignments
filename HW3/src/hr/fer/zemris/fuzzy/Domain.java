package hr.fer.zemris.fuzzy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Domain implements IDomain {
	
	public static Domain intRange(int first, int last) {
		return new SimpleDomain(first, last);
	}
	
	public static Domain combine(IDomain firstDomain, IDomain secondDomain) {
		List<SimpleDomain> domains = new ArrayList<>();
		if (firstDomain.getNumberOfComponents() <= 1) {
			domains.add((SimpleDomain)firstDomain);
		} else {
			for (int i = 0; i < firstDomain.getNumberOfComponents(); i++) {
				domains.add((SimpleDomain)firstDomain.getComponent(i));
			}
		}
		if (secondDomain.getNumberOfComponents() <= 1) {
			domains.add((SimpleDomain)secondDomain);
		} else {
			for (int i = 0; i < secondDomain.getNumberOfComponents(); i++) {
				domains.add((SimpleDomain)secondDomain.getComponent(i));
			}
		}
		return new CompositeDomain(domains.toArray(new SimpleDomain[0]));
	}
	
	@Override
	public int indexOfElement(DomainElement element) {
		int currentIndex = 0;
		for (DomainElement el : this) {
			if (el.equals(element)) {
				break;
			}
			currentIndex++;
		}
		return currentIndex;
	}
	
	@Override
	public DomainElement elementForIndex(int index) {
		int currentIndex = 0;
		Iterator<DomainElement> iterator = iterator();
		while (iterator.hasNext() && currentIndex < index) {
			iterator.next();
			currentIndex++;
		}
		return iterator.next();
	}
	
}
