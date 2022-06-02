package be.vermolen.boekhouden.service;

import be.vermolen.boekhouden.exception.InvoiceNotFoundException;
import be.vermolen.boekhouden.exception.UpdateException;
import be.vermolen.boekhouden.model.PaymentMethod;
import be.vermolen.boekhouden.model.document.Invoice;
import be.vermolen.boekhouden.model.dto.CreateInvoiceDto;
import be.vermolen.boekhouden.model.dto.LineDto;
import be.vermolen.boekhouden.model.line.ArticleLine;
import be.vermolen.boekhouden.model.line.Line;
import be.vermolen.boekhouden.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final InvoiceRepository invoiceRepository;
    private final ClientService clientService;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if (invoice == null) {
            throw new InvoiceNotFoundException(id);
        }
        return invoice;
    }

    public Invoice createInvoice(CreateInvoiceDto invoice) {
        return updateAndSaveInvoice(new Invoice(), invoice);
    }

    public Invoice updateInvoice(CreateInvoiceDto invoice) {
        Invoice original = getInvoice(invoice.getId());

        return updateAndSaveInvoice(original, invoice);
    }

    private Invoice updateAndSaveInvoice(Invoice original, CreateInvoiceDto invoice) {
        original.setDocDate(invoice.getDocDate());

        try {
            original.setPaymentMethod(PaymentMethod.compare(invoice.getPaymentMethod()));
        } catch (IllegalArgumentException e) {
            throw new UpdateException("factuur", "Betaalmethode '" + invoice.getPaymentMethod() + "' bestaat niet.");
        } catch (NullPointerException e) {
            throw new UpdateException("factuur", "Betaalmethode mag niet leeg zijn.");
        }

        if (original.getPaymentMethod().getDays() > 0) {
            original.setExpireDate(
                    DateUtils.addDays(
                            original.getDocDate(), original.getPaymentMethod().getDays()
                    )
            );
        } else {
            original.setExpireDate(null);
        }

        if (invoice.getClient() == null) {
            throw new UpdateException("factuur", "De factuur moet een klant hebben.");
        }
        original.setClient(clientService.get(invoice.getClient().getId()));

        if (invoice.getLines() == null || invoice.getLines().size() == 0) {
            throw new UpdateException("factuur", "De factuur moet minstens één lijn bevatten.");
        }

        original.setNotes(invoice.getNotes());

        original = invoiceRepository.save(original);

        ArrayList<Line> lines = new ArrayList<>();
        for (LineDto lineDto : invoice.getLines()) {
            if (lineDto.getArticle() == null) {
                Line line = new Line();
                line.setId(lineDto.getId());
                line.setDocument(original);
                line.setCustomArticleDescription(lineDto.getCustomArticleDescription());
                line.setOrderInDocument(lineDto.getOrderInDocument());
                lines.add(line);
            } else {
                ArticleLine line = new ArticleLine();
                line.setId(lineDto.getId());
                line.setDocument(original);
                line.setCustomArticleDescription(lineDto.getCustomArticleDescription());
                line.setOrderInDocument(lineDto.getOrderInDocument());
                line.setArticle(lineDto.getArticle());
                line.setCustomArticlePrice(lineDto.getCustomArticlePrice());
                line.setAmount(lineDto.getAmount());
                line.setDiscountPercentage(lineDto.getDiscountPercentage());
                lines.add(line);
            }
        }
        original.setLines(lines);

        return invoiceRepository.save(original);
    }
}
