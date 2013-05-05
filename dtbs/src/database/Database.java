package database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import exceptions.DatabaseException;

public abstract class Database {
	protected RandomAccessFile dbFile;
	protected String dbFileName;
	protected String mode;

	ByteArrayOutputStream bos;
	ObjectOutputStream oos;
	ByteArrayInputStream bis;
	ObjectInputStream ois;

	public Database(String file) throws DatabaseException {
		dbFileName = file;
		mode = "rw";
	}

	public void start(String mode) throws DatabaseException {
		this.mode = mode;
		File tmpFile = new File(dbFileName);

		if (!existsFile(tmpFile)) {
			throw new DatabaseException(
					"Soubor neexistuje, nejprve jej vytvorte");
		} else {
			try {
				dbFile = new RandomAccessFile(tmpFile, mode);
			} catch (IOException e) {
				throw new DatabaseException("Nelze nacist soubor " + e);
			}
		}
		readDatabaseFileHeader();
	}

	public void stop() throws DatabaseException {
		try {
			this.dbFile.close();
		} catch (IOException e) {
			throw new DatabaseException("Nelze skoncit beh database " + e);
		}
	}

	public void create() throws DatabaseException {
		// createDefaultDatabaseFile(new File(dbFileName));
	}

	protected void createDefaultDatabaseFile(File file)
			throws DatabaseException {
		if (mode == "r") {
			throw new DatabaseException(
					"Soubor otevren jen pro cteni,nelze vytvorit novy soubor databaze");
		}

		try {
			file.createNewFile();
			dbFile = new RandomAccessFile(file, mode);
		} catch (IOException e) {
			throw new DatabaseException("Nelze vytvorit soubor " + e);
		}
		writeDatabaseFileHeader();

	}

	protected boolean existsFile(File dbFile) {
		return dbFile.exists();
	}

	protected void clearDatabase() throws DatabaseException {
		try {
			dbFile.setLength(0);
		} catch (IOException e) {
			throw new DatabaseException("Nelze vycistit soubor " + e);
		}
		writeDatabaseFileHeader();

	}

	protected Long displaceToEnd(Long pos) throws DatabaseException {

		DataHead head = readDataHead(pos);
		if (!head.state) {
			return -1L;
		} else {
			Long newPos = getSize();
			Object o = readObject(head.len);
			writeDataHead(newPos, head);
			writeObject(o);
			writeBoolean(pos, false);
			return newPos;
		}
	}

	private boolean deleteObject(Long pos) {
		try {
			writeBoolean(pos, false);
			return true;
		} catch (DatabaseException e) {
			return false;
		}
	}

