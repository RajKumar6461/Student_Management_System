package com.example.resrrecycleview.comparator;

import com.example.resrrecycleview.model.Student;

import java.util.Comparator;

/**
 * This comparator class used to sort object data on some datatype bases
 *
 * this class used to sort Student data by its Name
 */

public class SortByName implements  Comparator<Student> {
    @Override
    public int compare(Student object1, Student object2) {
        String obj1=object1.getmFirstName()+" "+object1.getmLastName();
        String obj2=object2.getmFirstName()+" "+object2.getmLastName();

        return obj1.compareTo(obj2);
    }
}
