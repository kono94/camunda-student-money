package net.lwenstrom.flow;

import net.lwenstrom.ProcessConstants;
import net.lwenstrom.mock.ActivationLinkTable;
import net.lwenstrom.mock.ActivationLinkTableEntry;
import net.lwenstrom.mock.StudentTable;
import net.lwenstrom.mock.StudentTableEntry;
import net.lwenstrom.model.ActivationLinkProcessVariables;
import net.lwenstrom.model.Student;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.UUID;
import java.util.logging.Logger;

public class ActivationLinkGeneratorDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(ActivationLinkGeneratorDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // get student from database table by studentID from process variables
        long studentID = (Long) execution.getVariable(ProcessConstants.VAR_STUDENT_ID);
        StudentTableEntry studentTableEntry = StudentTable.getInstance().search(studentID);
        Student student = studentTableEntry.getStudent();

        ActivationLinkTable activationLinkTable = ActivationLinkTable.getInstance();

        String uuid = UUID.randomUUID().toString();
        activationLinkTable.save(new ActivationLinkTableEntry(uuid, student));
        new ActivationLinkProcessVariables(execution).setActivationLink(uuid);
    }
}
