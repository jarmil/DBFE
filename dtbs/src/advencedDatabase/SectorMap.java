package advencedDatabase;

import java.io.Serializable;
import java.nio.ByteBuffer;



final class SectorMap implements DatabaseSerialization {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6063181429878614682L;
	private int size;
	private int capacity;
	
	private Pair[] sectors;
	
	public SectorMap() {
		capacity = 10;
		size = 0;
		sectors = new Pair[capacity];

	}
	
	public void add(Long position,SectorHead head) {
		if (size >= capacity) {
			ensureCapacity();
		}
		sectors[size] = new Pair(position, head);
		size++;
	}

	public void change(int id, Long position) {
		if (id >= size) {
			return;
		}
		sectors[id].setPosition(position);
	}
	
	public SectorHead getSectorHead(int id){
		if (id >= size || id < 0) {
			return null;
		}
		return sectors[id].getHead();
	}

	public Long getSectorPointer(int id) {
		if (id >= size || id < 0) {
			return null;
		}
		return sectors[id].getPosition();
	}
	
	public Long getSectorPointer(Position pos) {
		
		return getSectorPointer(pos.sector);
	}
	
	public Long getDataAbsolutePointer(Position pos) {
		Long pointer = getSectorPointer(pos.sector);
		if(pointer == null)
			return null;
		return pointer + pos.position;
			
	}

	private void ensureCapacity() {
		capacity *= 2;
		sectors = java.util.Arrays.copyOf(sectors, capacity);		
	}	
	
	public byte[] serializable() {
		ByteBuffer bb = ByteBuffer.allocate(8 + (size * sectors[0].getHead().getSECTOR_HEAD_SIZE()));
		bb.putInt(size);
		bb.putInt(capacity);

		for (Pair pair: sectors) {
			bb.putLong(pair.position);
			bb.put(pair.head.serializable());
		}
		return bb.array();		
	}
	
	public void deSerializable(byte[] data) {
		int headSize= new SectorHead().getSECTOR_HEAD_SIZE();
		SectorHead tmpSectorHead = new SectorHead();
		Long tmpLong = null;
		
		
		ByteBuffer bb = ByteBuffer.wrap(data);
		size = bb.getInt();
		capacity = bb.getInt();
		
		for (int i = 0; i < size; i++) {	
			tmpLong = bb.getLong();
			tmpSectorHead.deSerializable((bb.get(new byte[headSize])).array());
			sectors[i] = new Pair(tmpLong,tmpSectorHead);
		}		
	}

	public int getSize(){
		return size - 1;
	}
	
	public Long[] getPointerList(){
		Long[] tmp = new Long[size];
		for (int i = 0; i < size; i++) {
			tmp[i] = sectors[i].getPosition();
		}
		return tmp;
		}
		
}
















