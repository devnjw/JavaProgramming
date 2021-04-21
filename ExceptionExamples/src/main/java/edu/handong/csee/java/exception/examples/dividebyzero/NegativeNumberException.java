package edu.handong.csee.java.exception.examples.dividebyzero;

public class NegativeNumberException extends Exception {
	
	public NegativeNumberException() { 
		super("Negative Number Exception!");    
	} 
	
	public NegativeNumberException(String message)    { 
		super(message);    
	} 
}
