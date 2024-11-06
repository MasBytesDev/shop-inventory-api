package sv.com.masbytes.shopinv.exception;

public class InvalidProductDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidProductDataException(String message) {
		super("Invalid product data: " + message);
	}

	public InvalidProductDataException(String message, Throwable cause) {
		super("Invalid product data: " + message, cause);
	}

}