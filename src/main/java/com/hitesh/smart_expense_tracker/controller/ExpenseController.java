package com.hitesh.smart_expense_tracker.controller;

import com.hitesh.smart_expense_tracker.model.Expense;
import com.hitesh.smart_expense_tracker.service.ExpenseRepository;

import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {
    
    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping
    public String getAllExpenses(Model model) {
        model.addAttribute("expenses", expenseRepository.getAllExpenses());
        return "expense-list";
    }

    @GetMapping("/add-expense")
    public String showAddForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "add-expense";
    }

    @PostMapping("/add-expense")
    public String addExpense(@ModelAttribute("expense") @Valid Expense expense, BindingResult result) {
        if (result.hasErrors()) {
            return "add-expense";
        }
        expenseRepository.addExpense(expense);
        return "redirect:/expenses";
    }

    @GetMapping("/edit-expense/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Optional<Expense> expenseOpt = Optional.ofNullable(expenseRepository.getExpenseById(id));
        if (expenseOpt.isPresent()) {
            model.addAttribute("expense", expenseOpt.get());
            return "edit-expense";
        } else {
            return "redirect:/expenses";
        }
    }

    @PostMapping("/edit-expense/{id}")
    public String editExpense(@PathVariable int id, @ModelAttribute("expense") @Valid Expense expense, BindingResult result) {
        if (result.hasErrors()) {
            return "edit-expense";
        }
        expense.setId(id);
        expenseRepository.updateExpense(expense);
        return "redirect:/expenses";
    }

    @GetMapping("/delete-expense/{id}")
    public String deleteExpense(@PathVariable int id) {
        expenseRepository.deleteExpense(id);
        return "redirect:/expenses";
    }
}
