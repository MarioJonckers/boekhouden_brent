package be.vermolen.boekhouden.controller;

import be.vermolen.boekhouden.model.document.Invoice;
import be.vermolen.boekhouden.model.dto.CreateInvoiceDto;
import be.vermolen.boekhouden.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/document")
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/invoices")
    public List<Invoice> getAllInvoices() {
        return documentService.getAllInvoices();
    }

    @GetMapping("/invoices/{id}")
    public Invoice getInvoice(@PathVariable String id) {
        return documentService.getInvoice(Long.valueOf(id.replace("F-", "")));
    }

    @PutMapping("/invoices")
    public Invoice updateInvoice(@RequestBody CreateInvoiceDto invoice) {
        return documentService.updateInvoice(invoice);
    }

    @PostMapping("/invoices")
    public Invoice createInvoice(@RequestBody CreateInvoiceDto invoice) {
        return documentService.createInvoice(invoice);
    }
}
