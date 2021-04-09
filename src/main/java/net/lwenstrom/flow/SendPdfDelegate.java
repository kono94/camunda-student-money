package net.lwenstrom.flow;

import net.lwenstrom.ProcessConstants;
import net.lwenstrom.mock.EmailService;
import net.lwenstrom.mock.StudentTable;
import net.lwenstrom.mock.StudentTableEntry;
import net.lwenstrom.model.Student;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.FileValue;

import java.util.logging.Logger;

public class SendPdfDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(SendPdfDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        long studentID = (Long) execution.getVariable(ProcessConstants.VAR_STUDENT_ID);
        StudentTableEntry studentTableEntry = StudentTable.getInstance().search(studentID);
        Student student = studentTableEntry.getStudent();

        String receipent = student.getName().substring(0,1).toLowerCase() + student.getSurname().toLowerCase() + "@studenten.hs-bremerhaven.de";

        FileValue pdf = execution.getVariableTyped(ProcessConstants.VAR_REQUEST_PDF);
        EmailService emailService = new EmailService();
        emailService.sendMailWithAttachment(receipent, generateEmailBody(student), pdf.getValue());
        LOGGER.info("Sende PDF als Email");
    }

    private String generateEmailBody(Student student){
        StringBuilder sb = new StringBuilder();
        sb.append("Moin Herr/Frau ").append(student.getSurname()).append(", <br>")
                .append("Sie haben den Aktivierungslink bestätigt und erhalten nun den Antrag ")
                .append("auf Begrüßungsgeld per PDF. Dieser befindet sich im Anhang.<br>")
                .append("Bitte drucken Sie diesen Antrag aus und bringen ihn unterschrieben ")
                .append("zum AStA Büro in Haus K.<br><br>")
                .append("Für den Fall, dass sie keinen Drucker besitzen können Sie auch direkt zum ")
                .append("AStA-Büro. Dort wird der Antrag für Sie ausgedruckt und Sie können direkt vor Ort ")
                .append("unterschreiben.<br><br><br>")
                .append("Viele Grüße,<br> Ihr AStA der Hochschule Bremerhaven");
        return sb.toString();
    }
}
