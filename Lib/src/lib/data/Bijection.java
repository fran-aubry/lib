package lib.data;

import java.util.HashMap;

public class Bijection<E, F> {

	private HashMap<E, F> EtoF;
	private HashMap<F, E> FtoE;
	
	public Bijection() {
		EtoF = new HashMap<>();
		FtoE = new HashMap<>();
	}
	
	public void put(E e, F f) {
		EtoF.put(e, f);
		FtoE.put(f, e);
	}
	
	public F get1(E e) {
		return EtoF.get(e);
	}
	
	public E get2(F f) {
		return FtoE.get(f);
	}
	
}
