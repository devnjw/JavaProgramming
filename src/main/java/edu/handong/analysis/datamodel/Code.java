package edu.handong.analysis.datamodel;

public class Code {
    private int year;
    private int semester;
    private String courseCode;
    private String CourseName;
    private String TotalStudents;
    private String StudentsTaken;
    private String Rate;

    public Code(Integer year, Integer semester, String courseCode){
        this.year = year;
        this.semester = semester;
        this.courseCode = courseCode;
    }//constructor

    public Integer getYear() {
        return year;
    }
    public Integer getSemester() {
        return semester;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getTotalStudents() {
        return TotalStudents;
    }

    public void setTotalStudents(String totalStudents) {
        TotalStudents = totalStudents;
    }

    public String getStudentsTaken() {
        return StudentsTaken;
    }

    public void setStudentsTaken(String studentsTaken) {
        StudentsTaken = studentsTaken;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }


}
