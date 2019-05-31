package edu.handong.analysis.datamodel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Code {
    private int year;
    private int semester;
    private String courseCode;
    private String courseName;
    private double totalStudents;
    private double studentsTaken;
    private double rate;
    private ArrayList<String> lines;
    private String studentIdForCheck;

    public Code(Integer year, Integer semester, String courseCode, ArrayList<String> lines){
        this.year = year;
        this.semester = semester;
        this.courseCode = courseCode;
        this.lines = lines;
        setCourseName();
        setTotalStudents();
        setStudentsTaken();
        setRate();
    }//constructor

    public Integer getYear() {
        return year;
    }
    public Integer getSemester() {
        return semester;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName() {
        for(String line:lines){
            if(line.split(", ")[4].equals(courseCode))
                courseName = line.split(", ")[5];
        }
    }

    public double getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents() {
        studentIdForCheck = "0";
        for(String line:lines){
            String data[] = line.split(", ");
            if(data[7].equals(Integer.toString(year)) && data[8].equals(Integer.toString(semester)) && !data[0].equals(studentIdForCheck)) {
                totalStudents++;
                studentIdForCheck = data[0];
            }
        }
    }

    public double getStudentsTaken() {
        return studentsTaken;
    }

    public void setStudentsTaken() {
        for(String line:lines){
            String data[] = line.split(", ");
            if(data[7].equals(Integer.toString(year)) && data[8].equals(Integer.toString(semester)))
                if(data[4].equals(courseCode))
                    studentsTaken++;
        }
    }

    public double getRate() {
        return rate;
    }

    public void setRate() {
        rate = studentsTaken/totalStudents;
    }

}
