package net.lwenstrom.mock;

import net.lwenstrom.model.Student;

import java.util.HashMap;
import java.util.Map;

/**
 * Mocking of a database table containing all historic requests.
 *
 * Implemented as Singleton to ditch annotation management (@Component, Singleton bean
 * in Spring but docker image uses Tomcat as servlet engine hence this solution)
 */
public class StudentEntityStorage implements StudentTable{

    private static StudentEntityStorage instance;
    private Map<Long, Student> students;

    private StudentEntityStorage(){
       students = new HashMap<>();

       //adding dummy student to display case if request has already been sent by student
       Student dummy = new Student();
       dummy.setStudentID(34937);
       students.put(dummy.getStudentID(), dummy);
    }

    public static StudentEntityStorage getInstance(){
        if(StudentEntityStorage.instance == null){
            StudentEntityStorage.instance = new StudentEntityStorage();
        }
        return StudentEntityStorage.instance;
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
