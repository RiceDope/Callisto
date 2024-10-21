package com.rwalker;

import java.util.Comparator;

public class ManualTesting {
    public static void main(String[] args) throws NoSuchMethodException{

        // (a, b) ->  a.getAge() - b.getAge()
        Sequence<Student> test = new Sequence<Student>((a, b) ->  a.getAge() - b.getAge());
        test.append(new Student(20, "Rhys"));
        test.append(new Student(15, "Rhys"));
        test.append(new Student(30, "Rhys"));
        test.setEnforceSort(true);
        // test.sort((a, b) ->  a.getAge() - b.getAge());
        test.sort();

        // test.sort((a, b) ->  b.getAge() - a.getAge());

        // test.sort(Student::getMother);

        //test.sort(new SortByAge());
        test.append(new Student(13, "Rhys"));
        test.append(new Student(35, "Rhys"));

        System.out.println(test.toString());

        // Integer[] test = new Integer[10];
        // test[3] = 10;
        // test[4] = 15;
        // test[5] = 20;

        // int start = 3;
        // int end = 6;


        // System.out.println(BinarySearch.findIndex(test, start, end, 14, (a, b) -> a-b));

    }
}

class SortByInt implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        return o1 - o2;
    }

}
