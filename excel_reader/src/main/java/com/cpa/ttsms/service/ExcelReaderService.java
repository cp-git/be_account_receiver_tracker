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
import com.cpa.ttsms.entity.IntrestData;

public interface ExcelReaderService {

//	ExcelReader createExcelReader(ExcelReader excelReader);

	void readExcelData(InputStream inputStream);

	ExcelReader getDataByInvoiceId(String invoiceNo);

	public List<ExcelReader> getExcelReadersByDateRange(LocalDate startDate, LocalDate endDate);

	public List<ExcelReader> getExcelReadersByDueDateRange(LocalDate startDate, LocalDate endDate);


	boolean updateSecondDateAsTodaysDate(List<String> invoiceNumbers);
	  

	ExcelReader insertExcelReader(ExcelReader excelReader);
	
	  
	public List<ExcelReader> getExcelReaderByCompanyIdAndStatus(Long companyId,int statusDays);
	public List<ExcelReader> getExcelReaderByCompanyId(Long companyId);


	public List<ExcelReader> GetAllData();

	boolean updateInvoicesPaidDateAsTodaysDate(List<String> invoiceNumbers);

	boolean updateRecoveryDateAsTodaysDate(List<String> invoiceNumbers);




	List<ExcelReader> getExcelReaderByStatusId(int statusDays);

	public List<ExcelReader> getInvoicesByRangeDatesOfInvoiceDateAndStatus(LocalDate startDate, LocalDate endDate,
			int Status);

	public ExcelReader updateInvoiceByInvoiceNo(ExcelReader excelReader, String invoiceNo);

	public ExcelReader updateInvoiceById(ExcelReader excelReader, int id);

	public ExcelReader updateInvoiceAndFinancedPercentageById(ExcelReader excelReader, int id,
			double financedPercentage);

	public IntrestData getIntrestDataById(int id);
}
