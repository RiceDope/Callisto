package com.rwalker;

import java.util.Comparator;

public class ManualTesting {
    public static void main(String[] args) throws NoSuchMethodException{

        // (a, b) ->  a.getAge() - b.getAge()

        // Instantiate with a comparator sorting ascending
        Sequence<Student> test = new Sequence<Student>((a, b) ->  a.getAge() - b.getAge());

        // Add some Students
        test.append(new Student(20, "a"));
        test.append(new Student(15, "b"));
        test.append(new Student(30, "c"));

        // Enforce sort on the array
        test.setEnforceSort(true);

        // Now sort in decending order
        test.sort((a, b) -> b.getAge() - a.getAge());

        // These will insert into the wrong position
        test.append(new Student(13, "d"));
        test.append(new Student(35, "e"));

        // OUTPUT = "[Age 13 Name d , Age 30 Name c , Age 20 Name a , Age 15 Name b , Age 35 Name e ]"
        System.out.println(test.toString()); // Not correctly sorted

        // Call now with default comparator that was set at instantiation
        test.sort();

        // OUTPUT = "[Age 13 Name d , Age 15 Name b , Age 20 Name a , Age 30 Name c , Age 35 Name e ]"
        System.out.println(test.toString()); // Now correctly sorted

        test.sort();

        test.append(new Student(30, "c"));

        System.out.println(test.toString());

        System.out.println(test.contains(new Student(30, "c")));

        Integer[] example = test.allIndexesOf(new Student(30, "c"));
        for (int i = 0; i < example.length; i++){
            System.out.println(example[i]);
        }

        Sequence<Student> testing = new Sequence<Student>((a, b) ->  a.getAge() - b.getAge());
        testing.append(new Student(20, "a"));
        testing.append(new Student(15, "b"));
        testing.append(new Student(30, "c"));
        testing.append(new Student(30, "c"));
        testing.append(new Student(13, "d"));
        testing.append(new Student(35, "e"));

        testing.sort();
        
        System.out.println(testing.toString());

        System.out.println(test.equals(testing));

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
