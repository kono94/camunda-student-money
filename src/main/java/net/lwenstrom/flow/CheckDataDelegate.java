package net.lwenstrom.flow;

import net.lwenstrom.ProcessConstants;
import net.lwenstrom.model.RejectionProcessVariables;
import net.lwenstrom.model.Student;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CheckDataDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Student student = (Student) execution.getVariable(ProcessConstants.VAR_STUDENT);


        RejectionProcessVariables rm = new RejectionProcessVariables(execution);
        rm.setFirstDataValidation(false);
        rm.setRejectionMessage("LMAO Wrong");
    }
}
