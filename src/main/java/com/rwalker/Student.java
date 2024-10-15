package com.rwalker;

/**
 * Demonstration class for use of a Comparator when sorting through the array
 * GitHub: https://github.com/RiceDope/COMP6200-Modern-Collections-Library
 * 
 * @author Rhys Walker
 * @since 14/10/2024
 */

import java.util.Comparator;

public class Student {

    private String name;
    private int age;
    
    public Student(int age, String name){
        this.age = age;
        this.name = name;
    }

    /**
     * Setter method for age
     * @param age The new age
     */
    public void setAge(int age){
        this.age = age;
    }

    /**
     * Setter method for name
     * @param name The new name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Getter method for age
     * @return The students age
     */
    public int getAge(){
        return age;
    }

    /**
     * Getter method for name
     * @return The students name
     */
    public String getName(){
        return name;
    }

    /**
     * Overrides builtin toString method
     */
    @Override
    public String toString(){
        return "Age " + age + " Name " + name + " "; 
    }

}

/**
 * Allows for comparison of Student via age
 */
class SortByAge implements Comparator<Student>{
    public int compare(Student a, Student b){
        return a.getAge() - b.getAge();
    }
}

/**
 * Allows for comparison of Student via name
 */
class SortByName implements Comparator<Student>{
    public int compare(Student a, Student b){
        return a.getName().compareTo(b.getName());
    }
}