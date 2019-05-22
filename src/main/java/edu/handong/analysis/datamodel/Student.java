package edu.handong.analysis.datamodel;

import javax.sound.midi.SysexMessage;
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
        coursesTaken = new ArrayList<Course>();
    } // constructor

    public void addCourse(Course newRecord){
        coursesTaken.add(newRecord);
    }

    public void setSemestersByYearAndSemester() {
        semestersByYearAndSemester = new HashMap<String, Integer>();
        int semester = 1;
        int yearTaken = coursesTaken.get(0).getYearTaken();
        int semesterCourseTaken = coursesTaken.get(0).getSemesterCourseTaken();

        semestersByYearAndSemester.put(yearTaken + "-" + semesterCourseTaken, semester++);

        for(int i = 0; i < coursesTaken.size(); i++){
            Course course = coursesTaken.get(i);
            if(course.getSemesterCourseTaken() != semesterCourseTaken || course.getYearTaken() != yearTaken) {
                yearTaken = course.getYearTaken();
                semesterCourseTaken = course.getSemesterCourseTaken();
                semestersByYearAndSemester.put(yearTaken + "-" + semesterCourseTaken, semester++);
            }
        }
    }
    public HashMap<String,Integer> getSemestersByYearAndSemester(){
        return semestersByYearAndSemester;
    }
    public int getNumCourseInNthSemester(int semester){
        int count = 0;

        for(int i = 0; i < coursesTaken.size(); i++)
            if(semester == semestersByYearAndSemester.get(coursesTaken.get(i).getYearTaken() + "-" + coursesTaken.get(i).getSemesterCourseTaken()))
                count++;

        return count;
    }
        /* field에 대한 getter setter 필요에 따라 추가 */

    public String getStudentId() {
        return studentId;
    }

}
