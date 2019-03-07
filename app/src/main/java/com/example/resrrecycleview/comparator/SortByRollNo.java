package com.example.resrrecycleview.comparator;

import com.example.resrrecycleview.model.Student;

import java.util.Comparator;

/**
 * This comparator class used to sort object data on some datatype bases
 *
 * this class used to sort Student data by its Roll No
 */
public class SortByRollNo  implements Comparator<Student> {
    @Override
    public int compare(Student object1, Student object2) {
        if(Integer.parseInt(object1.getmId())>Integer.parseInt(object2.getmId()))
            return 1;
        if(Integer.parseInt(object1.getmId())<Integer.parseInt(object2.getmId()))
            return -1;

        return 0;
    }

}
