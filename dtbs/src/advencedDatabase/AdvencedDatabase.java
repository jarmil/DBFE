package advencedDatabase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import exceptions.DatabaseException;

public class AdvencedDatabase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2166529033615542964L;
	private RandomAccessFile dbFile;
	private String dbFileName;
	private SectorMap sectorMap;
	private DatabaseHead head;

	public AdvencedDatabase(String file) throws DatabaseException {
		dbFileName = file;
	}

	private void allocate(Long size) throws DatabaseException {
		if (size <= 0)
			return;
		try {
			dbFile.setLength(dbFile.length() + size);
		} catch (IOException e) {
			throw new DatabaseException("Nelze zvetsit soubor " + e);
		}
	}

	protected void createDefaultDatabaseFile() throws DatabaseException {
		try {
			File file = new File(dbFileName);
			file.createNewFile();
			dbFile = new RandomAccessFile(file, "rw");
		} catch (IOException e) {
			throw new DatabaseException("Nelze vytvorit soubor " + e);
		}
		head = new DatabaseHead(1000, 5000L);
		sectorMap = new SectorMap();
		allocate(head.getBlockSize() * 2);
		writeDatabaseFileHeader();
		SectorHead sh = new SectorHead(head.getBlockSize(), 0);
		writeSectorHead(head.getDataPosition(), sh);
		sectorMap.add(head.getDataPosition());
		writeObject(sectorMap, head.getSectorMapPosition());
	}

	private boolean deleteObject(Long pos) {
		try {
			seek(pos);
			dbFile.writeByte(0);
			return true;
		} catch (IOException | DatabaseException e) {
			return false;
		}
	}

	protected Object deserializeObject(byte[] b) throws DatabaseException {
		try {
			ByteArrayInputStream bis;
			ObjectInputStream ois;
			bis = new ByteArrayInputStream(b);
			ois = new ObjectInputStream(bis);
			Object object = ois.readObject();
			return object;
		} catch (Exception e) {
			throw new DatabaseException("Chyba pri cteni ze zadaneho umisteni "
					+ e);
		}
	}

	protected byte[] readByteArray(Long pos, int len) throws DatabaseException {
		try {
			seek(pos);
			byte[] b = new byte[len];
			dbFile.read(b);
			return b;
		} catch (IOException e) {
			throw new DatabaseException("Nelze zapsat celociselnou hodnotu "
					+ e);
		}
	}
	   	
	private void readDatabaseFileHeader() throws DatabaseException {
		head = new DatabaseHead();
		head.deSerializable(readByteArray(0L, head.getDATABASE_HEAD_SIZE()));
	}

	private DataHead readDataHead(Long pos) throws DatabaseException {
		DataHead dh = new DataHead();
		dh.deSerializable(readByteArray(pos, dh.getDATA_HEAD_SIZE()));
		return dh;

	}

	protected Object readObject(Long pos) throws DatabaseException {

		DataHead dataHead = readDataHead(pos);
		try {
			byte[] bs = readByteArray(pos + dataHead.getDATA_HEAD_SIZE(),
					dataHead.getLen());
			return deserializeObject(bs);
		} catch (Exception e) {
			throw new DatabaseException("Chyba pri cteni ze zadaneho umisteni "
					+ e);
		}
	}

	private SectorHead readSectorHead(Long pos) throws DatabaseException {

		SectorHead sh = new SectorHead();
		sh.deSerializable(readByteArray(pos, sh.getSECTOR_HEAD_SIZE()));
		return sh;

	}

	private SectorMap readSectorMap() throws DatabaseException {

		Object object = readObject(head.getSectorMapPosition());

		if (object instanceof SectorMap) {
			sectorMap = (SectorMap) object;
		} else {
			throw new DatabaseException("Nelze nacist tabulku sektoru "
					+ object.toString());
		}
		return null;
	}

	private void resizeTo(Long fullSize) throws DatabaseException {
		try {
			dbFile.setLength(fullSize);
		} catch (IOException e) {
			throw new DatabaseException("Nelze zvetsit soubor " + e);
		}
	}

	protected void seek(long pointer) throws DatabaseException {
		try {
			if (pointer < 0) {
				dbFile.seek(0);
			}
			if (pointer > dbFile.length()) {
				dbFile.seek(dbFile.length());
			} else {
				dbFile.seek(pointer);
			}
		} catch (IOException e) {
			throw new DatabaseException(
					"Problem pri presunu pointeru na pozici " + e);
		}
	}

	protected byte[] serializeObject(Object o) throws DatabaseException {
		try {
			ByteArrayOutputStream bos;
			ObjectOutputStream oos;
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(o);
			oos.flush();
			return bos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException("nelze serializovat objekt " + e);
		}
	}

	protected void writeByteArray(Long pos, byte[] b) throws DatabaseException {
		try {
			seek(pos);
			dbFile.write(b);
		} catch (IOException e) {
			throw new DatabaseException("Nelze zapsat celociselnou hodnotu "
					+ e);
		}
	}

	private void writeDatabaseFileHeader() throws DatabaseException {
		writeByteArray(0L, head.serializable());
	}

	private void writeDataHead(Long pos, DataHead head)
			throws DatabaseException {

		writeByteArray(pos, head.serializable());

	}

	protected void writeObject(Object o, Long pos) throws DatabaseException {

		DataHead dataHead = new DataHead();

		byte[] buffer = serializeObject(o);
		dataHead.setLen(buffer.length);
		dataHead.setStatus(true);
		seek(pos);
		writeDataHead(pos, dataHead);
		writeByteArray(pos + dataHead.DATA_HEAD_SIZE, buffer);

	}

	private void writeSectorHead(Long pos, SectorHead sh)
			throws DatabaseException {
		
		writeByteArray(pos, sh.serializable());
	}

	/*
	 * 
	 * 
	 * low level methods
	 */
	/*
	 * protected byte[] serializeObject(Object o) throws DatabaseException { try
	 * { ByteArrayOutputStream bos; ObjectOutputStream oos; bos = new
	 * ByteArrayOutputStream(); oos = new ObjectOutputStream(bos);
	 * oos.writeObject(o); oos.flush(); return bos.toByteArray(); } catch
	 * (IOException e) { // TODO Auto-generated catch block throw new
	 * DatabaseException("nelze serializovat objekt " + e); } }
	 * 
	 * protected Object deserializeObject(byte[] b) throws DatabaseException {
	 * try { ByteArrayInputStream bis; ObjectInputStream ois; bis = new
	 * ByteArrayInputStream(b); ois = new ObjectInputStream(bis); Object object
	 * = ois.readObject(); return object; } catch (Exception e) { throw new
	 * DatabaseException("Chyba pri cteni ze zadaneho umisteni " + e); } }
	 * 
	 * protected Object readObject(Long pos) throws DatabaseException {
	 * 
	 * DataHead dataHead = readDataHead(pos); try { byte[] bs =
	 * readByteArray(pos + dataHead.getDATA_HEAD_SIZE(), dataHead.getLen());
	 * return deserializeObject(bs); } catch (Exception e) { throw new
	 * DatabaseException("Chyba pri cteni ze zadaneho umisteni " + e); } }
	 * 
	 * protected void writeObject(Object o, Long pos) throws DatabaseException {
	 * 
	 * DataHead dataHead = new DataHead();
	 * 
	 * byte[] buffer = serializeObject(o); dataHead.setLen(buffer.length);
	 * dataHead.setStatus(true); seek(pos); writeDataHead(pos, dataHead);
	 * writeByteArray(buffer);
	 * 
	 * }
	 * 
	 * protected String readString(Long pos) throws DatabaseException {
	 * 
	 * seek(pos); try { return dbFile.readUTF(); } catch (IOException e) { throw
	 * new DatabaseException("Nelze nacist retezec " + e); } }
	 * 
	 * protected String readString() throws DatabaseException { return
	 * readString(getPointer()); }
	 * 
	 * protected Long writeString(Long pos, String s) throws DatabaseException {
	 * try { seek(pos); dbFile.writeUTF(s); return getPointer(); } catch
	 * (IOException e) { throw new DatabaseException("Nelze zapsat retezec " +
	 * e); } }
	 * 
	 * protected Long writeString(String s) throws DatabaseException { return
	 * writeString(getPointer(), s); }
	 * 
	 * protected int readInt(Long pos) throws DatabaseException {
	 * 
	 * seek(pos); try { return dbFile.readInt(); } catch (IOException e) { throw
	 * new DatabaseException("Nelze nacist celociselnou hodnotu " + e); } }
	 * 
	 * protected int readInt() throws DatabaseException { return
	 * readInt(getPointer()); }
	 * 
	 * protected Long writeInt(Long pos, int i) throws DatabaseException { try {
	 * seek(pos); dbFile.writeInt(i); return getPointer(); } catch (IOException
	 * e) { throw new DatabaseException("Nelze zapsat celociselnou hodnotu " +
	 * e); } }
	 * 
	 * protected Long writeInt(int i) throws DatabaseException { return
	 * writeInt(getPointer(), i); }
	 * 
	 * protected boolean readBoolean(Long pos) throws DatabaseException {
	 * 
	 * seek(pos); try { seek(pos); return dbFile.readBoolean(); } catch
	 * (IOException e) { throw new
	 * DatabaseException("Nelze nacist logickou hodnotu " + e); } }
	 * 
	 * protected boolean readBoolean() throws DatabaseException {
	 * 
	 * return readBoolean(getPointer()); }
	 * 
	 * private void resizeTo(Long fullSize) throws DatabaseException { try {
	 * dbFile.setLength(fullSize); } catch (IOException e) { throw new
	 * DatabaseException("Nelze zvetsit soubor " + e); } }
	 * 
	 * private void allocate(Long size) throws DatabaseException { if (size < 0)
	 * return; try { dbFile.setLength(getLength() + size); } catch (IOException
	 * e) { throw new DatabaseException("Nelze zvetsit soubor " + e); } }
	 * 
	 * protected Long writeBoolean(Long pos, boolean bool) throws
	 * DatabaseException { try { seek(pos); dbFile.writeBoolean(bool); return
	 * getPointer(); } catch (IOException e) { throw new
	 * DatabaseException("Nelze zapsat celociselnou hodnotu " + e); } }
	 * 
	 * protected Long writeBoolean(boolean bool) throws DatabaseException {
	 * return writeBoolean(getPointer(), bool); }
	 * 
	 * protected Long writeByteArray(byte[] b) throws DatabaseException { return
	 * writeByteArray(getPointer(), b); }
	 * 
	 * protected Long writeByteArray(Long pos, byte[] b) throws
	 * DatabaseException { try { seek(pos); dbFile.write(b); return
	 * getPointer(); } catch (IOException e) { throw new
	 * DatabaseException("Nelze zapsat celociselnou hodnotu " + e); } }
	 * 
	 * protected byte[] readByteArray(Long pos, int len) throws
	 * DatabaseException { try { seek(pos); byte[] b = new byte[len];
	 * dbFile.read(b); return b; } catch (IOException e) { throw new
	 * DatabaseException("Nelze zapsat celociselnou hodnotu " + e); } }
	 * 
	 * protected Long readLong(Long pos) throws DatabaseException { try {
	 * seek(pos); Long l = dbFile.readLong(); return l; } catch (IOException e)
	 * { throw new DatabaseException("Nelze nacist dlouhe cislo " + e); } }
	 * 
	 * protected long readLong() throws DatabaseException { return
	 * readLong(getPointer()); }
	 * 
	 * protected Long writeLong(Long pos, long l) throws DatabaseException { try
	 * { seek(pos); dbFile.writeLong(l); return getPointer(); } catch
	 * (IOException e) { throw new
	 * DatabaseException("Nelze zapsat dlouhe cislo " + e); } }
	 * 
	 * protected Long writeLong(long l) throws DatabaseException { return
	 * writeLong(getPointer(), l); }
	 */
	/*
	 * 
	 * 
	 * low level methods
	 */

	private void writeSectorMap() throws DatabaseException {

		writeObject(sectorMap, head.getSectorMapPosition());
	}

	/*private Long findEmptyPosition(){
		for (int i = sectorMap.; i < array.length; i++) {
			
		}
	}
	
	public Position writeObject(Object o){
		Long positionLong = 
	}*/
	
}





