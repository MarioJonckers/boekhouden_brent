package be.vermolen.boekhouden.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "expense")
public class Expense implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    private LocalDate payOn;
    private LocalDate nextOccurrence;

    private double price;
    private double amount;

    @Enumerated(EnumType.STRING)
    private Recurrence recurrence;

    @JsonGetter
    public LocalDate getNextOccurrence() {
        if (expenseType == ExpenseType.RECURRENT) {
            LocalDate nextOccurrence = null;

            switch (recurrence) {
                case WEEKLY:
                    nextOccurrence = payOn.plusWeeks(1);
                    break;
                case MONTHLY:
                    nextOccurrence = payOn.plusMonths(1);
                    break;
                case TRIMESTERIAL:
                    nextOccurrence = payOn.plusMonths(3);
                    break;
                case QUARTERLY:
                    nextOccurrence = payOn.plusMonths(4);
                    break;
                case SEMESTER:
                    nextOccurrence = payOn.plusMonths(6);
                    break;
                case YEARLY:
                    nextOccurrence = payOn.plusYears(1);
                    break;
            }

            return nextOccurrence;
        } else {
            return null;
        }
    }

    @JsonSetter
    public void setPayOn(LocalDate payOn) {
        this.payOn = payOn;
        this.nextOccurrence = getNextOccurrence();
    }
}
