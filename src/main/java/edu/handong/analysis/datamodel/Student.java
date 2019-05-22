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
    } // constructor
    public void setCoursesTaken() {
        coursesTaken = new ArrayList<Course>();
    }

    public ArrayList<Course> getCoursesTaken() {
        return coursesTaken;
    }
    public void addCourse(Course newRecord){
        //coursesTaken = new ArrayList<Course>();
        coursesTaken.add(newRecord);
    }

    public void setSemestersByYearAndSemester() {
        semestersByYearAndSemester = new HashMap<String, Integer>();
        int semester = 1;
        int yearTaken = coursesTaken.get(0).getYearTaken();
        int semesterCourseTaken = coursesTaken.get(0).getSemesterCourseTaken();

        //System.out.println("test : "+ coursesTaken.size() + " + " + yearTaken + " + " + semesterCourseTaken);
        semestersByYearAndSemester.put(Integer.toString(yearTaken) + "-" + Integer.toString(semesterCourseTaken), semester++);

        for(int i = 0; i < coursesTaken.size(); i++){
            Course course = coursesTaken.get(i);
            //System.out.println(course.getSemesterCourseTaken() + "vs" + semesterCourseTaken);
            if(course.getSemesterCourseTaken() != semesterCourseTaken || course.getYearTaken() != yearTaken) {
                yearTaken = course.getYearTaken();
                semesterCourseTaken = course.getSemesterCourseTaken();
                semestersByYearAndSemester.put(Integer.toString(yearTaken) + "-" + Integer.toString(semesterCourseTaken), semester++);
                //System.out.println("WORKING!!! " + semestersByYearAndSemester.get(Integer.toString(yearTaken) + "-" + Integer.toString(semesterCourseTaken)));
            }
        }
    }
    public HashMap<String,Integer> getSemestersByYearAndSemester(){
        return semestersByYearAndSemester;
    }
    public int getNumCourseInNthSemester(int semester){
        int count = 0;

        for(int i = 0; i < coursesTaken.size(); i++){
            //System.out.println(semestersByYearAndSemester.get(coursesTaken.get(i).getYearTaken() + "-" + coursesTaken.get(i).getSemesterCourseTaken()));
            if(semester == semestersByYearAndSemester.get(coursesTaken.get(i).getYearTaken() + "-" + coursesTaken.get(i).getSemesterCourseTaken()))
                count++;
        }

        return count;
    }
        /* field에 대한 getter setter 필요에 따라 추가 */

    public String getStudentId() {
        return studentId;
    }

}