	protected byte[] serializeObject(Object o) throws DatabaseException {
		try {
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

	protected Object deserializeObject(byte[] b) throws DatabaseException {
		try {
			bis = new ByteArrayInputStream(b);
			ois = new ObjectInputStream(bis);
			Object object = ois.readObject();
			return object;
		} catch (Exception e) {
			throw new DatabaseException("Chyba pri cteni ze zadaneho umisteni "
					+ e);
		}
	}

	protected String readString(Long pos) throws DatabaseException {

		seek(pos);
		try {
			return dbFile.readUTF();
		} catch (IOException e) {
			throw new DatabaseException("Nelze nacist retezec " + e);
		}
	}

	protected String readString() throws DatabaseException {
		return readString(getPointer());
	}

	protected Long writeString(Long pos, String s) throws DatabaseException {
		try {
			seek(pos);
			dbFile.writeUTF(s);
			return getPointer();
		} catch (IOException e) {
			throw new DatabaseException("Nelze zapsat retezec " + e);
		}
	}

	protected Long writeString(String s) throws DatabaseException {
		return writeString(getPointer(), s);
	}

	protected int readInt(Long pos) throws DatabaseException {

		seek(pos);
		try {
			return dbFile.readInt();
		} catch (IOException e) {
			throw new DatabaseException("Nelze nacist celociselnou hodnotu "
					+ e);
		}
	}

	protected int readInt() throws DatabaseException {
		return readInt(getPointer());
	}

	protected Long writeInt(Long pos, int i) throws DatabaseException {
		try {
			seek(pos);
			dbFile.writeInt(i);
			return getPointer();
		} catch (IOException e) {
			throw new DatabaseException("Nelze zapsat celociselnou hodnotu "
					+ e);
		}
	}

	protected Long writeInt(int i) throws DatabaseException {
		return writeInt(getPointer(), i);
	}

	protected boolean readBoolean(Long pos) throws DatabaseException {

		seek(pos);
		try {
			seek(pos);
			return dbFile.readBoolean();
		} catch (IOException e) {
			throw new DatabaseException("Nelze nacist logickou hodnotu " + e);
		}
	}

	protected boolean readBoolean() throws DatabaseException {

		return readBoolean(getPointer());
	}

	protected void allocate(Long size) throws DatabaseException {
		if (size < 0)
			return;
		try {
			dbFile.setLength(getSize() + size);
		} catch (IOException e) {
			throw new DatabaseException("Nelze zvetsit soubor " + e);
		}
	}

	protected void resizeTo(Long fullSize) throws DatabaseException {
		try {
			dbFile.setLength(fullSize);
		} catch (IOException e) {
			throw new DatabaseException("Nelze zvetsit soubor " + e);
		}
	}

	protected Long writeBoolean(Long pos, boolean bool)
			throws DatabaseException {
		try {
			seek(pos);
			dbFile.writeBoolean(bool);
			return getPointer();
		} catch (IOException e) {
			throw new DatabaseException("Nelze zapsat celociselnou hodnotu "
					+ e);
		}
	}

	protected Long writeBoolean(boolean bool) throws DatabaseException {
		return writeBoolean(getPointer(), bool);
	}

	protected Long writeByteArray(byte[] b) throws DatabaseException {
		return writeByteArray(getPointer(), b);
	}

	protected Long writeByteArray(Long pos, byte[] b) throws DatabaseException {
		try {
			seek(pos);
			dbFile.write(b);
			return getPointer();
		} catch (IOException e) {
			throw new DatabaseException("Nelze zapsat celociselnou hodnotu "
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

	protected Long readLong(Long pos) throws DatabaseException {
		try {
			seek(pos);
			Long l = dbFile.readLong();
			return l;
		} catch (IOException e) {
			throw new DatabaseException("Nelze nacist dlouhe cislo " + e);
		}
	}

	protected long readLong() throws DatabaseException {
		return readLong(getPointer());
	}

	protected Long writeLong(Long pos, long l) throws DatabaseException {
		try {
			seek(pos);
			dbFile.writeLong(l);
			return getPointer();
		} catch (IOException e) {
			throw new DatabaseException("Nelze zapsat dlouhe cislo " + e);
		}
	}

	protected Long writeLong(long l) throws DatabaseException {
		return writeLong(getPointer(), l);
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

	protected Long getPointer() throws DatabaseException {
		try {
			return dbFile.getFilePointer();
		} catch (IOException e) {
			throw new DatabaseException("nelze vatit pointer " + e);
		}
	}

	public Long getSize() throws DatabaseException {
		try {
			return dbFile.length();
		} catch (IOException e) {
			throw new DatabaseException(
					"Nelze zjistit velikost souboru database " + e);
		}
	}

	protected DataHead readDataHead(Long pos) throws DatabaseException {
		DataHead dh = new DataHead();
		seek(pos);
		dh.setStatus(readBoolean());
		dh.setLen(readInt());
		return dh;

	}

	protected void writeDataHead(Long pos, DataHead head)
			throws DatabaseException {

		seek(pos);
		writeBoolean(head.state);
		writeInt(head.len);

	}

	protected boolean checkSpace(Long startPos, int objectLen)
			throws DatabaseException {
		DataHead dh;
		Long tmpPos;
		boolean result = false;

		if (startPos == getSize()) {
			result = true;
		} else {
			try {
				dh = readDataHead(startPos);
				if ((dh.len == 0) || dh.getLen() >= objectLen) {
					result = true;
				} else {
					tmpPos = startPos + dh.headSize + dh.getLen();

					if (tmpPos == getSize()) {
						result = true;
					} else {
						seek(tmpPos);
						dh = readDataHead(tmpPos);

						result = !dh.state;

					}
				}

			} catch (DatabaseException e) {
				seek(startPos);
				return false;
			}

		}
		seek(startPos);
		return result;
	}

	protected Object readObject(Long pos, int len) throws DatabaseException {

		try {
			byte[] bs = readByteArray(pos, len);
			return deserializeObject(bs);
		} catch (Exception e) {
			throw new DatabaseException("Chyba pri cteni ze zadaneho umisteni "
					+ e);
		}
	}

	protected Object readObject(int len) throws DatabaseException {

		return readObject(getPointer(), len);
	}

	protected int writeObject(Object o) throws DatabaseException {
		return writeObject(o, getPointer());
	}

	protected int writeObject(Object o, Long pos) throws DatabaseException {
		if (mode == "r")
			throw new DatabaseException("Databaze otevrena jen pro cteni");

		try {

			byte[] buffer = serializeObject(o);
			seek(pos);
			dbFile.write(buffer);
			return buffer.length;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException("Chyba pri zapisu na zadane umisteni "
					+ e);
		}

	}

	protected boolean addObject(Object o, Long pos) {
		byte[] obj;
		try {
			obj = serializeObject(o);
			if (((long)pos) == getSize()) {

				writeDataHead(pos, new DataHead(true, obj.length));
				writeByteArray(obj);
				return true;

			} else {
				if (!checkSpace(pos, obj.length)) {
					return false;
				} else {
					writeDataHead(pos, new DataHead(true, obj.length));
					writeByteArray(obj);
					return true;
				}
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
			return false;
		}
	}

	protected Object getObject(Long pos) {
		try {
			DataHead head = readDataHead(pos);
			return readObject(head.len);

		} catch (DatabaseException e) {
			return null;
		}
	}

	protected boolean removeObject(Long pos) {
		DataHead head;
		try {
			head = readDataHead(pos);
		} catch (DatabaseException e) {
			return false;
		}
		if (head.state) {
			return deleteObject(pos);
		}
		return true;
	}

	protected boolean changeObject(Long pos, Object newObject) {

		try {
			DataHead head = readDataHead(pos);
			byte[] obj = serializeObject(newObject);

			if (obj.length <= head.len) {
				writeByteArray(obj);
				return true;
			} else {
				return false;
			}
		} catch (DatabaseException e) {
			return false;
		}

	}

	protected void addObject(Object o) throws DatabaseException {
		addObject(o, getSize());
	}

	public abstract void resetPointer() throws DatabaseException;

	protected abstract void readDatabaseFileHeader() throws DatabaseException;

	protected abstract void writeDatabaseFileHeader() throws DatabaseException;

	public class DataHead {
		protected boolean state;
		protected int len;
		protected int headSize;

		public DataHead() {
			this(false, 0);
		}

		public DataHead(boolean status, int len) {
			super();
			this.state = status;
			this.len = len;
			headSize = 9;
		}

		public boolean isStatus() {
			return state;
		}

		public void setStatus(boolean status) {
			this.state = status;
		}

		public int getLen() {
			return len;
		}

		public void setLen(int len) {
			this.len = len;
		}

	}

}
