package com.LoanManagementSystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="loans")
public class Loan 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="loan_id")
	private int loanId;
	
	@Column(name="customer_name",nullable = false, length=100)
	private String customerName;
	
	@Column(name="loan_type",nullable=false,length=20)
	private String loanType;
	
	@Column(name="loan_amount", nullable=false)
	private double loanAmount;
	
	@Column(name="intrest_rate",nullable=false)
	private double intrestRate;
	
	@Column(name="duration_month",nullable=false)
	private int durationMonth;
	
	@Column(name="loan_status",nullable=false,length=20)
	private String loanStatus;
	
	@Column(name="credit_score",nullable=false)
	private int creditScore;
	
	
	public Loan() {
		
	}
	public Loan(int loanId, String customerName, String loanType, double loanAmount, double intrestRate,
			int durationMonth, String loanStatus, int creditScore) {
		
		this.loanId = loanId;
		this.customerName = customerName;
		this.loanType = loanType;
		this.loanAmount = loanAmount;
		this.intrestRate = intrestRate;
		this.durationMonth = durationMonth;
		this.loanStatus = loanStatus;
		this.creditScore = creditScore;
	}
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public double getIntrestRate() {
		return intrestRate;
	}
	public void setIntrestRate(double intrestRate) {
		this.intrestRate = intrestRate;
	}
	public int getDurationMonth() {
		return durationMonth;
	}
	public void setDurationMonth(int durationMonth) {
		this.durationMonth = durationMonth;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public int getCreditScore() {
		return creditScore;
	}
	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}
	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", customerName=" + customerName + ", loanType=" + loanType + ", loanAmount="
				+ loanAmount + ", intrestRate=" + intrestRate + ", durationMonth=" + durationMonth + ", loanStatus="
				+ loanStatus + ", creditScore=" + creditScore + "]";
	}
	
	
	
	

}
