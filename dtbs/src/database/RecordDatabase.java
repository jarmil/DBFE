package database;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import exceptions.DatabaseException;

public class RecordDatabase extends Database {

	private DatabaseHead head;
	private Map<String, Long> indexMap;
	private int size;
	private Long pointer;

	private final String TYPE = "SIMLOBJD";
	private final int VERSION = 1000;
	

	public RecordDatabase(String file) throws DatabaseException {
		super(file);
	}

	@Override
	public void resetPointer() throws DatabaseException {
		seek(head.getDataPointer());

	}
	
	@Override
	public void create() throws DatabaseException {
		head = new DatabaseHead(TYPE, VERSION, "Created by DTBS, Autor: KUBA, " + new Date().toString());
		indexMap = new HashMap<String, Long>();		
		createDefaultDatabaseFile(new File(super.dbFileName));
		resizeTo(head.getDEFAULT_DATA_START_POS());
		 writeIndexMap( head.getDEFAULT_INDEX_START_POS());
		
		head = null;
		indexMap = null;
	}
	
	private void writeIndexMap(Long pos) throws DatabaseException{
		DataHead dh = new DataHead();
		int size = writeObject(indexMap, pos + dh.headSize);
		writeDataHead(pos, new DataHead(true, size));
	}
	
	@SuppressWarnings("unchecked")
	private void readIndexMap(Long pos) throws DatabaseException{
		DataHead dh = readDataHead(pos);
		indexMap =(HashMap<String, Long>) readObject(pos + dh.headSize, dh.getLen());
	}
	

	@Override
	public void start(String mode) throws DatabaseException {
		// TODO Auto-generated method stub
		super.start(mode);
		readIndexMap(head.getIndexMapPointer());
		size = indexMap.size();
		if(size == 0){
			pointer = head.getDEFAULT_DATA_START_POS();
		}else{
			pointer = getSize();
		}
		
	}
	
	@Override
	public void stop() throws DatabaseException{
		try {
			writeIndexMap(head.getIndexMapPointer());
			this.dbFile.close();
		} catch (IOException e) {
			throw new DatabaseException("Nelze skoncit beh database " + e);
		}
	}
	
	public Object get(String key) {
		Long tmpLong = indexMap.get(key);
		if (tmpLong == null){
			return null;
		}else{
			return getObject(tmpLong);
		}
	}
	

	public boolean insert(String key,Object o) throws DatabaseException {
		if (indexMap.containsKey(key)){
			return false;
		}
		addObject(o, pointer);
		indexMap.put(key, pointer);
		pointer = getPointer();
		return true;
	}
	
	public boolean remove(String key) {
		Long tmp = indexMap.get(key);
		if(tmp == null){
			return false;
		}else{
			removeObject(tmp);
			indexMap.remove(key);
			return true;
		}		
	}
	
	public boolean change(String key, Object newObject) throws DatabaseException{
		if (indexMap.containsKey(key)){
			return false;
		}
		if( !changeObject(indexMap.get(key), newObject)){
			remove(key);
			return insert(key, newObject);
		}else{
			return true;
		}
	}
	
	@Override
	protected void readDatabaseFileHeader() throws DatabaseException {
		head = new DatabaseHead();

		head.setType(readString(head.getTYPE_POINTER_POS()));
		head.setVersion(readInt(head.getVERSION_POINTER_POS()));
		head.setIndexMapPointer(readLong(head.getINDEX_POINTER_POS()));
		head.setDataPointer(readLong(head.getDATA_POINTER_POS()));		
		head.setInfo(readString(head.getINFO_POINTER_POS()));

	}

	@Override
	protected void writeDatabaseFileHeader() throws DatabaseException {
		if (head == null) {
			head = new DatabaseHead(TYPE, VERSION, "Ahoj");
		}

		writeString(head.getTYPE_POINTER_POS(), head.getType());
		writeInt(head.getVERSION_POINTER_POS(), head.getVersion());
		writeLong(head.getINDEX_POINTER_POS(), head.getIndexMapPointer());
		writeLong(head.getDATA_POINTER_POS(), head.getDataPointer());
		writeString(head.getINFO_POINTER_POS(), head.getInfo());


	}
	
	public class SortedList{
		
	}
	
	public class DatabaseHead {

