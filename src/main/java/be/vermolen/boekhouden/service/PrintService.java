package be.vermolen.boekhouden.service;

import be.vermolen.boekhouden.document.InvoiceGenerator;
import be.vermolen.boekhouden.model.document.Invoice;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PrintService {

    public ByteArrayOutputStream downloadInvoice(Invoice invoice) {
        return new InvoiceGenerator().generatePdf(invoice);
    }
}
