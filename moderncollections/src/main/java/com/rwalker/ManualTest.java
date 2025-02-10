package com.rwalker;

import java.util.Arrays;
import java.util.Iterator;

import com.rwalker.sequenceStrategies.DefaultSequence;
import com.rwalker.sequenceStrategies.SequenceContext;
import com.rwalker.sequenceStrategies.SequenceState;
import com.rwalker.sequenceStrategies.SequenceStrategies;
import com.rwalker.sequenceStrategies.DefaultStrategy.DefaultSequenceStrategy;
import com.rwalker.sequenceStrategies.DefaultStrategy.SortedDefaultSequence;
import com.rwalker.sequenceStrategies.DefaultStrategy.UnsortedDefaultSequence;

public class ManualTest {
    public static void main(String[] args){

        SequenceContext<Integer> context = new SequenceContext<>();
        context.comparator = (a, b) -> a - b;
        context.initialSize = 4;
        context.currentState = SequenceState.UNSORTED;
        
        DefaultSequence<Integer> seq = new DefaultSequence<>(context);

        seq.add(10);
        seq.replace(0, 1);
        seq.contains(1);
        seq.sortOnwards();

        System.out.println(seq.getname());
        System.out.println(seq.getstate());


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