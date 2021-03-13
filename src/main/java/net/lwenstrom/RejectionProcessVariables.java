package net.lwenstrom;

import org.camunda.bpm.engine.delegate.VariableScope;

public class RejectionProcessVariables {
    private VariableScope variableScope;

    public RejectionProcessVariables(VariableScope variableScope){
        this.variableScope = variableScope;
    }

    public String getRejectionMessage(){
        return (String) variableScope.getVariable(ProcessConstants.PROCESS_REJECTION_MESSAGE);
    }

    public void setRejectionMessage(String rejectionMessage){
        variableScope.setVariable(ProcessConstants.PROCESS_REJECTION_MESSAGE, rejectionMessage);
    }

    public void setFirstDataValidation(boolean approved){
        variableScope.setVariable(ProcessConstants.PROCESS_DATA_VALIDATION_APPROVED, approved);
    }
}
