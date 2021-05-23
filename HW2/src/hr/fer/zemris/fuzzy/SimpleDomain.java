package hr.fer.zemris.fuzzy;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleDomain extends Domain {
	
	private int first;
	private int last;
	
	public SimpleDomain(int first, int last) {
		this.first = first;
		this.last = last;
	}

	@Override
	public int getCardinality() {
		return last - first;
	}

	@Override
	public IDomain getComponent(int index) {
		return this;
	}

	@Override
	public int getNumberOfComponents() {
		return 1;
	}

	@Override
	public Iterator<DomainElement> iterator() {
		return new SimpleDomainIterator();
	}

	public int getFirst() {
		return first;
	}

	public int getLast() {
		return last;
	}
	
	private class SimpleDomainIterator implements Iterator<DomainElement> {
		
		private int nextElement;
		
		public SimpleDomainIterator() {
			nextElement = first;
		}

		@Override
		public boolean hasNext() {
			return nextElement < last;
		}

		@Override
		public DomainElement next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return new DomainElement(nextElement++);
		}
		
	}
	
}
