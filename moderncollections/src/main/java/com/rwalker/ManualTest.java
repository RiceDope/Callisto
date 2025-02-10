package com.rwalker;

import java.util.Arrays;
import java.util.Iterator;

import com.rwalker.sequenceStrategies.SequenceContext;
import com.rwalker.sequenceStrategies.SequenceStrategies;
import com.rwalker.sequenceStrategies.SortedDefaultSequence;

public class ManualTest {
    public static void main(String[] args){

        SequenceContext<Integer> context = new SequenceContext<>();
        context.comparator = (a, b) -> a - b;
        context.initialSize = 4;
        
        SortedDefaultSequence<Integer> sortedDefaultSequence = new SortedDefaultSequence<>(context);

        sortedDefaultSequence.add(1);
        sortedDefaultSequence.add(2);
        sortedDefaultSequence.add(6);
        sortedDefaultSequence.add(0);
        sortedDefaultSequence.add(7);
        sortedDefaultSequence.add(3);
        sortedDefaultSequence.add(null);
        sortedDefaultSequence.add(null);
        sortedDefaultSequence.add(4);
        sortedDefaultSequence.add(5);
        sortedDefaultSequence.add(6);
        sortedDefaultSequence.add(null);
        sortedDefaultSequence.add(8);
        sortedDefaultSequence.add(9);
        sortedDefaultSequence.add(20);

        System.out.println(sortedDefaultSequence.rawString());
        System.out.println(sortedDefaultSequence);

        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);

        System.out.println(sortedDefaultSequence.rawString());
        System.out.println(sortedDefaultSequence);

        sortedDefaultSequence.insert(2, 12);

        System.out.println(sortedDefaultSequence.rawString());
        System.out.println(sortedDefaultSequence);

        sortedDefaultSequence.insert(0, 6);

        System.out.println(sortedDefaultSequence.rawString());
        System.out.println(sortedDefaultSequence);

        sortedDefaultSequence.replace(4, 24);
        sortedDefaultSequence.insert(5, 26);
        sortedDefaultSequence.insert(6, null);
        sortedDefaultSequence.add(9);

        System.out.println(sortedDefaultSequence.rawString());
        System.out.println(sortedDefaultSequence);

        Iterator<Integer> it = sortedDefaultSequence.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
            it.remove();
        }

        // Example Sequence Programs
        
        // Sequence<Student> students = new Sequence<>();
        // students.setComparator((a, b) -> a.getAge() - b.getAge());
        // students.sortOnwards();
        // students.add(new Student("Bob", 20));
        // students.add(new Student("Alice", 21));
        // students.add(new Student("Charlie", 19));
        // students.add(new Student("David", 22));
        // students.add(new Student("Eve", 18));
        // System.out.println(students);
        // Sequence<Student> descStudents;
        // descStudents = students.sortCopy((a, b) -> b.getAge() - a.getAge());
        // System.out.println(descStudents);

    }
}   