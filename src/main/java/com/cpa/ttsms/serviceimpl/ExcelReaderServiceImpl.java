package com.cpa.ttsms.serviceimpl;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpa.ttsms.entity.ExcelReader;
import com.cpa.ttsms.entity.IntrestData;
import com.cpa.ttsms.repository.ExcelReaderRepo;
import com.cpa.ttsms.repository.IntrestRepo;
import com.cpa.ttsms.service.ExcelReaderService;

@Service
public class ExcelReaderServiceImpl implements ExcelReaderService {

    @Autowired
    private ExcelReaderRepo excelReaderRepo;
    private static Logger logger;
    
    
    @Autowired
    private IntrestRepo intrestRepo;

    public ExcelReaderServiceImpl() {
        logger = Logger.getLogger(ExcelReaderServiceImpl.class);
    }

    @Override
    public void readExcelData(InputStream inputStream) {
    	 List<ExcelReader> invoiceDetailsList = new ArrayList<>();

    	    try (Workbook workbook = WorkbookFactory.create(inputStream)) {
    	        int numberOfSheets = workbook.getNumberOfSheets();
    	        for (int i = 0; i < numberOfSheets; i++) {
    	            Sheet sheet = workbook.getSheetAt(i);
    	            Iterator<Row> rowIterator = sheet.iterator();
    	            while (rowIterator.hasNext()) {
    	                Row row = rowIterator.next();
    	                if (row.getRowNum() == 0) continue; // Skip header row

    	                ExcelReader invoiceDetails = new ExcelReader();
    	               
    	                // Set ID manually if needed
    	                // invoiceDetails.setId(...);
    	                System.out.println(invoiceDetails);
    	                
    	                
    	                invoiceDetails.setInvoiceNo(getCellValueAsString(row.getCell(0)));
    	                invoiceDetails.setInvoiceDate(convertToLocalDate(row.getCell(1)));
    	                invoiceDetails.setInvoiceAmt(getCellValueAsDouble(row.getCell(2)));
//    	                invoiceDetails.setFinancedAmount(getCellValueAsDouble(row.getCell(3)));
//    	                invoiceDetails.setSetup(getCellValueAsDouble(row.getCell(4)));
//    	                invoiceDetails.setInterest(getCellValueAsDouble(row.getCell(5)));
//    	                invoiceDetails.setPaidAmt(getCellValueAsDouble(row.getCell(6)));
//    	                invoiceDetails.setPaidDate(convertToLocalDate(row.getCell(7)));
 //   	                invoiceDetails.setCreditDays(getCellValueAsInteger(row.getCell(8)));
//    	                invoiceDetails.setDueDate(convertToLocalDate(row.getCell(9)));
//    	                invoiceDetails.setRecdDate(convertToLocalDate(row.getCell(10)));
//    	                invoiceDetails.setBalAmt(getCellValueAsDouble(row.getCell(11)));
//    	                invoiceDetails.setSecondPaidDate(convertToLocalDate(row.getCell(12)));
    	                
    	                
    	                

    	                // Validate mandatory fields
    	                if (invoiceDetails.getInvoiceNo() == null || invoiceDetails.getInvoiceNo().isEmpty()) {
    	                    throw new IllegalArgumentException("InvoiceNo cannot be null or empty");
    	                }
System.out.println(invoiceDetails + "******invoice details*****");
    	                invoiceDetailsList.add(invoiceDetails);
    	               //Calculate financeamount
    	               Double finanaceAmount= invoiceDetails.getInvoiceAmt()*0.9;
    	               System.out.println(finanaceAmount);
    	               //Balance amount
    	               Double balanceAmount=invoiceDetails.getInvoiceAmt()-finanaceAmount;
    	               
    	               //Setup
    	               Double SetUpAmount=finanaceAmount * 0.005;
    	               
    	               //IntrestRate 
    	               Double IntrestRate=finanaceAmount*0.025;
    	               
    	               //paid Amount
    	               Double paidAmount=finanaceAmount-IntrestRate-SetUpAmount;
    	               
    	               invoiceDetails.setFinancedAmount(finanaceAmount);
    	               invoiceDetails.setBalAmt(balanceAmount);
    	               invoiceDetails.setSetup(SetUpAmount);
    	               
    	               LocalDate currentDate = invoiceDetails.getInvoiceDate();
    	                
    	             //  invoiceDetails.setDueDate(currentDate.plusDays(invoiceDetails.getCreditDays()));
    	               invoiceDetails.setInterest(IntrestRate);
    	               
    	               invoiceDetails.setPaidAmt(paidAmount);
    	               
    	             
    	               
    	               
    	               
    	             
    	               
    	                
    	            }
    	        }
    	    } catch (Exception e) {
    	        logger.error("Error reading Excel file", e);
    	    }
    	    
    	    System.out.println("The Invoice Details.."+invoiceDetailsList);
    	    
    	   
    	    

    	    excelReaderRepo.saveAll(invoiceDetailsList);
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : cell.toString();
    }

