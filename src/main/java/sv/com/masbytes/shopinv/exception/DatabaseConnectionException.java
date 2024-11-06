package sv.com.masbytes.shopinv.exception;

public class DatabaseConnectionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatabaseConnectionException(String message) {
		super("Database connection error: " + message);
	}

	public DatabaseConnectionException(String message, Throwable cause) {
		super("Database connection error: " + message, cause);
	}

}