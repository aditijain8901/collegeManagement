package com.example.attendance;

public class attendconst {
    private String dateofattendance;
    private String courseofattendace;
    private String students;

    public attendconst() {

    }
    public attendconst(String dateofattendance, String courseofattendace, String students) {

        this.dateofattendance=dateofattendance;
        this.courseofattendace = courseofattendace;
        this.students=students;
    }

    public String getDateofattendance() {
        return dateofattendance;
    }

    public String getCourseofattendace() {
        return courseofattendace;
    }

    public String getStudents() {
        return students;
    }
}