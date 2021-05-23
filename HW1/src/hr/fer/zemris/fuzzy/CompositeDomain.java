package hr.fer.zemris.fuzzy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CompositeDomain extends Domain {
	
	private SimpleDomain[] components;
	
	public CompositeDomain(SimpleDomain... components) {
		this.components = components;
	}

	@Override
	public int getCardinality() {
		int cardinality = 1;
		for (SimpleDomain domain : components) {
			cardinality *= domain.getCardinality();
		}
		return cardinality;
	}

	@Override
	public IDomain getComponent(int index) {
		return components[index];
	}

	@Override
	public int getNumberOfComponents() {
		return components.length;
	}

	@Override
	public Iterator<DomainElement> iterator() {
		return new CompositeDomainIterator();
	}
	
	private class CompositeDomainIterator implements Iterator<DomainElement> {
		
		private List<Iterator<DomainElement>> iterators;
		private int nextIndex;
		private static final int NEW = -1;
		private static final int DONE = -2;
		private DomainElement[] lastResult;
		
		public CompositeDomainIterator() {
			iterators = new ArrayList<>();
			for (int i = 0; i < components.length; i++) {
				iterators.add(components[i].iterator());
			}
			nextIndex = NEW;
			lastResult = new DomainElement[iterators.size()];
		}

		@Override
		public boolean hasNext() {
			if (nextIndex == NEW) {
				nextIndex = 0;
				for (Iterator<DomainElement> it : iterators) {
					if (!it.hasNext()) {
						nextIndex = DONE;
						break;
					}
				}
			} else if (nextIndex >= iterators.size()) {
				for (nextIndex = iterators.size() - 1; nextIndex >= 0; nextIndex--) {
					Iterator<DomainElement> it = iterators.get(nextIndex);
					if (it.hasNext()) {
						break;
					}
					if (nextIndex == 0) {
						nextIndex = DONE;
						break;
					}
					it = components[nextIndex].iterator();
					iterators.set(nextIndex, it);
					if (!it.hasNext()) {
						nextIndex = DONE;
						break;
					}
				}
			}
			return nextIndex >= 0;
		}

		@Override
		public DomainElement next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			for (; nextIndex < iterators.size(); nextIndex++) {
				lastResult[nextIndex] = iterators.get(nextIndex).next();
			}
			List<Integer> result = new ArrayList<>();
			for (DomainElement el : lastResult) {
				for (int i = 0; i < el.getNumberOfComponents(); i++) {
					result.add(el.getComponentValue(i));
				}
			}
			int[] arrayResult = new int[result.size()];
			for (int i = 0; i < result.size(); i++) {
				arrayResult[i] = result.get(i);
			}
			return new DomainElement(arrayResult);
		}
		
	}

}
