package edu.handong.analysis;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;

public class HGUCoursePatternAnalyzer {
	
	String[] lines = {	"1999-1, JC Nam, Java Programming",
						"1999-2, JC Nam, Programming Language Theory",
						"1999-1, JC Nam, Data Structures",
						"2001-1, JC Nam, Database Systems",
						"2018-1, SB Lim, Java Programming",
						"2018-2, SB Lim, Programming Language Theory",
						"2019-1, SB Lim, Data Structures",
						"2019-1, SB Lim, Algorithm Analysis",
						"2018-1, SJ Kim, Java Programming",
						"2018-2, SJ Kim, Programming Language Theory",
						"2019-1, SJ Kim, Logic Design",
						"2019-1, SJ Kim, Algorithm Analysis",
						};

	int numOfStudents;
	int numOfCourses;
	Student[] students;
	Course[] courses;
	
	/**
	 * This method runs our analysis logic to get the list of student and course names from lines.
	 * @param args
	 */
	public void run(String[] args) {
		
		numOfStudents = Integer.parseInt(args[0]);
		numOfCourses = Integer.parseInt(args[1]);
	
		students = initiateStudentArrayFromLines(lines);
		
		System.out.println("Number of All Students: " + numOfStudents);
		for(Student student: students) {
			if(!student.getName().equals("null"))
				System.out.println(student.getName());
		}
		
		courses = initiateCourseArrayFromLines(lines);
		System.out.println("Number of All Courses: " + numOfCourses);
		for(Course course: courses) {
			if(!course.getCourseName().equals("null"))
				System.out.println(course.getCourseName());
		}
	}

	/**
	 * This method returns a Student array to initiate the field, students, from lines.
	 * @param lines
	 * @return
	 */
	private Student[] initiateStudentArrayFromLines(String[] lines) {
		Student[] studentTemp = new Student[lines.length];
		int i = 0;
		for(String temp: lines)
			studentTemp[i++] = new Student(temp.split(", ")[1]);

		
		for(i=0; i < lines.length; i++)
			if(studentExist(studentTemp, studentTemp[i]))
				studentTemp[i] = new Student("null");
			

		return studentTemp;
	}

	/**
	 * This method check if there is the same name of the second argument in the array, students
	 * @param students
	 * @param student
	 * @return boolean
	 */
	private boolean studentExist(Student[] students, Student student) {
		int count = 0;
		for(int i = 0; i < students.length; i++) {
			if(students[i].getName().equals(student.getName())) {
				count++;
			}
			if(count >= 2)
				return true;
		}
			
		return false;
	}
	
	/**
	 * This method returns a Course array to initiate the field, courses, from lines.
	 * @param lines
	 * @return
	 */
	private Course[] initiateCourseArrayFromLines(String[] lines) {
		Course[] courseTemp = new Course[lines.length];
		int i = 0;
		for(String temp: lines)
			courseTemp[i++] = new Course(temp.split(", ")[2]);

		
		for(i=0; i < lines.length; i++)
			if(courseExist(courseTemp, courseTemp[i]))
				courseTemp[i] = new Course("null");
			

		return courseTemp;
	}

	/**
	 * This method check if there is the same name of the second argument in the array, courses.
	 * @param courses
	 * @param course
	 * @return boolean
	 */
	private boolean courseExist(Course[] courses, Course course) {
		int count = 0;
		for(int i = 0; i < courses.length; i++) {
			if(courses[i].getCourseName().equals(course.getCourseName())) {
				count++;
			}
			if(count >= 2)
				return true;
		}
			
		return false;

	}

}
