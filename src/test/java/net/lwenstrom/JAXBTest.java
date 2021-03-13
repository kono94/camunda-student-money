package net.lwenstrom;

import net.lwenstrom.model.Student;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.StringReader;
import java.time.LocalDate;

public class JAXBTest {

    @Test
    public void testXMLParser(){
        JAXBContext jaxbContext = null;
        try {
            String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns2:student xmlns:ns2=\"lwenstrom.net/ws/students\"><ns2:name>Jan</ns2:name></ns2:student>";
            String rawXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns2:student xmlns:ns2=\"lwenstrom.net/ws/students\">\n" +
                    "  <ns2:name>Jan</ns2:name>\n" +
                    "  <ns2:surname>Löwenstrom</ns2:surname>\n" +
                    "  <ns2:studentID>34937</ns2:studentID>\n" +
                    "  <ns2:avgScore>1.18</ns2:avgScore>\n" +
                    "  <ns2:birthday>1994-01-25</ns2:birthday>\n" +
                    "  <ns2:iban>DE59 1000 000 0323 1022</ns2:iban>\n" +
                    "  <ns2:street>Mundsburger Straße 233</ns2:street>\n" +
                    "  <ns2:plz>24754</ns2:plz>\n" +
                    "  <ns2:city>Hamburg</ns2:city>\n" +
                    "</ns2:student>\n";
            jaxbContext = JAXBContext.newInstance(Student.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Student student = (Student) jaxbUnmarshaller.unmarshal(new StringReader(test));
            assert (student != null) : "Student null";
            assert (student.getName() != null) : "Field parsing null";
            assert (student.getName().equals("Jan")): "Invalid Field value for 'name'";

            Student student2 = (Student) jaxbUnmarshaller.unmarshal(new StringReader(rawXML));
            assert (student2 != null) : "Student2 null";
            assert (student2.getName().equals("Jan")): "Invalid Field value for 'name'";
            assert (student2.getSurname().equals("Löwenstrom")): "Invalid Field value for 'surname'";
            assert (student2.getStudentID()==34937L): "Invalid Field value for 'studentID'";
            assert (student2.getAvgScore() == 1.18): "Invalid Field value for 'avgScore'";
            assert (student2.getBirthday().equals(
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDate.of(1994, 1, 25).toString())
            )): "Invalid Field value for 'birthday'";
            assert (student2.getIban().equals("DE59 1000 000 0323 1022")): "Invalid Field value for 'iban'";
            assert (student2.getStreet().equals("Mundsburger Straße 233")): "Invalid Field value for 'street'";
            assert (student2.getPlz() == 24754): "Invalid Field value for 'plz'";
            assert (student2.getCity().equals("Hamburg")): "Invalid Field value for 'city'";
        } catch (JAXBException | DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }
}
