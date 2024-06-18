/**
 * @author  - Code Generator
 * @createdOn -  07/06/2024
 * @Description Entity class for ExcelReader Service
 * 
 */

package com.cpa.ttsms.service;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import com.cpa.ttsms.entity.ExcelReader;

public interface ExcelReaderService {

//	ExcelReader createExcelReader(ExcelReader excelReader);

	void readExcelData(InputStream inputStream);
	
	ExcelReader getDataByInvoiceId(String invoiceNo);
	
	  public List<ExcelReader> getExcelReadersByDateRange(LocalDate startDate, LocalDate endDate);
	

	  public List<ExcelReader> getExcelReadersByDueDateRange(LocalDate startDate, LocalDate endDate);
	  
	  public List<ExcelReader> GetAllData();
	  
	  boolean updateInvoicesPaidDateAsTodaysDate(List<String> invoiceNumbers);

		boolean updateRecoveryDateAsTodaysDate(List<String> invoiceNumbers);

		boolean updateSecondDateAsTodaysDate(List<String> invoiceNumbers);
	  
	  //Inserting the data
	  ExcelReader insertExcelReader(ExcelReader excelReader);
}