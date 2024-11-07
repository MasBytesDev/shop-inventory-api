package sv.com.masbytes.shopinv.exception;

import lombok.Data;

@Data
public class ErrorResponse {
	
	private String message;
	private int statusCode;
	private long timestamp;
	
	public ErrorResponse(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
		this.timestamp = System.currentTimeMillis();
	}

}