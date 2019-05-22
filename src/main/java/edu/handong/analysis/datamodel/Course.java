package edu.handong.analysis.datamodel;

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


    public Course(String line){
        String data[] = line.split(", ");
        studentId = data[0];
        yearMonthGraduated = data[1];
        firstMajor = data[2];
        secondMajor = data[3];
        courseCode = data[4];
        courseName = data[5];
        courseCredit = data[6];
        yearTaken = Integer.parseInt(data[7]);
        semesterCourseTaken = Integer.parseInt(data[8]);
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
