/**
 * @author  - Code Generator
 * @createdOn -  07/06/2024
 * @Description Entity class for joblocation
 */

package com.cpa.ttsms.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invoicedetails")
public class ExcelReader {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "InvoiceNo", nullable = false, unique = true)
	private String invoiceNo;

	@Column(name = "InvoiceDate")
	private LocalDate invoiceDate;

	@Column(name = "InvoiceAmt")
	private Double invoiceAmt;

	@Column(name = "FinancedAmount")
	private Double financedAmount;

	@Column(name = "Setup")
	private Double setup;

	@Column(name = "Interest")
	private Double interest;

	@Column(name = "PaidAmt")
	private Double paidAmt;

	@Column(name = "PaidDate")
	private LocalDate paidDate;

//	@Column(name = "CreditDays")
//	private Integer creditDays;

	@Column(name = "DueDate")
	private LocalDate dueDate;

	@Column(name = "RecdDate")
	private LocalDate recdDate;

	@Column(name = "BalAmt")
	private Double balAmt;

	@Column(name = "SecondPaidDate")
	private LocalDate secondPaidDate;
	
	
	@Column(name = "credited_days")
	private int creditDays;
	
	
	@Column(name = "status")
	private Integer statusDays;
	
	@Column(name="invoiceaddeddate")
	private LocalDateTime invoiceAddedDate;
	
	@Column(name = "financePercent")
	private Double financePercent;
	
	@Column(name = "intrestRate")
	private Double intrestRate;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Double getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(Double invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}

	public Double getFinancedAmount() {
		return financedAmount;
	}

	public void setFinancedAmount(Double financedAmount) {
		this.financedAmount = financedAmount;
	}

	public Double getSetup() {
		return setup;
	}

	public void setSetup(Double setup) {
		this.setup = setup;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public LocalDate getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(LocalDate paidDate) {
		this.paidDate = paidDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getRecdDate() {
		return recdDate;
	}

	public void setRecdDate(LocalDate recdDate) {
		this.recdDate = recdDate;
	}

	public Double getBalAmt() {
		return balAmt;
	}

	public void setBalAmt(Double balAmt) {
		this.balAmt = balAmt;
	}

	public LocalDate getSecondPaidDate() {
		return secondPaidDate;
	}

	public void setSecondPaidDate(LocalDate secondPaidDate) {
		this.secondPaidDate = secondPaidDate;
	}

	public int getCreditDays() {
		return creditDays;
	}

	public void setCreditDays(int creditDays) {
		this.creditDays = creditDays;
	}

	public LocalDateTime getInvoiceAddedDate() {
		return invoiceAddedDate;
	}

	public void setInvoiceAddedDate(LocalDateTime invoiceAddedDate) {
		this.invoiceAddedDate = invoiceAddedDate;
	}
	
	

	public Integer getStatusDays() {
		return statusDays;
	}

	public void setStatusDays(Integer statusDays) {
		this.statusDays = statusDays;
	}
	
	

	public Double getFinancePercent() {
		return financePercent;
	}

	public void setFinancePercent(Double financePercent) {
		this.financePercent = financePercent;
	}
	
	

	

	public Double getIntrestRate() {
		return intrestRate;
	}

	public void setIntrestRate(Double intrestRate) {
		this.intrestRate = intrestRate;
	}

	
	public ExcelReader(int id, String invoiceNo, LocalDate invoiceDate, Double invoiceAmt, Double financedAmount,
			Double setup, Double interest, Double paidAmt, LocalDate paidDate, LocalDate dueDate, LocalDate recdDate,
			Double balAmt, LocalDate secondPaidDate, int creditDays, Integer statusDays, LocalDateTime invoiceAddedDate,
			Double financePercent, Double intrestRate) {
		super();
		this.id = id;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.invoiceAmt = invoiceAmt;
		this.financedAmount = financedAmount;
		this.setup = setup;
		this.interest = interest;
		this.paidAmt = paidAmt;
		this.paidDate = paidDate;
		this.dueDate = dueDate;
		this.recdDate = recdDate;
		this.balAmt = balAmt;
		this.secondPaidDate = secondPaidDate;
		this.creditDays = creditDays;
		this.statusDays = statusDays;
		this.invoiceAddedDate = invoiceAddedDate;
		this.financePercent = financePercent;
		this.intrestRate = intrestRate;
	}

	public ExcelReader() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ExcelReader [id=" + id + ", invoiceNo=" + invoiceNo + ", invoiceDate=" + invoiceDate + ", invoiceAmt="
				+ invoiceAmt + ", financedAmount=" + financedAmount + ", setup=" + setup + ", interest=" + interest
				+ ", paidAmt=" + paidAmt + ", paidDate=" + paidDate + ", dueDate=" + dueDate + ", recdDate=" + recdDate
				+ ", balAmt=" + balAmt + ", secondPaidDate=" + secondPaidDate + ", creditDays=" + creditDays
				+ ", statusDays=" + statusDays + ", invoiceAddedDate=" + invoiceAddedDate + ", financePercent="
				+ financePercent + ", intrestRate=" + intrestRate + "]";
	}

	

	
	

	
	
}
