package advencedDatabase;

import java.nio.ByteBuffer;

final class SectorHead implements DatabaseSerialization {
	/**
	 * 
	 */
	private static final long serialVersionUID = -235368119475640939L;
	protected int id;
	protected byte lock;
	protected Long sectorSize;
	protected Long emptySpacePointer;
	protected Long freeSpace;

	private final int SECTOR_HEAD_SIZE = 29;

	public SectorHead() {

	}

	public SectorHead(Long sectorSize, Long emptySpacePointer, Long freeSpace,
			int id, boolean lock) {
		super();
		this.sectorSize = sectorSize;
		this.emptySpacePointer = emptySpacePointer;
		this.freeSpace = freeSpace;
		this.id = id;
		this.lock = (byte) (lock ? 1 : 0);
	}

	public SectorHead(Long sectorSize, int id) {
		super();
		this.sectorSize = sectorSize;
		this.emptySpacePointer = (long) SECTOR_HEAD_SIZE;
		this.freeSpace = sectorSize - SECTOR_HEAD_SIZE;
		this.id = id;
		lock = 0;

	}

	public Long getSectorSize() {
		return sectorSize;
	}

	protected void setSectorSize(Long sectorSize) {
		this.sectorSize = sectorSize;
	}

	public Long getEmptySpacePointer() {
		return emptySpacePointer;
	}

	protected void setEmptySpacePointer(Long emptySpacePointer) {
		this.emptySpacePointer = emptySpacePointer;
	}

	public Long getFreeSpace() {
		return freeSpace;
	}

	protected void setFreeSpace(Long freeSpace) {
		this.freeSpace = freeSpace;
	}

	protected int getHeadSize() {
		return SECTOR_HEAD_SIZE;
	}

	public boolean getLock() {
		return lock == 1;
	}

	protected void setLock(boolean lock) {
		this.lock = (byte) (lock ? 1 : 0);
	}

	public int getSECTOR_HEAD_SIZE() {
		return SECTOR_HEAD_SIZE;
	}

	public int getId() {
		return id;
	}

	protected void setId(int id) {
		this.id = id;
	}

	public byte[] serializable() {
		ByteBuffer bb = ByteBuffer.allocate(SECTOR_HEAD_SIZE);
		bb.putInt(id);
		bb.put(lock);
		bb.putLong(sectorSize);
		bb.putLong(emptySpacePointer);
		bb.putLong(freeSpace);
		return bb.array();
	}

	public void deSerializable(byte[] data) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		id = bb.getInt();
		lock = bb.get();
		sectorSize = bb.getLong();
		emptySpacePointer = bb.getLong();
		freeSpace = bb.getLong();
	}

	public boolean check(Long size) {
		if (lock == (byte) 1)
			return false;
		if (freeSpace >= size || sectorSize == -1) {
			return true;
		} else {
			return false;
		}
	}

	protected boolean checkAndIngnoreLock(Long size) {
		if (freeSpace >= size || sectorSize == -1) {
			return true;
		} else {
			return false;
		}
	}

	public SectorHead updateHead(Long objectSize) {
		if (checkAndIngnoreLock(objectSize)) {
			emptySpacePointer += objectSize;
			if (!(sectorSize == -1)) {
				freeSpace -= objectSize;
			}
		} else {
			return null;
		}
		return this;
	}

	public boolean equals(SectorHead head) {
		if ((id == head.id) && (lock == head.lock)
				&& (sectorSize == head.sectorSize)
				&& (emptySpacePointer == head.emptySpacePointer)
				&& (freeSpace == head.freeSpace)) {
			return true;
		} else {
			return false;
		}
	}

}