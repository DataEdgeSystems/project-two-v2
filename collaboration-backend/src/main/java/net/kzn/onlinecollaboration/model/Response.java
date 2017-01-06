package net.kzn.onlinecollaboration.model;

public class Response {

	private int code;
	private String message;
	private SecurityError error;

	public Response() {

	}

	public Response(int code, String message, SecurityError error) {
		super();
		this.code = code;
		this.message = message;
		this.error = error;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SecurityError getError() {
		return error;
	}

	public void setError(SecurityError error) {
		this.error = error;
	}
}
