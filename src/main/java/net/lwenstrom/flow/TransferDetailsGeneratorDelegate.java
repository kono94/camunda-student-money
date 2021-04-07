package net.lwenstrom.flow;

import net.lwenstrom.ProcessConstants;
import net.lwenstrom.mock.StudentTable;
import net.lwenstrom.mock.StudentTableEntry;
import net.lwenstrom.model.BankTransfer;
import net.lwenstrom.model.Student;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.time.LocalDate;
import java.util.logging.Logger;

public class TransferDetailsGeneratorDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(TransferDetailsGeneratorDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        long studentID = (Long) execution.getVariable(ProcessConstants.VAR_STUDENT_ID);
        StudentTableEntry studentTableEntry = StudentTable.getInstance().search(studentID);
        Student student = studentTableEntry.getStudent();

        BankTransfer bankTransfer = new BankTransfer();
        bankTransfer.setAmountOfMoney(student.getAmount());
        // set day of execution to tomorrow
        bankTransfer.setExecutionDate(LocalDate.now().plusDays(1));
        bankTransfer.setName(student.getName(), student.getSurname());
        bankTransfer.setReceiverIBAN(student.getIban());

        execution.setVariable(ProcessConstants.VAR_BANK_TRANSFER, bankTransfer);
    }
}
