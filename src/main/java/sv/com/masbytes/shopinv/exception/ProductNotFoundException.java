package sv.com.masbytes.shopinv.exception;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String productCode) {
		super("Product with productCode " + productCode + " not found.");
	}

	public ProductNotFoundException(String productCode, Throwable cause) {
		super("Product with productCode " + productCode + " not found.", cause);
	}

}