package edu.handong.analysis.datamodel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;

public class Course {
    private String studentId;
    private String yearMonthGraduated;
    private String firstMajor;
    private String secondMajor;
    private String courseCode;
    private String courseName;
    private String courseCredit;
    private int yearTaken;
    private int semesterCourseTaken;


    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public Course(String input){
        try {
            Reader in = new FileReader(input);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            for (CSVRecord data : records) {
                studentId = data.get(0).trim();
                yearMonthGraduated = data.get(1).trim();
                firstMajor = data.get(2).trim();
                secondMajor = data.get(3).trim();
                courseCode = data.get(4).trim();
                courseName = data.get(5).trim();
                courseCredit = data.get(6).trim();
                yearTaken = Integer.parseInt(data.get(7).trim());
                semesterCourseTaken = Integer.parseInt(data.get(8).trim());
            }
        } catch( Exception e ){
            e.printStackTrace();
        }
        //String data[] = line.split(", ");

    }


    public String getStudentId() {
        return studentId;
    }
    public int getYearTaken() {
        return yearTaken;
    }
    public int getSemesterCourseTaken() {
        return semesterCourseTaken;
    }
}
