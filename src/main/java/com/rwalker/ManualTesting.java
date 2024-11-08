package com.rwalker;

import java.util.Comparator;
import java.util.Iterator;
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

        // String inputTest = "Hello8";
        // int hashCode = inputTest.hashCode() % 4;
        // if (hashCode < 0){
        //     hashCode = hashCode * -1;
        // }
        // System.out.println(hashCode);

        // 2 = Hello, Hello4, Hello8

        // Map<String, Integer> test = new Map<>((a, b) -> a.compareTo(b));
        // test.put("Hello", 100);
        // test.put("Hello1", 101);
        // test.put("Hello2", 102);
        // test.put("Hello3", 103);
        // test.put("Hello4", 104);
        // test.put("Hello5", 105);
        // test.put("Hello6", 106);
        // test.put("Hello7", 107);
        // test.put("Hello8", 108);
        // test.put("Hello9", 109);
        // test.remove("Hello9", 109);
        // System.out.println(test.keySet().toString());
        // System.out.println(test.sortedKeySet().toString());

        // System.out.println(test.toString());

        // test.replace("Hello10", 50);
        // test.replace("Hello", 50);

        // test.replace("Hello1", 50, 101);
        // test.replace("Hello2", 50, 101);

        // System.out.println(test.toString());

        // Map<String, Integer> test1 = new Map<>();
        // test1.putAll(test);
        // System.out.println(test1.toString());
        
        // System.out.println(test.keySet());
        // System.out.println(test.sortedKeySet((a, b) -> b.compareTo(a)));

        // System.out.println(test.getBucket(2));
        // test.remove("Hello8");
        // System.out.println(test.getBucket(2));

        Sequence<Integer> test1 = new Sequence<>((a, b) -> a - b);
        test1.append(100);
        test1.append(95);
        test1.append(150);
        test1.append(50);
        Iterator<Integer> it = test1.iterator();
        while(it.hasNext()){
            System.out.println(it.next().toString());
        }

    }
}