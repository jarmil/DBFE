package exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 28.1.13
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3046489910681922204L;

	public DatabaseException(String message){
        super(message);
    }
}
