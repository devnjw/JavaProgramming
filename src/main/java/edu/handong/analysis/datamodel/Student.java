package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {


    private String studentId;
    private ArrayList<Course> coursesTaken; // 학생이 들은 수업 목록
    private HashMap<String,Integer> semestersByYearAndSemester;
        // key: Year-Semester
        // e.g., 2003-1,

    public Student(String studentId){
        this.studentId = studentId;
    } // constructor
    public void addCourse(Course newRecord){
        coursesTaken = new ArrayList<Course>();
        coursesTaken.add(newRecord);
    }
    public HashMap<String,Integer> getSemestersByYearAndSemester(){
        semestersByYearAndSemester = new HashMap<String, Integer>();
        int semester = 1;
        int yearTaken = coursesTaken.get(0).getYearTaken();
        int semesterCourseTaken = coursesTaken.get(0).getSemesterCourseTaken();
        semestersByYearAndSemester.put(Integer.toString(yearTaken) + "-" + Integer.toString(semesterCourseTaken), semester++);

        for(Course course:coursesTaken){
            if(course.getSemesterCourseTaken() != semesterCourseTaken || course.getYearTaken() != yearTaken) {
                yearTaken = course.getYearTaken();
                semesterCourseTaken = course.getSemesterCourseTaken();
                semestersByYearAndSemester.put(Integer.toString(yearTaken) + "-" + Integer.toString(semesterCourseTaken), semester++);
            }
        }

        return semestersByYearAndSemester;
    }
    public int getNumCourseInNthSemester(int semester){
        int count = 0;
        return count;
    }
        /* field에 대한 getter setter 필요에 따라 추가 */

    public String getStudentId() {
        return studentId;
    }

}
