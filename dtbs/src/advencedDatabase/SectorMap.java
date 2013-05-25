package advencedDatabase;

import java.io.Serializable;
import java.nio.ByteBuffer;



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

	
	public byte[] serializable() {
		ByteBuffer bb = ByteBuffer.allocate(12 + (list.length * 8));
		bb.putInt(size);
		bb.putInt(capacity);
		bb.putInt(list.length);
		
		for (int i = 0; i < list.length; i++) {
			bb.putLong(list[i]);
		}
		return bb.array();		
	}
	
	public void deSerializable(byte[] data) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		size = bb.getInt();
		capacity = bb.getInt();
		int len = bb.getInt();
		
		list = new Long[len];
		for (int i = 0; i < len; i++) {
			list[i] = bb.getLong();
		}
	}
	
}