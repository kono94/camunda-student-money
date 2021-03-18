package net.lwenstrom.flow;

import net.lwenstrom.LoggerDelegate;
import net.lwenstrom.ProcessConstants;
import net.lwenstrom.model.Student;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.logging.Logger;

/**
 * Abandon the "camunda way" of parsing raw strings to XML to java objects.
 * Instead of using the static import "org.camunda.spin.Spin.XML" and the form
 * "Student student = XML(studentRawXml).mapTo(Student.class)", we'll use
 * classic JAXB by adding two dependencies:
 * "jaxb-api" from "java.xml.bin"
 * and
 * "jaxb-runtime" from "org.glassfish.jaxb"
 *
 *
 * Why:
 * We use the soap-http-connector in the modeler as some sort of inline "front-end-ish" call
 * that will parse the SOAP-Response (the student data) as XML-String to the output variable
 * defined in ProcessConstants.VAR_STUDENT_RAW_XML.
 * This ExecutionListener is called after the service task with the soap-connector hits the
 * lifecycle step "end", pulls the raw XML string from the process variable and parses it as
 * java object to store it in a process variable again. In the past, all fields of the student
 * got saved in separate process variables but that is bad practices. As mentioned in the camunda
 * documentation it is suggested to use as few process variables as possible and whenever possible
 * to use reference IDs to certain objects. In this case the ID is the reference to the student object
 * itself.
 * Furthermore it reduces the amount of separate js-scripts which are extracting single values
 * from the SOAP response ("parseBirthday.js", "parseIBAN.js" etc.). External scripts may only return
 * one single value that get saved up in a process variable.
 *
 * (Would have been even better to call the SOAP-WebService within a Java-Delegate and parse
 * the XML result directly but we have to use all sorts of scripts and delegate mechanisms)
 * (See the logs from the LOGGER in docker container by using "docker logs camunda" or
 * in the container itself (docker exec -it camunda bash -> logs/catalina.2021-03-XX.log)
 */
public class StudentDataXmlParser implements ExecutionListener {
    private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

    @Override
    public void notify(DelegateExecution execution) throws Exception {
       String studentRawXml = execution.getVariable(ProcessConstants.VAR_STUDENT_RAW_XML).toString();
       LOGGER.info(studentRawXml);

       JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
       Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
       Student student = (Student) jaxbUnmarshaller.unmarshal(new StringReader(studentRawXml));

       LOGGER.warning(student.getIban());
       execution.setVariable(ProcessConstants.VAR_STUDENT, student);
    }
}
