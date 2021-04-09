package net.lwenstrom.util;

import net.lwenstrom.model.Student;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class PdfGenerator {

    private PDDocument document;
    private PDPageContentStream contentStream;

    public PdfGenerator() throws IOException {
        document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        contentStream = new PDPageContentStream(document, page);
    }

    public PDDocument generate(Student student) throws IOException {
        defaultFont();
        contentStream.setLeading(14.5f);
        contentStream.beginText();
        contentStream.newLineAtOffset(40, 750);
        generateHeader();
        contentStream.newLineAtOffset(0, -100);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
        text("Begrüßungsgeldantrag");
        defaultFont();
        contentStream.newLineAtOffset(0, -30);
        generateIntroduction();

        contentStream.newLineAtOffset(0, -60);
        generateInformation(student);

        contentStream.newLineAtOffset(0, -200);
        contentStream.endText();
        generateSigning();
        contentStream.close();
        return document;
    }
    private void generateHeader() throws IOException {
        text("AStA");
        newLine();
        text("Hochschule Bremerhaven");
        newLine();
        text("An der Karlstadt 8");
        newLine();
        text("27568 Bremerhaven");
    }

    private void generateIntroduction() throws IOException {
        text("Sie haben einen Begrüßungsgeldantrag gestellt. Bitte überprüfen Sie " +
                "die Korrektheit Ihrer Angaben.");
        newLine();
        text("Drucken Sie anschließend diesen Antrag aus " +
                "und bringen ihn ");
        bold();
        text("unterschrieben");
        defaultFont();
        text(" zum AStA Büro ");
        newLine();
        text("in Haus K!");
        newLine();
        newLine();
        text("Ihre Angaben:");
    }

    private void generateInformation(Student student) throws IOException {
        singleInfo("Name:", student.getName() + " " + student.getSurname());
        singleInfo("Geburtstag:" , student.getBirthday().toString());
        singleInfo("Addresse:", student.getStreet() + ", " + student.getPlz() + " " + student.getCity());
        singleInfo("IBAN:", student.getIban());
    }

    private void singleInfo(String label, String value) throws IOException {
        text(label);
        contentStream.newLineAtOffset(70, 0);
        text(value);
        newLine();
        contentStream.newLineAtOffset(-70, 0);

    }

    private void generateSigning() throws IOException {
        contentStream.setLineWidth(2);
        contentStream.moveTo(40,200);
        contentStream.lineTo(250, 200);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.newLineAtOffset(60, 185);
        text("Datum, Unterschrift Antragssteller");
        contentStream.endText();
    }

    private void text(String text) throws IOException {
        contentStream.showText(text);
    }

    private void newLine() throws IOException {
        contentStream.newLine();
    }
    private void defaultFont() throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA, 11);
    }
    private void bold() throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 11);
    }
}
