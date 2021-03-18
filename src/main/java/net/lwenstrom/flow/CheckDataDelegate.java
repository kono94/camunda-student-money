package net.lwenstrom.flow;

import net.lwenstrom.ProcessConstants;
import net.lwenstrom.model.RejectionProcessVariables;
import net.lwenstrom.model.Student;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.List;

public class CheckDataDelegate implements JavaDelegate {

    private List<Integer> validPLZs;

    public CheckDataDelegate(){
        // valid post numbers from Bremerhaven and Bremen (selection)
        validPLZs = Arrays.asList(27570, 27572, 27574, 27568, 27576, 27578, 27580, 28195, 28215, 28197, 28217, 28237);
    }
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Student student = (Student) execution.getVariable(ProcessConstants.VAR_STUDENT);


        RejectionProcessVariables rm = new RejectionProcessVariables(execution);
        try{
            checkPlz(student.getPlz());
            checkIBAN(student.getIban());
        }catch (IllegalFormatException e){
            rm.setFirstDataValidation(false);
            rm.setRejectionMessage(e.getMessage());
        }

        rm.setFirstDataValidation(true);
    }

    private boolean checkPlz(int plz) throws IllegalFormatException {
            return validPLZs.contains(plz);
    }

    private boolean checkIBAN(String iban) throws IllegalFormatException{
        return true;
    }
}
