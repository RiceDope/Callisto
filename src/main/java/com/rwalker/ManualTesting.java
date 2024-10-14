package com.rwalker;

import java.util.Arrays;

public class ManualTesting {
    public static void main(String[] args) throws NoSuchMethodException{
        Sequence<Student> test = new Sequence<Student>();
        test.append(new Student(21, "George"));
        test.append(new Student(20, "Nathan"));
        test.append(new Student(16, "Ben"));
        test.append(new Student(17, "Thomas"));
        System.out.println(test.toString());
        test.setEnforceSort(true, new SortByAge());
        System.out.println(test.toString());
        test.setAscending(true, new SortByName());
        // test.sort(new SortByAge());
        System.out.println(test.toString());

        // Student[] test = new Student[4];
        // test[0] = new Student(21, "George");
        // test[1] = new Student(12, "Ben");
        // test[2] = new Student(15, "Mike");
        // test[3] = new Student(100, "Nathan");
        // Arrays.sort(test, new SortByName());
        // for (int i = 0; i < test.length; i++){
        //     System.out.println(test[i].toString());
        // }
    }
}

