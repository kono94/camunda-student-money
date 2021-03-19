package net.lwenstrom.flow;

import net.lwenstrom.ProcessConstants;
import net.lwenstrom.mock.ActivationLinkTable;
import net.lwenstrom.mock.ActivationLinkTableEntry;
import net.lwenstrom.model.ActivationLinkProcessVariables;
import net.lwenstrom.model.Student;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.UUID;

public class ActivationLinkGeneratorDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Student student = (Student) execution.getVariable(ProcessConstants.VAR_STUDENT);
        ActivationLinkTable activationLinkTable = ActivationLinkTable.getInstance();

        String uuid = UUID.randomUUID().toString();
        activationLinkTable.save(new ActivationLinkTableEntry(uuid, student));
        new ActivationLinkProcessVariables(execution).setActivationLink(uuid);
    }
}
