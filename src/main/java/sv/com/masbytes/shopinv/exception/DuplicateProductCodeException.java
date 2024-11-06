package sv.com.masbytes.shopinv.exception;

public class DuplicateProductCodeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateProductCodeException(String productCode) {
		super("Product with productCode " + productCode + " already exists.");
	}

	public DuplicateProductCodeException(String productCode, Throwable cause) {
		super("Product with productCode " + productCode + " already exists.", cause);
	}

}