package net.lwenstrom;

import org.camunda.bpm.engine.delegate.VariableScope;

import javax.xml.datatype.XMLGregorianCalendar;


public class StudentProcessVariables {
    private VariableScope variableScope;

    public StudentProcessVariables(VariableScope variableScope){
        this.variableScope = variableScope;
    }

    public String getName(){
        return (String) variableScope.getVariable(ProcessConstants.VAR_STUDENT_NAME);
    }

    public String getSurName(){
        return (String) variableScope.getVariable(ProcessConstants.VAR_STUDENT_SURNAME);
    }

    public long getStudentID(){
        return (long) variableScope.getVariable(ProcessConstants.VAR_STUDENT_ID);
    }


    public double getAvgScore(){
        return (double) variableScope.getVariable(ProcessConstants.VAR_STUDENT_AVG_SCORE);
    }

    public XMLGregorianCalendar getBirthday(){
        return (XMLGregorianCalendar) variableScope.getVariable(ProcessConstants.VAR_STUDENT_BIRTHDAY);
    }

    public String getIBAN(){
        return (String) variableScope.getVariable(ProcessConstants.VAR_STUDENT_IBAN);
    }

    public String getStreet(){
        return (String) variableScope.getVariable(ProcessConstants.VAR_STUDENT_STREET);
    }

    public int getPLZ(){
        return (int) variableScope.getVariable(ProcessConstants.VAR_STUDENT_PLZ);
    }

    public String getCity(){
        return (String) variableScope.getVariable(ProcessConstants.VAR_STUDENT_CITY);
    }
}
