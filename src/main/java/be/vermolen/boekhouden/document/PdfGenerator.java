package be.vermolen.boekhouden.document;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfGenerator {

    Font defaultFont = new Font(Font.FontFamily.HELVETICA, 12);
    Font boldDefaultFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    Font headerFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    Font smallFont = new Font(Font.FontFamily.HELVETICA, 8);
    Font boldSmallFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

    PdfPTable table;
    Document document;

    void setSettings(Document document) {
        document.setMargins( 20,20, 30,30);
    }

    void addMetaData(Document document) {
        document.addAuthor("Vermolen Brent");
        document.addCreator("Vermolen Brent");
        document.addCreationDate();
    }

    void addEmptyLine(Paragraph paragraph, int number) {
        addEmptyLine(paragraph, number, this.defaultFont);
    }

    void addEmptyLine(Paragraph paragraph, int number, Font font) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" ", font));
        }
    }

    void addCellSpace(int height) {
        PdfPCell cellSpace = new PdfPCell();
        cellSpace.setBorder(0);
        cellSpace.setFixedHeight(height);
        cellSpace.setColspan(table.getNumberOfColumns());
        table.addCell(cellSpace);
    }
}
