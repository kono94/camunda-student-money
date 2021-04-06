package net.lwenstrom.model;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BankTransfer {

    private String xmlString;

    public BankTransfer() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        xmlString = convertDummyXmlToString();
        setCreditTimeToNow();
    }

    private String convertDummyXmlToString() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        ClassLoader classLoader = getClass().getClassLoader();
        File xmlFile = new File(classLoader.getResource("templates/DummyTransferDetails.xml").getFile());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }

    public void setCreditTimeToNow(){
        /*
            Replace key: $R_CreDtTm
            Example format: 2021-04-06T09:10:03
         */
        xmlString = xmlString.replace("$R_CreDtTm", LocalDateTime.now().withNano(0).toString());
    }

    public void setAmountOfMoney(double amount){
        /*
            Replace keys: $R_CtrSum (overall sum of all transaction (always one)),
                          $R_InstdAmt  (single transaction)

           Format: 150.00
         */

        xmlString = xmlString.replace("$R_CtrSum", String.format("%.2f", amount)).replace("$R_InstdAmt", String.format("%.2f", amount));
    }

    public void setExecutionDate(LocalDate day){
        /*
            Replace key: $R_ReqdExctnDt
            Format: 2021-04-06
         */

        xmlString = xmlString.replace("$R_ReqdExctnDt", day.toString());
    }

    public void setName(String name, String surname){
        /*
            Replace key: $R_Nm
            Format: Max Moller
         */
        xmlString = xmlString.replace("$R_Nm", name + " " + surname);
    }

    public void setReceiverIBAN(String iban){
        /*
            Replace key: $R_CdtTrfTxInf_IBAN
            Format: DE20250000000025101562 (no whitespaces!)
         */
        xmlString = xmlString.replace("$R_CdtTrfTxInf_IBAN", iban.replaceAll("\\s+",""));
    }

    public String getXmlString(){
        return xmlString;
    }
}
