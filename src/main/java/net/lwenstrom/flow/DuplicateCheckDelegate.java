package net.lwenstrom.flow;

import net.lwenstrom.ProcessConstants;
import net.lwenstrom.mock.StudentEntityStorage;
import net.lwenstrom.model.RejectionProcessVariables;
import net.lwenstrom.model.Student;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DuplicateCheckDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Student student = (Student) execution.getVariable(ProcessConstants.VAR_STUDENT);
        RejectionProcessVariables rm = new RejectionProcessVariables(execution);
        StudentEntityStorage studentTable = StudentEntityStorage.getInstance();

        if(studentTable.search(student.getStudentID()) == null){
            rm.setDuplicateCheckApproved(true);
            studentTable.save(student);
        }else{
            rm.setDuplicateCheckApproved(false);
            rm.setRejectionMessage("Dieser Student hat bereits einen Bergrueßungsgeldantrag gestellt!");
        }
    }
}
