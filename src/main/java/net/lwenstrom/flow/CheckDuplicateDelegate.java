package net.lwenstrom.flow;

import net.lwenstrom.ProcessConstants;
import net.lwenstrom.mock.StudentTable;
import net.lwenstrom.mock.StudentTableEntry;
import net.lwenstrom.model.RejectionProcessVariables;
import net.lwenstrom.model.Student;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

public class CheckDuplicateDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(CheckDuplicateDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Student student  = (Student) execution.getVariable(ProcessConstants.VAR_STUDENT);

        RejectionProcessVariables rm = new RejectionProcessVariables(execution);
        StudentTable studentTable = StudentTable.getInstance();
        //TODO: remove
        studentTable.reset();

        LOGGER.info(studentTable.count() + "");
        if (!studentTable.contains(student.getStudentID())) {
            rm.setDuplicateCheckApproved(true);
            studentTable.save(new StudentTableEntry(student));
        } else {
            rm.setDuplicateCheckApproved(false);
            rm.setRejectionMessage("Dieser Student hat bereits einen Bergrueßungsgeldantrag gestellt!");
        }
    }
}
