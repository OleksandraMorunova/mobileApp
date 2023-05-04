package com.umsf.lab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ArrayListStudents {
    public final static ArrayList<Student> students = new ArrayList<>(Arrays.asList(
            new Student("Іванов Роман", "301"),
            new Student("Петров Федір", "301"),
            new Student("Осадча Оксана", "302"),
            new Student("Максимов Руслан", "302"),
            new Student("Смірнов Василь", "308"),
            new Student("Потапова Марія", "309"),
            new Student("Гонський Іван", "309"),
            new Student("Васильєв Максим", "309")
    ));

    public static ArrayList<Student> getStudents(String groupStudents){
        ArrayList<Student> stList = new ArrayList<>();
        for(Student st: students){
            if(Objects.equals(st.getGroupNumber(), groupStudents)) stList.add(st);
        }
        return stList;
    }
}