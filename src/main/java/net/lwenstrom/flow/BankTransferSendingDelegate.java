package net.lwenstrom.flow;

import net.lwenstrom.ProcessConstants;
import net.lwenstrom.mock.EmailService;
import net.lwenstrom.mock.StudentTable;
import net.lwenstrom.mock.StudentTableEntry;
import net.lwenstrom.model.BankTransfer;
import net.lwenstrom.model.Student;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

public class BankTransferSendingDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(BankTransferSendingDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("BankTransferSendingDelegate called");

        long studentID = (Long) execution.getVariable(ProcessConstants.VAR_STUDENT_ID);
        StudentTableEntry studentTableEntry = StudentTable.getInstance().search(studentID);
        Student student = studentTableEntry.getStudent();

        BankTransfer bankTransfer = (BankTransfer) execution.getVariable(ProcessConstants.VAR_BANK_TRANSFER);

        String receipent = student.getName().substring(0,1).toLowerCase() + student.getSurname().toLowerCase() + "@studenten.hs-bremerhaven.de";
        EmailService emailService = new EmailService();

        emailService.sendMail(receipent, bankTransfer.getXmlString());
        LOGGER.info("Sending " + bankTransfer.getXmlString());
    }
}
