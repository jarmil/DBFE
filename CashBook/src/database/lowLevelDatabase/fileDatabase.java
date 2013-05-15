package database.lowLevelDatabase;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 12.1.13
 * Time: 18:53
 * To change this template use File | Settings | File Templates.
 */
public abstract class fileDatabase {

    private RandomAccessFile dbFile;

    // Total length in bytes of the global database headers.
    protected static final int FILE_HEADERS_REGION_LENGTH = 16;

    // Number of bytes in the record header.
    protected static final int RECORD_HEADER_LENGTH = 16;

    // The length of a key in the index.
    protected static final int MAX_KEY_LENGTH = 64;

    // The total length of one index entry - the key length plus the record header length.
    protected static final int INDEX_ENTRY_LENGTH = MAX_KEY_LENGTH + RECORD_HEADER_LENGTH;

    // File pointer to the num records header.
    protected static final long NUM_RECORDS_HEADER_LOCATION = 0;

    // File pointer to the data start pointer header.
    protected static final long DATA_START_HEADER_LOCATION = 4;

    public fileDatabase(String file) throws IOException{
        File tmp = new File(file);
        if(!tmp.exists()) {
               throw new RuntimeException("File doesn't exists!");
        }
        dbFile = new RandomAccessFile(file,"rw");

    }

    public void add(Object o){

    }
    public Object get(String key){
          return null;
    }
    public void change(Object o, String key){

    }

    public void remove(String key){

    }

}