    private LocalDate convertToLocalDate(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC || !DateUtil.isCellDateFormatted(cell)) {
            return null;
        }
        Date date = cell.getDateCellValue();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Double getCellValueAsDouble(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC) {
            return null;
        }
        return cell.getNumericCellValue();
    }

    private Integer getCellValueAsInteger(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC) {
            return null;
        }
        return (int) cell.getNumericCellValue();
    }

	@Override
	public ExcelReader getDataByInvoiceId(String invoiceNo) {
		// TODO Auto-generated method stub
		
	
		ExcelReader excelreader=excelReaderRepo.getAllDataByInvoiceNumber(invoiceNo);
		
		

		return excelreader;
	}
	
    public List<ExcelReader> getExcelReadersByDateRange(LocalDate startDate, LocalDate endDate) {
    	System.out.println("In Controller...");
    	System.out.println(excelReaderRepo.findAllByinvoiceDateBetween(startDate, endDate));
        return excelReaderRepo.findAllByinvoiceDateBetween(startDate, endDate);
    }

	@Override
	public List<ExcelReader> getExcelReadersByDueDateRange(LocalDate startDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		System.out.println(excelReaderRepo.findAllBydueDateBetween(startDate, endDate));
        return excelReaderRepo.findAllBydueDateBetween(startDate, endDate);
	}

	@Override
	public List<ExcelReader> GetAllData() {
		// TODO Auto-generated method stub
		return excelReaderRepo.findAll();
	}

	@Override
	public ExcelReader insertExcelReader(ExcelReader excelReader) {
		// TODO Auto-generated method stub
		
		
		
		
//		IntrestData instestData=intrestRepo.getSetupDataByID(excelReader.getSetupinstrest());
//		System.out.println(instestData);
		
//		

		IntrestData instestData=intrestRepo.getSetupDataByID();
         System.out.println(instestData);
		
		//INTREST_RATE 
		Double finance_rate=instestData.getFinance_percent();
//		
//		
//		//SETUP_PERCENTAGE
		double setuprate_percent=instestData.getSetup_percent();
//		
//		//Instrest rate
//		
    	double instrestrate_percent=instestData.getInstrest_rate();
//		
//		
		  Double finanaceAmount= excelReader.getInvoiceAmt()* finance_rate / 100;
          System.out.println(finanaceAmount);
//          //Balance amount
          Double balanceAmount=excelReader.getInvoiceAmt()-finanaceAmount;
//          
//          //Setup
          Double SetUpAmount=finanaceAmount * setuprate_percent /100;
//          
//          //IntrestRate 
          Double IntrestRate=finanaceAmount*instrestrate_percent/100;
//          
//          //paid Amount
          Double paidAmount=finanaceAmount-IntrestRate-SetUpAmount;
//          
          excelReader.setFinancedAmount(finanaceAmount);
          excelReader.setSetup(SetUpAmount);
          excelReader.setInterest(IntrestRate);
          excelReader.setPaidAmt(paidAmount);
          excelReader.setBalAmt(balanceAmount);
         
          
//          
//          
//          
//          
//          
          LocalDate currentDate = excelReader.getInvoiceDate();
           
          excelReader.setDueDate(currentDate.plusDays(instestData.getCredited_days()));
          
          excelReader.setCreditDays(instestData.getCredited_days());
          
         
		
	
		return excelReaderRepo.save(excelReader);
	}

}
