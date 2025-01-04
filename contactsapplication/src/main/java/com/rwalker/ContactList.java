package com.rwalker;

import com.rwalker.Sequence;
import java.util.Comparator;
public class ContactList {
    
    private Sequence<Contact> contacts;
    private Comparator<Contact> ageComp = (a, b) -> a.age - b.age;
    private Comparator<Contact> firstnameComp = (a, b) -> a.firstname.compareTo(b.firstname);
    private Comparator<Contact> surnameComp = (a, b) -> a.surname.compareTo(b.surname);
    private Comparator<Contact> numberComp = (a, b) -> a.number.compareTo(b.number);

    public ContactList() {
        contacts = new Sequence<>(surnameComp);
        contacts.sortOnwards();
    }

    public void addContact(Contact contact) {
        contacts.append(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contacts.firstIndexOf(contact));
    }

    public Contact getContact(String name) {
        for (Contact contact : contacts) {
            if (contact.firstname.equals(name) || contact.surname.equals(name)) {
                return contact;
            }
        }
        return null;
    }
}
