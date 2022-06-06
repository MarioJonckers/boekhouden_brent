package be.vermolen.boekhouden.repository;

import be.vermolen.boekhouden.model.Expense;
import be.vermolen.boekhouden.model.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByExpenseTypeAndNextOccurrenceIs(ExpenseType expenseType, LocalDate date);
}