		protected Long dataPointer;
		protected Long indexPointer;
		protected String info;
		private String type;
		private int version;

		private final int MAX_INFO_LEN = 98;
		private final int MAX_TYPE_LEN = 8;
		private final Long TYPE_POINTER_POS = 0L;
		private final Long VERSION_POINTER_POS =(long) MAX_TYPE_LEN +2;
		private final Long INDEX_POINTER_POS = VERSION_POINTER_POS + 4;
		private final Long DATA_POINTER_POS = INDEX_POINTER_POS + 8;
		private final Long INFO_POINTER_POS = DATA_POINTER_POS + 8;
		private final Long INITIAL_SPACE_BLOCK = 2048L;
		private final Long DEFAULT_INDEX_START_POS =(long) MAX_INFO_LEN + MAX_TYPE_LEN + INFO_POINTER_POS + MAX_INFO_LEN +2;
		private final Long DEFAULT_DATA_START_POS =DEFAULT_INDEX_START_POS + INITIAL_SPACE_BLOCK;
		
		public Long getDEFAULT_INDEX_START_POS() {
			return DEFAULT_INDEX_START_POS;
		}

		public Long getDEFAULT_DATA_START_POS() {
			return DEFAULT_DATA_START_POS;
		}

		
		

		public DatabaseHead() {
		}

		public DatabaseHead(String type, int version, String info,
				Long dataPointer, Long indexMapPointer) {
			this.dataPointer = dataPointer;
			this.indexPointer = indexMapPointer;
			setInfo(info);
			this.type = type;
			this.version = version;
		}
		
		public DatabaseHead(String type, int version, String info) {
			setInfo(info);
			this.type = type;
			this.version = version;
			dataPointer = DEFAULT_DATA_START_POS;
			indexPointer = DEFAULT_INDEX_START_POS;
		}
		
		public Long getTYPE_POINTER_POS() {
			return TYPE_POINTER_POS;
		}

		public Long getVERSION_POINTER_POS() {
			return VERSION_POINTER_POS;
		}

		public Long getDataPointer() {
			return dataPointer;
		}

		public void setDataPointer(Long dataPointer) {
			this.dataPointer = dataPointer;
		}

		public Long getIndexMapPointer() {
			return indexPointer;
		}

		public void setIndexMapPointer(Long indexMapPointer) {
			this.indexPointer = indexMapPointer;
		}

		public int getVersion() {
			return version;
		}

		public String getType() {
			return type;
		}

		public String getInfo() {
			return new String(info);
		}

		public void setInfo(String info) {
			if (info.length() > MAX_INFO_LEN) {
				this.info = info.substring(0, MAX_INFO_LEN);
			} else {
				this.info = info;
			}
		}

		public void addToInfo(String s) {
			if ((s.length() + this.info.length()) <= MAX_INFO_LEN) {
				setInfo(this.info + s);
			} else {
				setInfo(this.info
						+ s.substring(0, MAX_INFO_LEN - this.info.length()));
			}
		}

		public void setType(String type) {
			if (type.length() > MAX_TYPE_LEN) {
				this.type = type.substring(0, MAX_INFO_LEN);
			} else {
				this.type = type;
			}
		}

		public void setVersion(int version) {
			this.version = version;
		}

		public Long getINDEX_POINTER_POS() {
			return INDEX_POINTER_POS;
		}

		public Long getDATA_POINTER_POS() {
			return DATA_POINTER_POS;
		}

		public Long getINFO_POINTER_POS() {
			return INFO_POINTER_POS;
		}
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws DatabaseException {
		RecordDatabase rd = new RecordDatabase("aaa.bin");
		//rd.create();
		rd.start("rw");
		//System.out.println(rd.insert("jahudka2",new Date()));
		//System.out.println(rd.insert("banan1", new Integer(20)));
		//System.out.println(rd.insert("morce2", new Random().nextDouble()));
		System.out.println("a : " + rd.get("banan"));
		System.out.println("b : " + rd.get("morce"));
		System.out.println("c : " + rd.get("jahudka2"));
		System.out.println("c : " + rd.get("nahoda"));
		System.out.println("e : " + rd.get("jahudka"));
		System.out.println("d : " + rd.indexMap.size());
		rd.stop();
		String string = new String("aaa");
		String string2 = new String("aaa");
		System.out.print(string == string2);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
