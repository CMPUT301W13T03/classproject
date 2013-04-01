package ca.c301.t03_exceptions;

/**
 * Exception for when the User enters an empty string
 */
public class NullStringException extends Exception {

	private static final long serialVersionUID = 1L;

	public NullStringException() {  
		super("No String Entered");  
	}  

	public NullStringException(String msg) {  
		this();  
	}  
}
