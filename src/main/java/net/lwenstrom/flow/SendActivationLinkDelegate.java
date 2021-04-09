package net.lwenstrom.flow;

import net.lwenstrom.ProcessConstants;
import net.lwenstrom.mock.EmailService;
import net.lwenstrom.mock.StudentTable;
import net.lwenstrom.mock.StudentTableEntry;
import net.lwenstrom.model.ActivationLinkProcessVariables;
import net.lwenstrom.model.Student;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;


public class SendActivationLinkDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(SendActivationLinkDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // get student from database table by studentID from process variables
        long studentID = (Long) execution.getVariable(ProcessConstants.VAR_STUDENT_ID);
        StudentTableEntry studentTableEntry = StudentTable.getInstance().search(studentID);
        Student student = studentTableEntry.getStudent();
        Date requestDateTime = studentTableEntry.getRequestDateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

        String uuid = new ActivationLinkProcessVariables(execution).getActivationLink();
        String receipent = student.getName().substring(0,1).toLowerCase() + student.getSurname().toLowerCase() + "@studenten.hs-bremerhaven.de";
        EmailService emailService = new EmailService();
        StringBuilder sb = new StringBuilder();
        sb.append("Moin Herr/ Frau ");
        sb.append(student.getSurname()).append(", <br>");
        sb.append("Sie haben einen Antrag zum Erhalt des Begrueßungsgeld am <b>").append(sdf.format(requestDateTime)).append("</b> gestellt. \n");
        sb.append("<br>Bitte bestaetigen Sie ihren Antrag indem Sie auf folgenen Link klicken: <br><br>");
        sb.append("<a href=\"http://localhost:8089/api/activationLink?uuid=").append(uuid).append("\" target=\"_blank\">");
        sb.append("AKTIVIERUNGSLINK</a><br><br>");
        sb.append("Ihr AStA-Team");
        emailService.sendMail(receipent, sb.toString());
        LOGGER.info("E-Mail wurde gesendet an " + receipent + " (executionID="  + execution.getId() +  ") Payload: \n\n" + sb.toString());
    }
}
