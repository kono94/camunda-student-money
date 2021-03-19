package net.lwenstrom.mock;

import net.lwenstrom.model.Student;

public class StudentTableEntry {
    private Student student;

    public StudentTableEntry(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
