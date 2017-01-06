package net.kzn.onlinecollaboration.model;

public class SecurityError {
	private String reason;
	private String message;
	
	public SecurityError() {}
	
	public SecurityError(String reason, String message) {
		super();
		this.reason = reason;
		this.message = message;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
}
