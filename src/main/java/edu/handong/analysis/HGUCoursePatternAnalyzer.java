package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysise.utils.NotEnoughArgumentException;
import edu.handong.analysise.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = args[0]; // csv file to be analyzed
		String resultPath = args[1]; // the file path where the results are saved.
		ArrayList<String> lines = Utils.getLines(dataPath, true);
		
		students = loadStudentCourseRecords(lines);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		
		// TODO: Implement this method
		HashMap<String,Student> students = new HashMap<String,Student>();

		Student student;
		for(int i = 0; i < Integer.parseInt(lines.get(lines.size()-1).split(", ")[0]); i++) {
			student = new Student(String.format("%04d", i+1));
			students.put(String.format("%04d", i+1), student);
		}

		//Course course;
		for(String line:lines) {
			Course course = new Course(line);
			students.get(course.getStudentId()).addCourse(course);
		}

		return students; // do not forget to return a proper variable.

	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		// TODO: Implement this method
		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester");

		for(int i = 0; i < sortedStudents.size(); i++){
			sortedStudents.get(String.format("%04d", i+1)).setSemestersByYearAndSemester();
			for(int j = 0; j < sortedStudents.get(String.format("%04d", i+1)).getSemestersByYearAndSemester().size(); j++)
				arrayList.add(sortedStudents.get(String.format("%04d", i+1)).getStudentId() + ", " + sortedStudents.get(String.format("%04d", i+1)).getSemestersByYearAndSemester().size() + ", " + Integer.toString(j+1) + ", " + Integer.toString(sortedStudents.get(String.format("%04d", i+1)).getNumCourseInNthSemester(j+1)));
		}

		return arrayList; // do not forget to return a proper variable.
	}
}
