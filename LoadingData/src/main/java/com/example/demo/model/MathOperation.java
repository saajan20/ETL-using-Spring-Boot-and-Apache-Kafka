package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MathOperation {
	
	private String numberOne;
	private String numberTwo;
	@Id
	private String operation;
	private String result;
	
	
	public MathOperation() {
		
	}


	public MathOperation(String numberOne, String numberTwo, String operation, String result) {
		
		this.numberOne = numberOne;
		this.numberTwo = numberTwo;
		this.operation = operation;
		this.result = result;
	}


	public String getNumberOne() {
		return numberOne;
	}


	public void setNumberOne(String numberOne) {
		this.numberOne = numberOne;
	}


	public String getNumberTwo() {
		return numberTwo;
	}


	public void setNumberTwo(String numberTwo) {
		this.numberTwo = numberTwo;
	}


	public String getOperation() {
		return operation;
	}


	public void setOperation(String operation) {
		this.operation = operation;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}
	
	

}
