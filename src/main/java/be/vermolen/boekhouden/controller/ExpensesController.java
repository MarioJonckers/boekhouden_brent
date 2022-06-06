package be.vermolen.boekhouden.controller;

import be.vermolen.boekhouden.model.Expense;
import be.vermolen.boekhouden.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses")
public class ExpensesController {

    private final ExpenseService expenseService;

    @GetMapping
    public List<Expense> getAll() {
        expenseService.checkRecurrentExpense();
        return null;
    }
}
