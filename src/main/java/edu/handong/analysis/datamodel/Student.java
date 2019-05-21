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
        coursesTaken.add(newRecord);
    }
    public HashMap<String,Integer> getSemestersByYearAndSemester(){
        return semestersByYearAndSemester;
    }
    public int getNumCourseInNthSemester(int semester){
        int count = 0;
        return count;
    }
        /* field에 대한 getter setter 필요에 따라 추가 */

}
