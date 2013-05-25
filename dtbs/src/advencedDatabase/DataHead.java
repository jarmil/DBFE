package advencedDatabase;

import java.io.Serializable;
import java.nio.ByteBuffer;

final class DataHead implements Serializable,DatabaseSerialization {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5706607404227868164L;
	protected byte status;
	protected int len;
	protected final int DATA_HEAD_SIZE = 5;

	public DataHead() {
		this(false, 0);
	}

	public DataHead(boolean status, int len) {
		super();
		this.status = (byte) (status?1:0);
		this.len = len;
	}

	public int getDATA_HEAD_SIZE() {
		return DATA_HEAD_SIZE;
	}

	public boolean isStatus() {
		return (status == 1);
	}

	public void setStatus(boolean status) {
		this.status = (byte) (status?1:0);
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	
	
	public byte[] serializable() {
		ByteBuffer bb = ByteBuffer.allocate(DATA_HEAD_SIZE);
		bb.put(status);
		bb.putInt(len);
		return bb.array();
		
	}
	
	public void deSerializable(byte[] data) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		status = bb.get();
		len = bb.getInt();
		
	}
	
}