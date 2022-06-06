package be.vermolen.boekhouden.service;

import be.vermolen.boekhouden.model.Expense;
import be.vermolen.boekhouden.model.ExpenseType;
import be.vermolen.boekhouden.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Scheduled(cron = "0 0 2 * * ?", zone = "Europe/Brussels")
    public void checkRecurrentExpense() {
        List<Expense> expenses = expenseRepository.findAllByExpenseTypeAndNextOccurrenceIs(ExpenseType.RECURRENT, LocalDate.now());
        for (Expense expense : expenses) {
            Expense newExpense = new Expense();
            newExpense.setExpenseType(expense.getExpenseType());
            newExpense.setAmount(expense.getAmount());
            newExpense.setDescription(expense.getDescription());
            newExpense.setRecurrence(expense.getRecurrence());
            newExpense.setPayOn(expense.getNextOccurrence());

            expenseRepository.save(newExpense);
        }
    }
}
