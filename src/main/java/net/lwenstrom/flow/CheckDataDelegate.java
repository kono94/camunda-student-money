package net.lwenstrom.flow;

import net.lwenstrom.ProcessConstants;
import net.lwenstrom.mock.StudentTable;
import net.lwenstrom.mock.StudentTableEntry;
import net.lwenstrom.model.RejectionProcessVariables;
import net.lwenstrom.model.Student;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


import java.util.Arrays;
import java.util.List;

public class CheckDataDelegate implements JavaDelegate {

    private List<Integer> validPLZs;

    public CheckDataDelegate() {
        // valid post numbers from Bremerhaven and Bremen (selection)
        validPLZs = Arrays.asList(27570, 27572, 27574, 27568, 27576, 27578, 27580, 28195, 28215, 28197, 28217, 28237);
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // get student from database table by studentID from process variables
        long studentID = (Long) execution.getVariable(ProcessConstants.VAR_STUDENT_ID);
        StudentTableEntry studentTableEntry = StudentTable.getInstance().search(studentID);
        Student student = studentTableEntry.getStudent();


        RejectionProcessVariables rm = new RejectionProcessVariables(execution);
        try {
            checkPlz(student.getPlz());
            checkIBAN(student.getIban());
        } catch (IllegalArgumentException e) {
            rm.setFirstDataValidation(false);
            rm.setRejectionMessage(e.getMessage());
        }
        rm.setFirstDataValidation(true);
    }

    private void checkPlz(int plz) throws IllegalArgumentException {
        if (!validPLZs.contains(plz)) throw new IllegalArgumentException("PLZ ausserhalb des erlaubten Bereiches.");
    }

    private void checkIBAN(String iban) throws IllegalArgumentException {
        if (iban == null) {
            throw new IllegalArgumentException("IBAN ist nicht angegeben");
        }
        if (!iban.substring(0, 2).equals("DE")) {
            throw new IllegalArgumentException("IBAN muss mit 'DE' beginnen");
        }
        if (iban.charAt(2) < '0' || iban.charAt(2) > '9' || iban.charAt(3) < '0' || iban.charAt(3) > '9') {
            throw new IllegalArgumentException("IBAN hat falsches Format");
        }

    }
}
