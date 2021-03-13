package net.lwenstrom.util;

import net.lwenstrom.RejectionProcessVariables;
import net.lwenstrom.StudentProcessVariables;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CheckDataDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        StudentProcessVariables student = new StudentProcessVariables(execution);

        RejectionProcessVariables rm = new RejectionProcessVariables(execution);
        rm.setFirstDataValidation(false);
        rm.setRejectionMessage("LMAO Wrong");
    }
}
