package advencedDatabase;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Date;

final class DatabaseHead implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7723927835788218042L;
	private int version;
	private Long blockSize;
	private Long sectorMapPos;
	private Long dataPos;
	private String dtbInfo;

	private final int MAX_INFO_LEN = 100;
	protected final int DATABASE_HEAD_SIZE = 130;

	protected DatabaseHead() {
	}

	public DatabaseHead(int version, Long blockSize, Long indexPos,
			Long dataPos, String dtbInfo) {
		this();
		this.version = version;
		this.blockSize = blockSize;
		this.sectorMapPos = indexPos;
		this.dataPos = dataPos;
		this.dtbInfo = dtbInfo;
	}

	public DatabaseHead(int version, Long blockSize) {
		this();
		this.version = version;
		this.blockSize = blockSize;
		this.sectorMapPos = (long)DATABASE_HEAD_SIZE;
		this.dataPos = blockSize;
		this.dtbInfo = new Date().toString();

	}

	
	
	public int getDATABASE_HEAD_SIZE() {
		return DATABASE_HEAD_SIZE;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Long getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(Long blockSize) {
		this.blockSize = blockSize;
	}

	public Long getSectorMapPosition() {
		return sectorMapPos;
	}

	public void setSectorMapPosition(Long indexPos) {
		this.sectorMapPos = indexPos;
	}

	public Long getDataPosition() {
		return dataPos;
	}

	public void setDataPosition(Long dataPos) {
		this.dataPos = dataPos;
	}

	public String getDtbInfo() {
		return dtbInfo;
	}

	public void setDtbInfo(String dtbInfo) {
		if (dtbInfo.length() > MAX_INFO_LEN) {
			this.dtbInfo = dtbInfo.substring(0, MAX_INFO_LEN);
		} else {
			this.dtbInfo = dtbInfo;
		}
	}
	
	public byte[] serializable() {
		ByteBuffer bb = ByteBuffer.allocate(getDATABASE_HEAD_SIZE());
		bb.putInt(version);
		bb.putLong(blockSize);
		bb.putLong(sectorMapPos);
		bb.putLong(dataPos);
		bb.put(dtbInfo.getBytes());
		return bb.array();
		
	}
	
	public void deSerializable(byte[] data) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		version = bb.getInt();
		blockSize = bb.getLong();
		sectorMapPos = bb.getLong();
		dataPos = bb.getLong();
		dtbInfo = new String(bb.get(new byte[MAX_INFO_LEN], 0, MAX_INFO_LEN).array());
		
	}
	
	
	
}