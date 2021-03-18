package net.lwenstrom.mock;

import net.lwenstrom.model.Student;

public interface StudentTable {
    void save(Student student);
    Student search(long studentID);
    int count();
}
