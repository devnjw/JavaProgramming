package edu.handong.csee.java.exception.examples.dividebyzero;

public class DivideByZeroException extends Exception {

	public DivideByZeroException ()
	{
		super("edu.handong.csee.java.exception.examples.dividebyzero.DivideByZeroException occured!");
	}

	public DivideByZeroException (String message)
	{
		super(message);
	}
}
