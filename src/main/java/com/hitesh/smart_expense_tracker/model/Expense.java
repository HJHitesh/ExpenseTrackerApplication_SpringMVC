package com.hitesh.smart_expense_tracker.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Expense {
	
	private int id;
    private String description;
    private double amount;
    private String category;
    private LocalDate date;

}
