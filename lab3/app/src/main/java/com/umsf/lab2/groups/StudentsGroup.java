package com.umsf.lab2.groups;

public class StudentsGroup {
    private String number;
    private String facultyName;
    private int educationLevel;
    private boolean contractExistsFlg;
    private boolean privilageExistsFlg;

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getFacultyName() {
        return facultyName;
    }
    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
    public int getEducationLevel() {
        return educationLevel;
    }
    public void setEducationLevel(int educationLevel) {
        this.educationLevel = educationLevel;
    }
    public boolean isContractExistsFlg() {
        return contractExistsFlg;
    }
    public void setContractExistsFlg(boolean contractExistsFlg) {
        this.contractExistsFlg = contractExistsFlg;
    }
    public boolean isPrivilageExistsFlg() {
        return privilageExistsFlg;
    }
    public void setPrivilageExistsFlg(boolean privilageExistsFlg) {
        this.privilageExistsFlg = privilageExistsFlg;
    }

    public StudentsGroup(String number, String facultyName, int educationLevel, boolean contractExistsFlg, boolean privilageExistsFlg) {
        this.number = number;
        this.facultyName = facultyName;
        this.educationLevel = educationLevel;
        this.contractExistsFlg = contractExistsFlg;
        this.privilageExistsFlg = privilageExistsFlg;
    }
}
