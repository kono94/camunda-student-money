package net.lwenstrom.flow;

import net.lwenstrom.ProcessConstants;
import net.lwenstrom.mock.StudentTable;
import net.lwenstrom.mock.StudentTableEntry;
import net.lwenstrom.model.Student;
import net.lwenstrom.util.PdfGenerator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.FileValue;

import java.io.File;
import java.util.logging.Logger;

public class PdfGenerationDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(PdfGenerationDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        long studentID = (Long) execution.getVariable(ProcessConstants.VAR_STUDENT_ID);
        StudentTableEntry studentTableEntry = StudentTable.getInstance().search(studentID);
        Student student = studentTableEntry.getStudent();

        PdfGenerator pdfGenerator = new PdfGenerator();
        PDDocument pdf = pdfGenerator.generate(student);
        LOGGER.info("PDF wurde erstellt (executionID=" + execution.getId() + ")");


        final File file = File.createTempFile("tmpRequest", ".pdf");
        pdf.save(file);
        FileValue typedFileValue = Variables
                .fileValue("addresses.txt")
                .file(file)
                .mimeType("application/pdf")
                .encoding("UTF-8")
                .create();
        pdf.close();
        execution.setVariable(ProcessConstants.VAR_REQUEST_PDF, typedFileValue);
    }
}
