package edu.handong.analysis;

public class Main {
	public static void main(String[] args) {
		System.out.println("Debugging : " + args[0] + " + " + args[1]);
		HGUCoursePatternAnalyzer analyzer = new HGUCoursePatternAnalyzer();
		analyzer.run(args);
	}
}