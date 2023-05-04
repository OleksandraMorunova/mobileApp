package com.umsf.lab1;

public class Student {
    private String name;
    private String groupNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public Student(String name, String groupNumber) {
        this.name = name;
        this.groupNumber = groupNumber;
    }
}