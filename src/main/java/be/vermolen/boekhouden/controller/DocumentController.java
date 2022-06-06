package be.vermolen.boekhouden.controller;

import be.vermolen.boekhouden.model.document.Invoice;
import be.vermolen.boekhouden.model.dto.CreateInvoiceDto;
import be.vermolen.boekhouden.service.DocumentService;
import be.vermolen.boekhouden.service.PrintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/document")
public class DocumentController {

    private final DocumentService documentService;
    private final PrintService printService;

    @GetMapping("/invoices")
    public List<Invoice> getAllInvoices() {
        return documentService.getAllInvoices();
    }

    @GetMapping("/invoices/{id}")
    public Invoice getInvoice(@PathVariable String id) {
        return documentService.getInvoice(Long.valueOf(id.substring(6)));
    }

    @PutMapping("/invoices")
    public Invoice updateInvoice(@RequestBody CreateInvoiceDto invoice) {
        return documentService.updateInvoice(invoice);
    }

    @PostMapping("/invoices")
    public Invoice createInvoice(@RequestBody CreateInvoiceDto invoice) {
        return documentService.createInvoice(invoice);
    }

    @GetMapping("/invoices/download/{id}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable String id) {
        Invoice invoice = documentService.getInvoice(Long.valueOf(id.substring(6)));
        ByteArrayOutputStream byteArr = printService.downloadInvoice(invoice);

        return ResponseEntity
                .ok()
                .header("content-disposition", "attachment; filename=" + id + ".pdf")
                .body(byteArr.toByteArray());
    }

    @PutMapping("/invoices/togglePaid/{id}")
    public void togglePaid(@PathVariable String id) {
        documentService.togglePaid(Long.valueOf(id.substring(6)));
    }
}
