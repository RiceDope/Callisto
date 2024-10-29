package com.rwalker;

import java.util.Comparator;
public class ManualTesting {
    public static void main(String[] args) {

        // (a, b) ->  a.getAge() - b.getAge()

        // Instantiate with a comparator sorting ascending
        // Sequence<Student> test = new Sequence<Student>((a, b) ->  a.getAge() - b.getAge());

        // // Add some Students
        // test.append(new Student(20, "a"));
        // test.append(new Student(15, "b"));
        // test.append(new Student(30, "c"));

        // // Enforce sort on the array
        // test.setEnforceSort(true);

        // // Now sort in decending order
        // test.sort((a, b) -> b.getAge() - a.getAge());

        // // These will insert into the wrong position
        // test.append(new Student(13, "d"));
        // test.append(new Student(35, "e"));

        // // OUTPUT = "[Age 13 Name d , Age 30 Name c , Age 20 Name a , Age 15 Name b , Age 35 Name e ]"
        // System.out.println(test.toString()); // Not correctly sorted

        // // Call now with default comparator that was set at instantiation
        // test.sort();

        // // OUTPUT = "[Age 13 Name d , Age 15 Name b , Age 20 Name a , Age 30 Name c , Age 35 Name e ]"
        // System.out.println(test.toString()); // Now correctly sorted

        // test.sort();

        // test.append(new Student(30, "c"));

        // System.out.println(test.toString());

        // System.out.println(test.contains(new Student(30, "c")));

        // int[] example = test.allIndexesOf(new Student(30, "c"));
        // for (int i = 0; i < example.length; i++){
        //     System.out.println(example[i]);
        // }

        // Sequence<Student> testing = new Sequence<Student>((a, b) ->  a.getAge() - b.getAge());
        // testing.append(new Student(20, "a"));
        // testing.append(new Student(15, "b"));
        // testing.append(new Student(30, "c"));
        // testing.append(new Student(30, "c"));
        // testing.append(new Student(13, "d"));
        // testing.append(new Student(35, "e"));

        // testing.sort();
        
        // System.out.println(testing.toString());

        // System.out.println(test.equals(testing));

        // Integer[] test = new Integer[10];
        // test[3] = 10;
        // test[4] = 15;
        // test[5] = 20;

        // int start = 3;
        // int end = 6;


        // System.out.println(BinarySearch.findIndex(test, start, end, 14, (a, b) -> a-b));

        // String inputTest = "Moron1";
        // int hashCode = inputTest.hashCode() % 4;
        // if (hashCode < 0){
        //     hashCode = hashCode * -1;
        // }

        // System.out.println(hashCode);

        Map<String, Integer> test = new Map<>(4, 0.75f, 2.0f);

        test.put("Hello", 100);
        test.put("Moron1", 150);
        test.put("Moronic", 450);
        test.put("ALA", 600);
        test.put("HFHF", 100);
        test.put("HEHEHEHEHEH", 700);
        test.put("KEEFED", 999);
        test.put("Crack", 153);
        System.out.println(test.get("Moron1"));
        System.out.println(test.get("Hello"));
        System.out.println(test.toString());

    }
}