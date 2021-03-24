package net.lwenstrom.mock;

import net.lwenstrom.model.Student;

import java.util.Hashtable;
import java.util.Map;

/**
 * Mocking of a database table containing all historic requests.
 * <p>
 * Implemented as Singleton to ditch annotation management (@Component, Singleton bean
 * in Spring but docker image uses Tomcat as servlet engine hence this solution)
 */
public class StudentTable implements TableMock<Long, StudentTableEntry> {

    private static StudentTable instance;
    private Map<Long, StudentTableEntry> students;

    private StudentTable() {
        students = new Hashtable<>();

        //adding dummy student to display case if request has already been sent by student
        Student dummy = new Student();
        dummy.setStudentID(34937);
        students.put(dummy.getStudentID(), new StudentTableEntry(dummy));
    }

    public static StudentTable getInstance() {
        if (StudentTable.instance == null) {
            StudentTable.instance = new StudentTable();
        }
        return StudentTable.instance;
    }

    @Override
    public void save(StudentTableEntry studentTableEntry) {
        students.put(studentTableEntry.getStudent().getStudentID(), studentTableEntry);
    }

    @Override
    public boolean contains(Long studentID) {
        return students.containsKey(studentID);
    }

    @Override
    public StudentTableEntry search(Long studentID) {
        return students.get(studentID);
    }

    @Override
    public void delete(Long studentID) {
        students.remove(studentID);
    }

    @Override
    public int count() {
        return students.size();
    }

    // For developing purposes only
    public void reset(){
        students = new Hashtable<>();
    }
}
