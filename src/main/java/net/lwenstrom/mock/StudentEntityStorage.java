package net.lwenstrom.mock;

import net.lwenstrom.model.Student;

import java.util.HashMap;
import java.util.Map;

public class StudentEntityStorage implements StudentTable{

    private Map<Long, Student> students;

    public StudentEntityStorage(){
       students = new HashMap<>();

       //adding dummy student to display case if request has already been sent by student
       Student dummy = new Student();
       dummy.setStudentID(123);
       students.put(dummy.getStudentID(), dummy);
    }
    @Override
    public void save(Student student) {
        students.put(student.getStudentID(), student);
    }

    @Override
    public Student search(long studentID) {
        return students.get(studentID);
    }

    @Override
    public int count() {
        return students.size();
    }
}
