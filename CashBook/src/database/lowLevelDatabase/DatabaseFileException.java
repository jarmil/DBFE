package database.lowLevelDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 13.1.13
 * Time: 19:11
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseFileException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4089076162652297598L;

	public DatabaseFileException(String message){
        super(message);
    }
}
