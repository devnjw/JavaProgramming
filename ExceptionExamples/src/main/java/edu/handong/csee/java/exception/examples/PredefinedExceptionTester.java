package edu.handong.csee.java.exception.examples;

public class PredefinedExceptionTester {

	public static void main(String[] args) {
		PredefinedExceptionTester tester = new PredefinedExceptionTester();
		tester.run(args);
	}
	
	public void run(String[] args) {
		//try {
			System.out.println(args[0]);
			System.out.println(args[1]);
			System.out.println(1/0);
		/*}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Check your CLI arguments. You need three arguments!");
			System.out.println(e.getMessage() + " " + e.toString());
			e.printStackTrace();
		}*/
		
		System.out.println("Program finished normally!");
	}

}
