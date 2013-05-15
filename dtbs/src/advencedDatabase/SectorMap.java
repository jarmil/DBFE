package advencedDatabase;

import java.io.Serializable;



final class SectorMap implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6063181429878614682L;
	private int size;
	private int capacity;

	private Long[] list;		
	
	public SectorMap() {
		capacity = 10;
		size = 0;
		list = new Long[capacity];

	}

	public void add(Long position) {
		if (size >= capacity) {
			ensureCapacity();
		}
		
		list[size] = position;
		size++;
	}

	public void change(int index, Long position) {
		if (index >= size) {
			return;
		}
		list[index] = position;
	}

	public Long get(int index) {
		if (index >= size) {
			return null;
		}
		return list[index];
	}

	private void ensureCapacity() {
		list = java.util.Arrays.copyOf(list, capacity * 2);
		capacity *= capacity;
	}

	
}