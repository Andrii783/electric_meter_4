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
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ReportGenerator {
    public ByteArrayOutputStream generateReport(int meterNumber, int currentReading, int previousReading) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
       // PDFont font = PDType0Font.load(document, new File("../src/main/resources/CaviarDreams.ttf"));
        InputStream inputStream = this.getClass().getResourceAsStream("/CaviarDreams.ttf");
        PDFont font = PDType0Font.load(document, inputStream);

        contentStream.setFont(font, 14);

        // Добавляем текст в документ
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 700);
        contentStream.showText("Звіт про покази засобів обліку");
        contentStream.newLineAtOffset(0, -20);
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM yyyy 'р.'"));
        contentStream.showText("за розрахунковий період: " + formattedDate);
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Споживач: " + meterNumber);
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

        // Добавляем таблицу
        float margin = 230;
        float yStart = page.getMediaBox().getHeight() - margin;
        float tableWidth = page.getMediaBox().getWidth() - margin;
        float yPosition = yStart;
        int rows = 5;
        int cols = 2;
        float rowHeight = 20;
        float tableHeight = rowHeight * rows;
        float cellMargin = 2f;
        float marginX = (page.getMediaBox().getWidth() - tableWidth) / 2;

        contentStream.setLineWidth(1f);
        contentStream.moveTo(marginX, yStart);
        contentStream.lineTo(marginX + tableWidth, yStart);
        contentStream.stroke();

        // Рисуем строки таблицы
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(marginX, yStart - i * rowHeight);
            contentStream.lineTo(marginX + tableWidth, yStart - i * rowHeight);
            contentStream.stroke();
        }

        // Рисуем столбцы таблицы
        for (int i = 0; i <= cols; i++) {
            contentStream.moveTo(marginX + i * tableWidth / cols, yStart);
            contentStream.lineTo(marginX + i * tableWidth / cols, yStart - tableHeight);
            contentStream.stroke();
        }

        // Заполняем ячейки таблицы данными
        float yPositionText = yStart - cellMargin - rowHeight;
        contentStream.beginText();


        String[][] content = {
                {"Заголовок 1", "Значение 1"},
                {"Заголовок 2", "Значение 2"},
                {"Заголовок 3", "Значение 3"},
                {"Заголовок 4", "Значение 4"},
                {"Заголовок 5", "Значение 5"}
        };

//        for (int i = 0; i < rows; i++) {
//            contentStream.newLineAtOffset(cellMargin, -rowHeight);
//            for (int j = 0; j < cols; j++) {
//                contentStream.showText(content[i][j]);
//                contentStream.newLineAtOffset(tableWidth / cols, 0);
//            }
//        }

        contentStream.newLineAtOffset(marginX + cellMargin+10, yPositionText+7);
        for (int i=0; i<content.length;i++){
            for (int j = 0; j < content[0].length; j++) {
                contentStream.showText(content[i][j]);
                contentStream.newLineAtOffset(190, 0);
            }
            contentStream.newLineAtOffset(-380, -20);
        }
        contentStream.endText();
        contentStream.close();
        document.save(outputStream);
        document.close();

        return outputStream;
    }
}

//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDFont;
//import org.apache.pdfbox.pdmodel.font.PDType0Font;
//import org.springframework.stereotype.Service;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//@Service
//public class ReportGenerator {
//    public static ByteArrayOutputStream generateReport(int meterNumber, int currentReading, int previousReading) throws IOException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        PDDocument document = new PDDocument();
//        PDPage page = new PDPage();
//        document.addPage(page);
//
//        PDPageContentStream contentStream = new PDPageContentStream(document, page);
//        PDFont font = PDType0Font.load(document, new File("src/main/resources/CaviarDreams.ttf"));
//        contentStream.setFont(font, 30);
//
//        contentStream.beginText();
//
//
//        contentStream.newLineAtOffset(275, 750);
//        contentStream.showText("Звіт про покази засобів обліку");
//        contentStream.setFont(font, 14);
//        contentStream.newLineAtOffset(-225, -40);
//        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("'за розрахунковий період 'MMMM yyyy 'рік'"));
//        contentStream.showText("Дата: " + formattedDate);
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Номер лічильника: " + meterNumber);
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Попередній: " + previousReading);
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Теперішній: " + currentReading);
//        contentStream.newLineAtOffset(0, -20);
//
//        int result = currentReading - previousReading;
//        contentStream.showText("Результат: " + result);
//
//        contentStream.endText();
//        contentStream.close();
//
//        document.save(outputStream);
//        document.close();
//
//        return outputStream;
//   }
//}
