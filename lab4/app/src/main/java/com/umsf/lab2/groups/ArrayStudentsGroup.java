package com.umsf.lab2.groups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ArrayStudentsGroup {
    public static ArrayList<StudentsGroup> studentsGroup = new ArrayList<>(Arrays.asList(
            new StudentsGroup("301", "Комп'ютерні науки", 0, true, false),
            new StudentsGroup("302", "Комп'ютерні науки", 0, true, false),
            new StudentsGroup("308", "Комп'ютерні науки", 0, true, true),
            new StudentsGroup("309", "Комп'ютерні науки", 0, true, false),
            new StudentsGroup("511м", "Комп'ютерні науки", 0, false, false)
    ));

    public static ArrayList<StudentsGroup> getStudents(String groupNumbers){
        ArrayList<StudentsGroup> sList = new ArrayList<>();
        for(StudentsGroup sg: studentsGroup){
            if(Objects.equals(sg.getNumber(), groupNumbers) || (groupNumbers == "")){
                sList.add(sg);
            }
        }
        return sList;
    }
}
