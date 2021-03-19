package net.lwenstrom.model;

import net.lwenstrom.ProcessConstants;
import org.camunda.bpm.engine.delegate.VariableScope;

public class ActivationLinkProcessVariables {
    private VariableScope variableScope;

    public ActivationLinkProcessVariables(VariableScope variableScope){
        this.variableScope = variableScope;
    }

    public String getActivationLink(){
        return (String) variableScope.getVariable(ProcessConstants.VAR_ACTIVATION_LINK);
    }

    public void setActivationLink(String activationLink){
        variableScope.setVariable(ProcessConstants.VAR_ACTIVATION_LINK, activationLink);
    }
}
