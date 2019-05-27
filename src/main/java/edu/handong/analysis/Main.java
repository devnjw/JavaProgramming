package edu.handong.analysis;

import org.apache.commons.cli.Options;

public class Main {
	public static void main(String[] args) {
		HGUCoursePatternAnalyzer analyzer = new HGUCoursePatternAnalyzer();
		analyzer.run(args);
	}
}