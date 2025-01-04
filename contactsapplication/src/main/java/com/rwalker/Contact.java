package com.rwalker;

import java.time.LocalDate;
import java.util.Comparator;

public class Contact {
    
    public String firstname;
    public String surname;
    public int age;
    public String number;
    public LocalDate birthday;

    public Contact(String firstname, String surname, int age, String number, LocalDate birthday) {
        this.firstname = firstname;
        this.surname = surname;
        this.age = age;
        this.number = number;
        this.birthday = birthday;
    }
}
