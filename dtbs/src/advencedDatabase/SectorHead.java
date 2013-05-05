package advencedDatabase;

import java.io.Serializable;
import java.nio.ByteBuffer;

final class SectorHead implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -235368119475640939L;
	protected int id;	
	protected byte lock;
	protected int maxAllowedObjects;
	protected Long sectorSize;
	protected Long emptySpacePointer;
	protected Long freeSpace;
	private final int SECTOR_HEAD_SIZE = 33;

	public SectorHead() {
		
	}
	
	
	public SectorHead(Long sectorSize, Long emptySpacePointer,
			Long freeSpace, int id,boolean lock,int maxAllowedObjects) {
		super();
		this.sectorSize = sectorSize;
		this.emptySpacePointer = emptySpacePointer;
		this.freeSpace = freeSpace;
		this.id = id;
		this.lock = (byte) (lock?1:0);
		this.maxAllowedObjects = maxAllowedObjects;
	}

	public SectorHead(Long sectorSize, int id) {
		super();
		this.sectorSize = sectorSize;
		this.emptySpacePointer = (long)SECTOR_HEAD_SIZE;
		this.freeSpace = sectorSize - SECTOR_HEAD_SIZE;
		this.id = id;
		maxAllowedObjects = -1;
		lock = 0;
		
	}

	public Long getSectorSize() {
		return sectorSize;
	}

	public void setSectorSize(Long sectorSize) {
		this.sectorSize = sectorSize;
	}

	public Long getEmptySpacePointer() {
		return emptySpacePointer;
	}

	public void setEmptySpacePointer(Long emptySpacePointer) {
		this.emptySpacePointer = emptySpacePointer;
	}

	public Long getFreeSpace() {
		return freeSpace;
	}

	public void setFreeSpace(Long freeSpace) {
		this.freeSpace = freeSpace;
	}

	protected int getHeadSize() {
		return SECTOR_HEAD_SIZE;
	}

	public boolean getLock() {
		return lock == 1;
	}


	public void setLock(boolean lock) {
		this.lock = (byte) (lock?1:0);
	}


	public int getMaxAllowedObjects() {
		return maxAllowedObjects;
	}


	public void setMaxAllowedObjects(int maxAllowedObjects) {
		this.maxAllowedObjects = maxAllowedObjects;
	}



	public int getSECTOR_HEAD_SIZE() {
		return SECTOR_HEAD_SIZE;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] serializable() {
		ByteBuffer bb = ByteBuffer.allocate(SECTOR_HEAD_SIZE);
		bb.putInt(id);
		bb.put((byte) lock );
		bb.putInt(maxAllowedObjects);
		bb.putLong(sectorSize);
		bb.putLong(emptySpacePointer);
		bb.putLong(freeSpace);
		return bb.array();
		
	}
	
	public void deSerializable(byte[] data) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		id = bb.getInt();
		lock = bb.get();
		maxAllowedObjects = bb.getInt();
		sectorSize = bb.getLong();
		emptySpacePointer = bb.getLong();
		freeSpace = bb.getLong();
		
	}
	
}