package com.rad.statistics.utils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.UnaryOperator;

import javax.activity.InvalidActivityException;

public class ConcurrentBoundedBufferArray<T> {

	private final AtomicReferenceArray<T> buffer;
	private Comparator<T> comparator;

	public ConcurrentBoundedBufferArray(int size) {

		buffer = new AtomicReferenceArray<T>(size);

	}
	public ConcurrentBoundedBufferArray(int size, Comparator<T> comparator) {
		this(size);
		this.comparator = comparator;
	}

	/**
	 * Nullify the array
	 */
	public void clear() {
		
		for(int i=0;i<size();i++) {
			boolean nullified=false;
			do{
				nullified=buffer.compareAndSet(i, buffer.get(i),null );
			}while(!nullified);
		}
	}
	
	public boolean add(T t) throws InvalidActivityException {
		return addItem(t);
	}
	 /**
	   * 
	   * Do a compare and swap, and get the swapped element or null, if it was not
	   * swapped
	   * @param i
	   * 
	   * @param item
	   * 
	   * @return
	 * @throws InvalidActivityException 
	   * 
	   */

	 private T compareAndSwap(int i, T item) throws InvalidActivityException
	  {
	    boolean set = false, greater = false;
	    T t = null;
	    while (!set) {
	      t = buffer.get(i);

	      // either i-th element was replaced with this item
	      // or by some other element
	   
	    	if(t instanceof StorageModel && item instanceof StorageModel)
		      {
		    	  StorageModel r1=(StorageModel) t;
		    	  StorageModel r2= (StorageModel)item;
		    	  long diff=Math.abs(r1.getTimestamp()-r2.getTimestamp()) ;
		    	  long diffSeconds =TimeUnit.MILLISECONDS.toSeconds(diff);
		    	if(diffSeconds> 60)
		    	{
		    		throw new InvalidActivityException();
		    	}
		      }
	      greater = compare(item, t) > 0;
	      set = buffer.compareAndSet(i, t, greater ? item : t);

	    }
	    return greater ? t : null;

	  }

	  private boolean addItem(int fromOffset, T item) throws InvalidActivityException
	  {
	    for (int i = fromOffset; i < size(); i++)
	    {   
	      if (!buffer.compareAndSet(i, null, item))
	      {
	        T swapped = compareAndSwap(i, item);
	        if (swapped != null)
	        {

	          // the item has been placed. so break. but then
	          // the element currently at 'i' has been swapped. so find its new
	          // position, if present
	          if (i + 1 < size()) {
	            addItem(swapped);
	          }

	          return true;
	        }
	      }
	      else {
	        return true;
	      }
	    }

	    return false;

	  }

	  private boolean addItem(T item) throws InvalidActivityException
	  {
	    return addItem(0, item);
	  }

	public T get(int index) {
		if(index<0|| index>size())
			throw new IndexOutOfBoundsException();
		return buffer.get(index);
	}

	public int size() {
		return buffer.length();
	}

	public boolean isEmpty() {
		// Stream.of(buffer).count()==0;
		for (int i = 0; i < size(); i++) {
			if (buffer.get(i) != null)
				return false;
		}

		return true;
	}

	public boolean contains(Object o) {
		if (o != null) {
			for (int i = 0; i < size(); i++) {
				if (o.equals(buffer.get(i)))
					return true;
			}

		}
		return false;
	}


	public <T> T[] toArray() {
		  T[] a = (T[])new Object[size()];
	    for (int i = 0; i < size(); i++) {
	      a[i] = (T) buffer.get(i);
	    }

	    return a;

	  }


	private int compare(T t1, T t2) {
		if (comparator != null) {
			return comparator.compare(t1, t2);
		} else if (t1 instanceof Comparable) {
			return ((Comparable<T>) t1).compareTo(t2);
		} else {
			throw new IllegalStateException(
					"<T> should implement Comparable or ConcurrentHeadBuffer should be passed a Comparator");
		}
	}
	
}
