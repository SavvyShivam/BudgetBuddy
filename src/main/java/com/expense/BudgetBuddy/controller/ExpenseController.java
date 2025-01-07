package com.expense.BudgetBuddy.controller;

import com.expense.BudgetBuddy.entity.Expense;
import com.expense.BudgetBuddy.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
public class ExpenseController {
    private ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(Pageable page){
       return expenseService.getAllExpenses(page).stream().toList();
    }

    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable("id") Long id){
        return expenseService.getExpenseById(id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseBuId(@RequestParam("id") Long id){
        expenseService.deleteExpenseById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public Expense saveExpenseDetails(@Valid @RequestBody Expense expense){
       return expenseService.saveExpenseDetails(expense);
    }

    @PutMapping("/expenses/{id}")
    public Expense updateExpenseDetails(@RequestBody Expense expense,@PathVariable Long id){
        return expenseService.updateExpenseDetails(id,expense);
    }

    @GetMapping("/expenses/category")
    public List<Expense> getExpenseByCategory(@RequestParam String category,Pageable page){
        return expenseService.readByCategory(category,page);
    }

    @GetMapping("/expenses/name")
    public List<Expense> getExpensesByName(@RequestParam String keyword,Pageable page){
        return  expenseService.readByName(keyword, page);
    }

    @GetMapping("/expenses/date")
    public List<Expense> getExpenseByDates(@RequestParam(required = false) Date startDate,
                                           @RequestParam(required = false) Date endDate, Pageable page){
        return expenseService.readByDate(startDate,endDate,page);
    }

}
