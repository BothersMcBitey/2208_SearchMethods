package search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import main.Node;

public class SearchSet<E> implements Set<E> {

	List<E> set;
	
	public SearchSet(){
		set = new ArrayList<E>();
	}
	
	@Override
	public boolean add(E node) {
		Iterator<E> it = set.iterator();
		Node n = (Node) node;
		
		while(it.hasNext()){
			Node current = (Node) it.next();
			if(current.equals(n)){ 
				if(current.getCost() > n.getCost()){				
					it.remove();
					return set.add(node);
				} else {
					return false;
				}
			}
		}		
		return set.add(node);	
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return set.addAll(c);
	}

	@Override
	public void clear() {
		set.clear();
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return set.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return set.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

}
