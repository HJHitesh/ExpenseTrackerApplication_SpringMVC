package com.hitesh.smart_expense_tracker.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitesh.smart_expense_tracker.model.Expense;

@Repository
public class ExpenseRepository {
	
	private static final String FILE_PATH = "expenses.json";
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	private static List<Expense> expenses = new ArrayList();
	
	
	ExpenseRepository(){
		loadExpenses();
	}
	
	private void loadExpenses() {
		File file = new File(FILE_PATH);
		
		if(file.exists()) {
			try {
				expenses = objectMapper.readValue(file, new TypeReference<List<Expense>>() {});
			} catch (IOException e) {
				e.printStackTrace();
				expenses = new ArrayList<>();
			}
		}
	}
	
	// Save expenses to JSON file
    private void saveExpenses() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), expenses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	 // Get all expenses
    public List<Expense> getAllExpenses() {
        return expenses;
    }

    // Add a new expense
    public void addExpense(Expense expense) {
        expense.setId(generateId()); // Assign a unique ID
        expenses.add(expense);
        saveExpenses();
    }

    // Find expene by ID
    public Expense  getExpenseById(int id) {
        return expenses.stream()
                       .filter(exp -> exp.getId() == id)
                       .findFirst().get();
    }


    // Update an existing expense
    public void updateExpense(Expense updatedExpense) {
        expenses.replaceAll(exp -> exp.getId() == updatedExpense.getId() ? updatedExpense : exp);
        saveExpenses();
    }

    // Delete an expense
    public void deleteExpense(int id) {
        expenses.removeIf(exp -> exp.getId() == id);
        saveExpenses();
    }

    // Generate a unique ID
    private int generateId() {
        return expenses.isEmpty() ? 1 : expenses.get(expenses.size() - 1).getId() + 1;
    }

}
