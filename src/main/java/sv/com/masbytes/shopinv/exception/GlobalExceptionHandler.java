package sv.com.masbytes.shopinv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Maneja la excepción DuplicateProductCodeException
	@ExceptionHandler(DuplicateProductCodeException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateProductCodeException(DuplicateProductCodeException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	// Maneja la excepción ProductNotFoundException
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	// Maneja la excepción DatabaseConnectionException
	@ExceptionHandler(DatabaseConnectionException.class)
	public ResponseEntity<ErrorResponse> handleDatabaseConnectionException(DatabaseConnectionException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	// Maneja cualquier otra excepción inesperada
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse("Hubo un error inesperado: " + ex.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

}