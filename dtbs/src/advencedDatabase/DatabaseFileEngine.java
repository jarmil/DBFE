package advencedDatabase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

import exceptions.DatabaseException;

public class DatabaseFileEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2166529033615542964L;
	private RandomAccessFile dbFile;
	private SectorMap sectorMap;
	private DatabaseHead head;

	public DatabaseFileEngine() throws DatabaseException {

	}

	public void create(String file) throws DatabaseException {
		File tmpFile = new File(file);
		if (tmpFile.exists())
			throw new DatabaseException("Soubor jiz existuje");
		createDefaultDatabaseFile(tmpFile);
	}

	public void load(String file) throws DatabaseException {
		if (!new File(file).exists())
			throw new DatabaseException("Soubor neexistuje vytvorte novy");
		try {
			dbFile = new RandomAccessFile(file, "rw");
		} catch (FileNotFoundException e) {
			throw new DatabaseException(e.getMessage());
		}
		readDatabaseFileHeader();
		readSectorMap();
		checkDatabaseConsistence();
		
	}
	protected void checkDatabaseConsistence() throws DatabaseException{
		int index = 0;
		for(long l: sectorMap.getPointerList()){
			if(!readSectorHead(l).equals(sectorMap.getSectorHead(index++)))
				throw new DatabaseException("Neco nesedi nesouhlasi hlavicky sektoru");
		}
		
	}


	public void close() throws DatabaseException {
		writeDatabaseFileHeader();
		writeSectorMap();
	}

	protected Object readObject(Position position) throws DatabaseException {
		
		Long pos = sectorMap.getDataAbsolutePointer(position);
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

	protected void readObject(Long pos, DatabaseSerialization serialization)
			throws DatabaseException {

		DataHead dataHead = readDataHead(pos);

		serialization.deSerializable(readByteArray(
				pos + dataHead.getDATA_HEAD_SIZE(), dataHead.getLen()));

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

	protected void writeObject(DatabaseSerialization serialization, Long pos)
			throws DatabaseException {

		DataHead dataHead = new DataHead();

		byte[] buffer = serialization.serializable();
		dataHead.setLen(buffer.length);
		dataHead.setStatus(true);
		seek(pos);
		writeDataHead(pos, dataHead);
		writeByteArray(pos + dataHead.DATA_HEAD_SIZE, buffer);
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

	protected void allocate(Long size) throws DatabaseException {
		if (size <= 0)
			return;
		try {
			dbFile.setLength(dbFile.length() + size);
		} catch (IOException e) {
			throw new DatabaseException("Nelze zvetsit soubor " + e);
		}
	}
	
	protected boolean lockSector(int sector) throws DatabaseException {
		
		Long pointer = sectorMap.getSectorPointer(sector);
		if(pointer == null)
			return false;
		SectorHead sectorHead =	readSectorHead(pointer);
		sectorHead.setLock(true);
		writeSectorHead(pointer, sectorHead);
		return true;
	}

	protected void createDefaultDatabaseFile(File file)
			throws DatabaseException {
		try {
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
		sectorMap.add(head.getDataPosition(),sh);
		writeObject(sectorMap, head.getSectorMapPosition());
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

	protected void deserializeObject(byte[] bs,
			DatabaseSerialization serialization) throws DatabaseException {
		serialization.deSerializable(bs);
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

	private SectorHead readSectorHead(Long pos) throws DatabaseException {

		SectorHead sh = new SectorHead();
		sh.deSerializable(readByteArray(pos, sh.getSECTOR_HEAD_SIZE()));
		return sh;

	}

	private void readSectorMap() throws DatabaseException {
		
		sectorMap = new SectorMap();
		readObject(head.getSectorMapPosition(),sectorMap);
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

	protected byte[] serializeObject(DatabaseSerialization serialization)
			throws DatabaseException {
		return serialization.serializable();
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

	private void writeSectorHead(Long pos, SectorHead sh)
			throws DatabaseException {

		writeByteArray(pos, sh.serializable());
	}

	private void writeSectorMap() throws DatabaseException {

		writeObject(sectorMap, head.getSectorMapPosition());
	}

}
