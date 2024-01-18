package palko.electric_meter_4.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ReportGenerator {
    public static ByteArrayOutputStream generateReport(int meterNumber, int currentReading, int previousReading) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDFont font = PDType0Font.load(document, new File("../CaviarDreams.ttf"));
        contentStream.setFont(font, 30);

        contentStream.beginText();


        contentStream.newLineAtOffset(275, 750);
        contentStream.showText("Звіт");
        contentStream.setFont(font, 14);
        contentStream.newLineAtOffset(-225, -40);
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy 'рік'"));
        contentStream.showText("Дата: " + formattedDate);
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Номер лічильника: " + meterNumber);
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Попередній: " + previousReading);
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Теперішній: " + currentReading);
        contentStream.newLineAtOffset(0, -20);

        int result = currentReading - previousReading;
        contentStream.showText("Результат: " + result);

        contentStream.endText();
        contentStream.close();

        document.save(outputStream);
        document.close();

        return outputStream;
   }
}
