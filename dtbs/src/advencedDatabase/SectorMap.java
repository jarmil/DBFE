package advencedDatabase;

import java.io.Serializable;
import java.nio.ByteBuffer;



final class SectorMap implements Serializable,DatabaseSerialization {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6063181429878614682L;
	private int size;
	private int capacity;

	private Long[] list;		
	
	private transient SectorHead[] heads; 
	
	public SectorMap() {
		capacity = 10;
		size = 0;
		list = new Long[capacity];
		heads = new SectorHead[capacity];

	}
	
	public void adhead(SectorHead head){
		heads[head.getId()] = head;
	}

	public void add(Long position,SectorHead head) {
		if (size >= capacity) {
			ensureCapacity();
		}
		System.out.print("dyhju");
		list[size] = position;
		heads[size] = head; 
		size++;
	}

	public void change(int id, Long position) {
		if (id >= size) {
			return;
		}
		list[id] = position;
	}

	public Long getSectorPointer(int id) {
		if (id >= size || id < 0) {
			return null;
		}
		return list[id];
	}
	
	public Long getSectorPointer(Position pos) {
		
		return getSectorPointer(pos.sector);
	}
	
	public Long getAbsolutePointer(Position pos) {
		Long pointer = getSectorPointer(pos.sector);
		if(pointer == null)
			return null;
		return pointer + pos.position;
			
	}

	private void ensureCapacity() {
		capacity *= 2;
		list = java.util.Arrays.copyOf(list, capacity);		
		heads = java.util.Arrays.copyOf(heads, capacity);
	}
	
	public void addReadSectorHead(SectorHead head) {
		if(heads == null)
			heads = new SectorHead[capacity];
		heads[head.getId()] = head;
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

	public int getSize(){
		return size;
	}
}