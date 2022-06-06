package be.vermolen.boekhouden.document;

import be.vermolen.boekhouden.model.Article;
import be.vermolen.boekhouden.model.Client;
import be.vermolen.boekhouden.model.document.Invoice;
import be.vermolen.boekhouden.model.line.ArticleLine;
import be.vermolen.boekhouden.model.line.Line;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class InvoiceGenerator extends PdfGenerator {

    public ByteArrayOutputStream generatePdf(Invoice invoice) {
        createDocument();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);

            document.open();

            document.addTitle("Factuur " + invoice.getId());
            document.addSubject(invoice.getId());
            addMetaData(document);

            table = new PdfPTable(12);
            table.setWidthPercentage(100);

            addHeader();
            addClient(invoice.getClient());
            addGeneralInfoLine(invoice);

            addLinesHeader();
            invoice.getLines().sort(Comparator.comparingInt(Line::getOrderInDocument));
            for (int i = 0; i < invoice.getLines().size(); i++) {
                Line line = invoice.getLines().get(i);
                addLine(line, i == invoice.getLines().size() - 1);
            }

            addOverview(invoice);

            document.add(table);
            document.close();
            return byteArrayOutputStream;
        } catch (DocumentException e) {
            throw new IllegalArgumentException("Factuur kon niet worden afgedrukt.");
        }
    }

    private void addHeader() {
        PdfPCell header = new PdfPCell();
        header.setColspan(12);
        header.setBorder(0);

        Paragraph companyInfo = new Paragraph();

        companyInfo.add(new Paragraph("Vermolen-IT bv", headerFont));
        addEmptyLine(companyInfo, 1, smallFont);
        companyInfo.add(new Paragraph("Kweek 7", boldSmallFont));
        companyInfo.add(new Paragraph("2290 Vorselaar", boldSmallFont));
        companyInfo.add(new Paragraph("België", boldSmallFont));
        addEmptyLine(companyInfo, 1, smallFont);
        companyInfo.add(new Paragraph("GSM: 0476/79 83 46", boldSmallFont));
        companyInfo.add(new Paragraph("E-Mail: brentvermolen@hotmail.be", boldSmallFont));

        header.addElement(companyInfo);
        table.addCell(header);
    }

    private void addClient(Client client) {
        PdfPCell empty = new PdfPCell();
        empty.setColspan(7);
        empty.setBorder(0);
        PdfPCell clientInfo = new PdfPCell();
        clientInfo.setColspan(5);
        clientInfo.setBorder(0);

        PdfPTable clientInfoTable = new PdfPTable(1);
        clientInfoTable.setWidthPercentage(100);

        PdfPCell title = new PdfPCell();
        Paragraph factuur = new Paragraph(15, "Factuur", headerFont);
        factuur.setSpacingBefore(4);
        factuur.setSpacingAfter(4);
        title.addElement(factuur);
        title.setBackgroundColor(BaseColor.LIGHT_GRAY);

        PdfPCell clientInfoCell = new PdfPCell();
        Paragraph clientInfoPar = new Paragraph();
        clientInfoPar.add(new Paragraph(client.getName(), defaultFont));
        String address = client.getAddress();
        if (client.getAddress2() != null && !client.getAddress2().isBlank()) {
            address += ", " + client.getAddress2();
        }
        clientInfoPar.add(new Paragraph(address, defaultFont));

        clientInfoPar.add(new Paragraph(client.getCity().getPostalCode() + " " + client.getCity().getCity(), defaultFont));
        clientInfoPar.add(new Paragraph(client.getCity().getCountry().getName(), defaultFont));
        addEmptyLine(clientInfoPar, 1);

        clientInfoCell.addElement(clientInfoPar);

        clientInfoTable.addCell(title);
        clientInfoTable.addCell(clientInfoCell);

        clientInfo.addElement(clientInfoTable);
        table.addCell(empty);
        table.addCell(clientInfo);
    }

    private void addGeneralInfoLine(Invoice invoice) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        addCellSpace(20);

        PdfPCell clientCodeHeader = new PdfPCell();
        clientCodeHeader.setColspan(3);
        Paragraph klantcode = new Paragraph(10, "Klantcode", boldDefaultFont);
        klantcode.setAlignment(Element.ALIGN_CENTER);

        clientCodeHeader.addElement(klantcode);
        clientCodeHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
        PdfPCell docDateHeader = new PdfPCell();
        docDateHeader.setColspan(3);
        docDateHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        Paragraph factuurdatum = new Paragraph(10, "Factuurdatum", boldDefaultFont);
        factuurdatum.setAlignment(Element.ALIGN_CENTER);

        docDateHeader.addElement(factuurdatum);
        docDateHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
        PdfPCell expDateHeader = new PdfPCell();
        expDateHeader.setColspan(3);
        expDateHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        Paragraph vervaldatum = new Paragraph(10, "Vervaldatum", boldDefaultFont);
        vervaldatum.setAlignment(Element.ALIGN_CENTER);

        expDateHeader.addElement(vervaldatum);
        expDateHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
        PdfPCell idHeader = new PdfPCell();
        idHeader.setColspan(3);
        idHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        Paragraph nummer = new Paragraph(10, "Nummer", boldDefaultFont);
        nummer.setAlignment(Element.ALIGN_CENTER);

        idHeader.addElement(nummer);
        idHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);

        PdfPCell clientCodeCell = new PdfPCell();
        clientCodeCell.setColspan(3);
        Paragraph element = new Paragraph(10, invoice.getClient().getId() + "", defaultFont);
        element.setAlignment(Element.ALIGN_CENTER);

        clientCodeCell.addElement(element);
        PdfPCell docDateCell = new PdfPCell();
        docDateCell.setColspan(3);
        Paragraph element1 = new Paragraph(10, invoice.getDocDate().format(formatter), defaultFont);
        element1.setAlignment(Element.ALIGN_CENTER);

        docDateCell.addElement(element1);
        PdfPCell expDateCell = new PdfPCell();
        expDateCell.setColspan(3);
        Paragraph element2 = new Paragraph(10, invoice.getExpireDate().format(formatter), defaultFont);
        element2.setAlignment(Element.ALIGN_CENTER);

        expDateCell.addElement(element2);
        PdfPCell idCell = new PdfPCell();
        idCell.setColspan(3);
        Paragraph element3 = new Paragraph(10, invoice.getId(), defaultFont);
        element3.setAlignment(Element.ALIGN_CENTER);

        idCell.addElement(element3);

        table.addCell(clientCodeHeader);
        table.addCell(docDateHeader);
        table.addCell(expDateHeader);
        table.addCell(idHeader);

        table.addCell(clientCodeCell);
        table.addCell(docDateCell);
        table.addCell(expDateCell);
        table.addCell(idCell);
    }

    private void addLinesHeader() {
        addCellSpace(10);

        /*PdfPCell code = new PdfPCell();
        code.setColspan(2);
        code.setBackgroundColor(BaseColor.LIGHT_GRAY);
        Paragraph artCode = new Paragraph(10, "Artikelcode", boldDefaultFont);
        artCode.setAlignment(Element.ALIGN_CENTER);
        code.addElement(artCode);*/

        PdfPCell description = new PdfPCell();
        description.setColspan(4);
        description.setBackgroundColor(BaseColor.LIGHT_GRAY);
        Paragraph descriptionP = new Paragraph(10, "Omschrijving", boldDefaultFont);
        descriptionP.setAlignment(Element.ALIGN_CENTER);
        description.addElement(descriptionP);

        PdfPCell amount = new PdfPCell();
        amount.setColspan(2);
        amount.setBackgroundColor(BaseColor.LIGHT_GRAY);
        Paragraph amountP = new Paragraph(10, "Aantal", boldDefaultFont);
        amountP.setAlignment(Element.ALIGN_CENTER);
        amount.addElement(amountP);

        PdfPCell ehp = new PdfPCell();
        ehp.setColspan(2);
        ehp.setBackgroundColor(BaseColor.LIGHT_GRAY);
        Paragraph ehpP = new Paragraph(10, "EHP ex. btw", boldDefaultFont);
        ehpP.setAlignment(Element.ALIGN_CENTER);
        ehp.addElement(ehpP);

        PdfPCell discount = new PdfPCell();
        discount.setColspan(1);
        discount.setBackgroundColor(BaseColor.LIGHT_GRAY);
        Paragraph discountP = new Paragraph(10, "Kort %", boldDefaultFont);
        discountP.setAlignment(Element.ALIGN_CENTER);
        discount.addElement(discountP);

        PdfPCell exclBtw = new PdfPCell();
        exclBtw.setColspan(2);
        exclBtw.setBackgroundColor(BaseColor.LIGHT_GRAY);
        Paragraph exclBtwP = new Paragraph(10, "Bedrag ex. btw", boldDefaultFont);
        exclBtwP.setAlignment(Element.ALIGN_CENTER);
        exclBtw.addElement(exclBtwP);

        PdfPCell btw = new PdfPCell();
        btw.setColspan(1);
        btw.setBackgroundColor(BaseColor.LIGHT_GRAY);
        Paragraph btwP = new Paragraph(10, "BTW", boldDefaultFont);
        btwP.setAlignment(Element.ALIGN_CENTER);
        btw.addElement(btwP);

        //table.addCell(code);
        table.addCell(description);
        table.addCell(amount);
        table.addCell(ehp);
        table.addCell(discount);
        table.addCell(exclBtw);
        table.addCell(btw);
    }

    private void addLine(Line line, boolean isLast) {
        /*PdfPCell code = new PdfPCell();
        code.setColspan(2);
        code.setBackgroundColor(BaseColor.LIGHT_GRAY);
        Paragraph artCode = new Paragraph(10, "Artikelcode", boldDefaultFont);
        artCode.setAlignment(Element.ALIGN_CENTER);
        code.addElement(artCode);*/
        int border = isLast ? Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT : Rectangle.LEFT | Rectangle.RIGHT;

        PdfPCell description = new PdfPCell();
        description.setColspan(4);
        description.setBorder(border);
        description.setPaddingBottom(12);

        PdfPCell amount = new PdfPCell();
        amount.setColspan(2);
        amount.setBorder(border);
        amount.setPaddingBottom(12);

        PdfPCell ehp = new PdfPCell();
        ehp.setColspan(2);
        ehp.setBorder(border);
        ehp.setPaddingBottom(12);

        PdfPCell discount = new PdfPCell();
        discount.setColspan(1);
        discount.setBorder(border);
        discount.setPaddingBottom(12);

        PdfPCell exclBtw = new PdfPCell();
        exclBtw.setColspan(2);
        exclBtw.setBorder(border);
        exclBtw.setPaddingBottom(12);

        PdfPCell btw = new PdfPCell();
        btw.setColspan(1);
        btw.setBorder(border);
        btw.setPaddingBottom(12);

        Paragraph descriptionP = new Paragraph();
        if (line instanceof ArticleLine) {
            ArticleLine articleLine = (ArticleLine) line;
            descriptionP.add(new Paragraph(articleLine.getArticle().getName(), defaultFont));
            if (line.getCustomArticleDescription() != null && !line.getCustomArticleDescription().isBlank()) {
                descriptionP.add(new Paragraph(line.getCustomArticleDescription(), defaultFont));
            } else if (articleLine.getArticle().getDescription()!= null && !articleLine.getArticle().getDescription().isBlank()) {
                descriptionP.add(new Paragraph(articleLine.getArticle().getDescription(), defaultFont));
            }

            Paragraph amountP = new Paragraph(articleLine.getAmount() + " (" + articleLine.getArticle().getUnit().getName() + ")", defaultFont);
            amountP.setAlignment(Element.ALIGN_RIGHT);
            amount.addElement(amountP);

            double customPrice = articleLine.getCustomArticlePrice();
            double price = customPrice != 0 ? customPrice : articleLine.getArticle().getPrice();
            Paragraph ehpP = new Paragraph(getRoundedPrice(price), defaultFont);
            ehpP.setAlignment(Element.ALIGN_RIGHT);
            ehp.addElement(ehpP);

            if (articleLine.getDiscountPercentage() > 0) {
                Paragraph discountP = new Paragraph(articleLine.getDiscountPercentage() + " %", defaultFont);
                discountP.setAlignment(Element.ALIGN_RIGHT);
                discount.addElement(discountP);
            }

            Paragraph exclBtwP = new Paragraph(getRoundedPrice(getPriceExclBtw(articleLine)), defaultFont);
            exclBtwP.setAlignment(Element.ALIGN_RIGHT);
            exclBtw.addElement(exclBtwP);

            Paragraph btwP = new Paragraph(articleLine.getArticle().getBtwPercentage() + " %", defaultFont);
            btwP.setAlignment(Element.ALIGN_RIGHT);
            btw.addElement(btwP);
        } else {
            descriptionP.add(new Paragraph(line.getCustomArticleDescription(), defaultFont));
        }
        description.addElement(descriptionP);

        //table.addCell(code);
        table.addCell(description);
        table.addCell(amount);
        table.addCell(ehp);
        table.addCell(discount);
        table.addCell(exclBtw);
        table.addCell(btw);
    }

    private void addOverview(Invoice invoice) {
        addCellSpace(30);

        PdfPCell empty = new PdfPCell();
        empty.setColspan(7);
        empty.setBorder(0);

        PdfPCell overview = new PdfPCell();
        overview.setColspan(3);
        Paragraph overviewP = new Paragraph();
        overviewP.add(new Paragraph("Totaal ex. BTW", boldDefaultFont));
        overviewP.add(new Paragraph("Totaal BTW", boldDefaultFont));
        overview.addElement(overviewP);

        double subtotalExBtw = 0;
        double subtotalBtw = 0;
        for (Line line : invoice.getLines()) {
            ArticleLine articleLine = (ArticleLine) line;
            subtotalExBtw += getPriceExclBtw(articleLine);
            subtotalBtw += getPriceExclBtw(articleLine) * ((double)articleLine.getArticle().getBtwPercentage() / 100);
        }

        PdfPCell overviewNumber = new PdfPCell();
        overviewNumber.setColspan(2);
        overviewNumber.setPaddingBottom(12);
        Paragraph overviewNumberP = new Paragraph();
        Paragraph o = new Paragraph(getRoundedPrice(subtotalExBtw), defaultFont);
        Paragraph o1 = new Paragraph(getRoundedPrice(subtotalBtw), defaultFont);
        o.setAlignment(Element.ALIGN_RIGHT);
        o1.setAlignment(Element.ALIGN_RIGHT);
        overviewNumberP.add(o);
        overviewNumberP.add(o1);
        overviewNumber.addElement(overviewNumberP);

        table.addCell(empty);
        table.addCell(overview);
        table.addCell(overviewNumber);

        PdfPCell total = new PdfPCell();
        total.setColspan(3);
        total.setBackgroundColor(BaseColor.LIGHT_GRAY);
        Paragraph totalP = new Paragraph();
        totalP.add(new Paragraph("Totaal incl. BTW", titleFont));
        total.addElement(totalP);

        PdfPCell totalNumber = new PdfPCell();
        totalNumber.setColspan(2);
        totalNumber.setPaddingBottom(12);
        totalNumber.setBackgroundColor(BaseColor.LIGHT_GRAY);
        Paragraph totalNumberP = new Paragraph();
        Paragraph o2 = new Paragraph(getRoundedPrice(subtotalExBtw + subtotalBtw), titleFont);
        o2.setAlignment(Element.ALIGN_RIGHT);
        totalNumberP.add(o2);
        totalNumber.addElement(totalNumberP);

        table.addCell(empty);
        table.addCell(total);
        table.addCell(totalNumber);
    }

    private String getRoundedPrice(double price) {
        return String.format("€ %.2f", price);
    }

    private double getPriceExclBtw(ArticleLine articleLine) {
        double customPrice = articleLine.getCustomArticlePrice();
        double price = customPrice != 0 ? customPrice : articleLine.getArticle().getPrice();
        double subtotal = articleLine.getAmount() * price;
        if (articleLine.getDiscountPercentage() > 0) {
            subtotal *= (1 - ((double)articleLine.getDiscountPercentage() / 100));
        }

        return subtotal;
    }

    private void createDocument() {
        document = new Document();
        setSettings(document);
    }
}
