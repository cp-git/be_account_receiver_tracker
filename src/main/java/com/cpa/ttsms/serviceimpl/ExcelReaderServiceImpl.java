package com.cpa.ttsms.serviceimpl;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
		return excelReaderRepo.findAllByOrderByInvoiceDateAsc();
	}

//	@Override
//	public ExcelReader insertExcelReader(ExcelReader excelReader) {
//		// TODO Auto-generated method stub
//		
//		
////		IntrestData instestData=intrestRepo.getSetupDataByID(excelReader.getSetupinstrest());
////		System.out.println(instestData);
//		 LocalDateTime today = LocalDateTime.now();
//		IntrestData instestData=intrestRepo.getSetupDataByID();
//         System.out.println(instestData);
//		//INTREST_RATE 
//		Double finance_rate=instestData.getFinance_percent();
////		//SETUP_PERCENTAGE
//		double setuprate_percent=instestData.getSetup_percent();
//
//    	double instrestrate_percent=instestData.getInstrest_rate();
//		  Double finanaceAmount= excelReader.getInvoiceAmt()* finance_rate / 100;
//          System.out.println(finanaceAmount);
////          //Balance amount
//          Double balanceAmount=excelReader.getInvoiceAmt()-finanaceAmount;
////          
////          //Setup
//          Double SetUpAmount=finanaceAmount * setuprate_percent /100;
////          
////          //IntrestRate 
//          Double IntrestRate=finanaceAmount*instrestrate_percent/100;
////          
////          //paid Amount
//          Double paidAmount=finanaceAmount-IntrestRate-SetUpAmount;      
//          excelReader.setFinancedAmount(finanaceAmount);
//          excelReader.setSetup(SetUpAmount);
//          excelReader.setInterest(IntrestRate);
//          excelReader.setPaidAmt(paidAmount);
//          excelReader.setBalAmt(balanceAmount);
//          excelReader.setInvoiceAddedDate(today);
//          excelReader.setStatusDays(0);
//         
//          
//       
//          LocalDate currentDate = excelReader.getInvoiceDate();
//           
//          excelReader.setDueDate(currentDate.plusDays(instestData.getCredited_days()));
//          
//          excelReader.setCreditDays(instestData.getCredited_days());
//          
//         
//		
//	
//		return excelReaderRepo.save(excelReader);
//	}

	@Override
	public boolean updateInvoicesPaidDateAsTodaysDate(List<String> invoiceNumbers) {
		boolean allUpdated = true;
		LocalDate today = LocalDate.now();
		for (String invoiceNo : invoiceNumbers) {
			try {
				ExcelReader toUpdateDetails = excelReaderRepo.findByInvoiceNo(invoiceNo);
				if (toUpdateDetails != null) {
					toUpdateDetails.setPaidDate(today);
					toUpdateDetails.setStatusDays(1);
//					toUpdateDetails.setRecdDate(today);
					excelReaderRepo.save(toUpdateDetails);
				} else {
					allUpdated = false; // If any invoice number is not found, set the flag to false
				}
			} catch (Exception e) {
				allUpdated = false; // If any exception occurs, set the flag to false
			}
		}
		return allUpdated;
	}

	@Override
	public boolean updateRecoveryDateAsTodaysDate(List<String> invoiceNumbers) {
		boolean allUpdated = true;
		LocalDate today = LocalDate.now();
		for (String invoiceNo : invoiceNumbers) {
			try {
				ExcelReader toUpdateDetails = excelReaderRepo.findByInvoiceNo(invoiceNo);
				if (toUpdateDetails != null) {
//					toUpdateDetails.setPaidDate(today);
					toUpdateDetails.setRecdDate(today);
					toUpdateDetails.setStatusDays(2);
					excelReaderRepo.save(toUpdateDetails);
				} else {
					allUpdated = false; // If any invoice number is not found, set the flag to false
				}
			} catch (Exception e) {
				allUpdated = false; // If any exception occurs, set the flag to false
			}
		}
		return allUpdated;
	}

	@Override
	public boolean updateSecondDateAsTodaysDate(List<String> invoiceNumbers) {
		boolean allUpdated = true;
		System.out.println(invoiceNumbers + "************");
		LocalDate today = LocalDate.now();
		for (String invoiceNo : invoiceNumbers) {
			try {
				ExcelReader toUpdateDetails = excelReaderRepo.findByInvoiceNo(invoiceNo);
				if (toUpdateDetails != null) {
//					toUpdateDetails.setPaidDate(today);
					toUpdateDetails.setSecondPaidDate(today);
					toUpdateDetails.setStatusDays(3);
					excelReaderRepo.save(toUpdateDetails);
				} else {
					allUpdated = false; // If any invoice number is not found, set the flag to false
				}
			} catch (Exception e) {
				allUpdated = false; // If any exception occurs, set the flag to false
			}
		}
		return allUpdated;
	}

	@Override
	public List<ExcelReader> getExcelReaderByStatusId(int statusDays) {
		// TODO Auto-generated method stub
		return excelReaderRepo.findByStatusDaysOrderByInvoiceDateAsc(statusDays);
	}

	@Override
	public List<ExcelReader> getInvoicesByRangeDatesOfInvoiceDateAndStatus(LocalDate startDate, LocalDate endDate,
			int status) {
		 List<ExcelReader> result;
		    if(status == 0) {
		        result = excelReaderRepo.findAllByinvoiceDateBetweenAndStatusDays(startDate, endDate, status);
		    } else if(status == 1) {
		        result = excelReaderRepo.findAllByPaidDateBetweenAndStatusDays(startDate, endDate, status);
		    } else if(status == 2) {
		        result = excelReaderRepo.findAllByRecdDateBetweenAndStatusDays(startDate, endDate, status);
		    }else if(status ==3) {
		    	 result = excelReaderRepo.findAllBySecondPaidDateBetweenAndStatusDays(startDate, endDate, status);
		    } else {
		        result = new ArrayList<>(); // Return an empty list or handle this case as needed
		    }
		    return result;
	}

	@Override
	public ExcelReader updateInvoiceByInvoiceNo(ExcelReader excelReader, String invoiceNo) {
		// TODO Auto-generated method stub
		ExcelReader toUpdateInvoice = null ;
		ExcelReader updatedInvoice = null;
		toUpdateInvoice = excelReaderRepo.findByInvoiceNo(invoiceNo);
		if(toUpdateInvoice != null) {
			toUpdateInvoice.setInvoiceNo(excelReader.getInvoiceNo());
			toUpdateInvoice.setInvoiceDate(excelReader.getInvoiceDate());
			toUpdateInvoice.setFinancedAmount(excelReader.getFinancedAmount());
			toUpdateInvoice.setSetup(excelReader.getSetup());
			toUpdateInvoice.setInterest(excelReader.getInterest());
			toUpdateInvoice.setPaidAmt(excelReader.getPaidAmt());
			toUpdateInvoice.setPaidDate(excelReader.getPaidDate());
			toUpdateInvoice.setCreditDays(excelReader.getCreditDays());
			toUpdateInvoice.setDueDate(excelReader.getDueDate());
			toUpdateInvoice.setRecdDate(excelReader.getRecdDate());
			toUpdateInvoice.setBalAmt(excelReader.getBalAmt());
			toUpdateInvoice.setSecondPaidDate(excelReader.getSecondPaidDate());

			if(excelReader.getPaidDate()==null) {
				toUpdateInvoice.setPaidDate(null);
				toUpdateInvoice.setStatusDays(0);
			}
			else if(excelReader.getPaidDate()!=null) {
				toUpdateInvoice.setPaidDate(excelReader.getPaidDate());
				toUpdateInvoice.setStatusDays(1);
				
			}
			
			
			if(excelReader.getPaidDate()!=null && excelReader.getRecdDate()==null ) {
				toUpdateInvoice.setRecdDate(null);
				toUpdateInvoice.setStatusDays(1);
			}
			else if(excelReader.getPaidDate()!=null && excelReader.getRecdDate()!=null ) {
				toUpdateInvoice.setRecdDate(excelReader.getRecdDate());
				toUpdateInvoice.setStatusDays(2);
				
			}
			
			
			if(excelReader.getRecdDate()!=null && excelReader.getSecondPaidDate()==null ) {
				toUpdateInvoice.setSecondPaidDate(null);
				toUpdateInvoice.setStatusDays(2);
			}
			else if(excelReader.getRecdDate()!=null && excelReader.getSecondPaidDate()!=null ) {
				toUpdateInvoice.setSecondPaidDate(excelReader.getSecondPaidDate());
				toUpdateInvoice.setStatusDays(3);
				
			}
			
			
			updatedInvoice = excelReaderRepo.save(toUpdateInvoice);
			
		}
		return updatedInvoice;
	}

//	@Override
//	public ExcelReader updateInvoiceById(ExcelReader excelReader, int id) {
//		ExcelReader toUpdateInvoice = null ;
//		ExcelReader updatedInvoice = null;
//		toUpdateInvoice = excelReaderRepo.findById(id);
//		if(toUpdateInvoice != null) {
//			toUpdateInvoice.setInvoiceNo(excelReader.getInvoiceNo());
//			toUpdateInvoice.setInvoiceDate(excelReader.getInvoiceDate());
//			toUpdateInvoice.setInvoiceAmt(excelReader.getInvoiceAmt());
//			toUpdateInvoice.setFinancedAmount(excelReader.getFinancedAmount());
//			toUpdateInvoice.setSetup(excelReader.getSetup());
//			toUpdateInvoice.setInterest(excelReader.getInterest());
//			toUpdateInvoice.setPaidAmt(excelReader.getPaidAmt());
//			toUpdateInvoice.setPaidDate(excelReader.getPaidDate());
//			toUpdateInvoice.setCreditDays(excelReader.getCreditDays());
//			toUpdateInvoice.setDueDate(excelReader.getDueDate());
//			toUpdateInvoice.setRecdDate(excelReader.getRecdDate());
//			toUpdateInvoice.setBalAmt(excelReader.getBalAmt());
//			toUpdateInvoice.setSecondPaidDate(excelReader.getSecondPaidDate());
//			toUpdateInvoice.setStatusDays(1);
//			
//			
//			 if(excelReader.getPaidDate()!=null && excelReader.getRecdDate()!=null ) {
//				toUpdateInvoice.setStatusDays(2);	
//			}
//			 if(excelReader.getSecondPaidDate()!=null && excelReader.getPaidDate()!=null) {
//				toUpdateInvoice.setStatusDays(3);		
//			}
//			
//			
//			updatedInvoice = excelReaderRepo.save(toUpdateInvoice);
//			
//		}
//		return updatedInvoice;
//	}
	
	@Override
	public ExcelReader updateInvoiceById(ExcelReader excelReader, int id) {
		ExcelReader toUpdateInvoice = null ;
		ExcelReader updatedInvoice = null;
		toUpdateInvoice = excelReaderRepo.findById(id);
		if(toUpdateInvoice != null) {
			 LocalDateTime today = LocalDateTime.now();
			IntrestData instestData=intrestRepo.getSetupDataByID();
			Double finance_rate=instestData.getFinance_percent();
			double setuprate_percent=instestData.getSetup_percent();
	    	double instrestrate_percent=instestData.getInstrest_rate();
			  Double finanaceAmount= excelReader.getInvoiceAmt()* finance_rate / 100;
	          Double balanceAmount=excelReader.getInvoiceAmt()-finanaceAmount;
	          Double SetUpAmount=finanaceAmount * setuprate_percent /100;
	          Double IntrestRate=finanaceAmount*instrestrate_percent/100;
	          Double paidAmount=finanaceAmount-IntrestRate-SetUpAmount;      
	
	          
	       
	          LocalDate currentDate = excelReader.getInvoiceDate();
	           
	          excelReader.setDueDate(currentDate.plusDays(instestData.getCredited_days()));
	          
	        //  excelReader.setCreditDays(instestData.getCredited_days());
	          
	         
			toUpdateInvoice.setInvoiceNo(excelReader.getInvoiceNo());
			toUpdateInvoice.setInvoiceDate(excelReader.getInvoiceDate());
			toUpdateInvoice.setInvoiceAmt(excelReader.getInvoiceAmt());
			toUpdateInvoice.setFinancedAmount(finanaceAmount);
			toUpdateInvoice.setSetup(SetUpAmount);
			toUpdateInvoice.setInterest(IntrestRate);
			toUpdateInvoice.setPaidAmt(paidAmount);
			
			toUpdateInvoice.setCreditDays(excelReader.getCreditDays());
			toUpdateInvoice.setDueDate(excelReader.getDueDate());
			toUpdateInvoice.setRecdDate(excelReader.getRecdDate());
			toUpdateInvoice.setBalAmt(balanceAmount);
			toUpdateInvoice.setSecondPaidDate(excelReader.getSecondPaidDate());
			
			System.out.println(toUpdateInvoice.getInterest());
			
			if(excelReader.getPaidDate()==null) {
				toUpdateInvoice.setPaidDate(null);
				toUpdateInvoice.setStatusDays(0);
				toUpdateInvoice.setFinancePercent(null);
				toUpdateInvoice.setFinancedAmount(null);
				toUpdateInvoice.setSetup(null);
				toUpdateInvoice.setInterest(null);
				toUpdateInvoice.setPaidAmt(null);
				toUpdateInvoice.setIntrestRate(null);
				toUpdateInvoice.setBalAmt(null);
				
				
				
				
			}
			else if(excelReader.getPaidDate()!=null) {
				toUpdateInvoice.setPaidDate(excelReader.getPaidDate());
				toUpdateInvoice.setStatusDays(1);
				
				toUpdateInvoice.setInterest(IntrestRate);
				
				
				
			}
			
			
			if(excelReader.getPaidDate()!=null && excelReader.getRecdDate()==null ) {
				toUpdateInvoice.setRecdDate(null);
				toUpdateInvoice.setStatusDays(1);
			}
			else if(excelReader.getPaidDate()!=null && excelReader.getRecdDate()!=null ) {
				toUpdateInvoice.setRecdDate(excelReader.getRecdDate());
				toUpdateInvoice.setStatusDays(2);
				
			}
			
			
			if(excelReader.getRecdDate()!=null && excelReader.getSecondPaidDate()==null ) {
				toUpdateInvoice.setSecondPaidDate(null);
				toUpdateInvoice.setStatusDays(2);
			}
			else if(excelReader.getRecdDate()!=null && excelReader.getSecondPaidDate()!=null ) {
				toUpdateInvoice.setSecondPaidDate(excelReader.getSecondPaidDate());
				toUpdateInvoice.setStatusDays(3);
				
			}
			
		
			
			
			
//			 if(excelReader.getPaidDate()!=null && excelReader.getRecdDate()!=null ) {
//				toUpdateInvoice.setStatusDays(2);	
//			}
//			 if(excelReader.getSecondPaidDate()!=null && excelReader.getPaidDate()!=null) {
//				toUpdateInvoice.setStatusDays(3);		
//			}
			
			
			updatedInvoice = excelReaderRepo.save(toUpdateInvoice);
			
		}
		return updatedInvoice;
	}
	
	@Override
	public ExcelReader insertExcelReader(ExcelReader excelReader) {
		ExcelReader createInvoice = null;
		excelReader.setStatusDays(0);
		excelReader.setCreditDays(0);
		createInvoice = excelReaderRepo.save(excelReader);
		return createInvoice;
	}
	
	@Override
	public ExcelReader updateInvoiceAndFinancedPercentageById(ExcelReader excelReader, int id,
			double financedPercentage) {
		
		IntrestData toUpdateIntrest = updateIntrestData(financedPercentage);
		ExcelReader toUpdateInvoice = null;
		ExcelReader updatedInvoice = null;
		toUpdateInvoice = excelReaderRepo.findById(id);
		if (toUpdateIntrest != null) {
			
			

			//Taking finance rate from intrest_data table
			Double finance_rate = toUpdateIntrest.getFinance_percent();
			System.out.println("Finance Percent from intrest data table"+finance_rate);
			
			//Taking finance rate from intrest_data table
			double setuprate_percent = toUpdateIntrest.getSetup_percent();
			System.out.println("setuprate_percent from intrest data table"+setuprate_percent);
			
			//Taking finance rate from intrest_data table
			double instrestrate_percent = toUpdateIntrest.getInstrest_rate();
			System.out.println("instrestrate_percent from intrest data table"+instrestrate_percent);
			
			
			//Calculate  finanaceAmount 
			Double finanaceAmount = excelReader.getInvoiceAmt() * finance_rate / 100;
			System.out.println("Calculate  finanaceAmount "+finanaceAmount);
			
			//Calculate  balanceAmount 
			Double balanceAmount = excelReader.getInvoiceAmt() - finanaceAmount;
			System.out.println("Calculate  balanceAmount"+balanceAmount);
			
			//Calculate  SetUpAmount 
			Double SetUpAmount = finanaceAmount * setuprate_percent / 100;
			System.out.println("Calculate  SetUpAmount"+SetUpAmount);
			
			//Calculate  IntrestRate 
			Double IntrestRate = finanaceAmount * instrestrate_percent / 100;
			System.out.println("Calculate  IntrestRate "+IntrestRate);
			
			//Calculate  paidAmount 
			Double paidAmount = finanaceAmount - IntrestRate - SetUpAmount;
			
			System.out.println("Calculate  paidAmount "+paidAmount);
			

			toUpdateInvoice.setInvoiceNo(excelReader.getInvoiceNo());
			toUpdateInvoice.setInvoiceDate(excelReader.getInvoiceDate());
			toUpdateInvoice.setInvoiceAmt(excelReader.getInvoiceAmt());
			toUpdateInvoice.setPaidDate(excelReader.getPaidDate());	
			toUpdateInvoice.setDueDate(excelReader.getDueDate());
			toUpdateInvoice.setRecdDate(excelReader.getRecdDate());
			toUpdateInvoice.setSecondPaidDate(excelReader.getSecondPaidDate());
		
			
/////////////////////////////////////////////  DUE DATE AND PAID DATE CALCULATION /////////////////////////////////			
			
			 LocalDate dueDate = excelReader.getDueDate();
		     LocalDate paidDate = excelReader.getPaidDate();
		     
		     
		     // Calculate the number of days between the two dates
		     /*
		      * We are adding one to the difference between the 
		      * "Due" date and "Paid" date to include the date on which
		      * the amount was paid in the calculation to check for completion of 30 days.
		      * For example If the Due date is on 4 July and financier provides the amount on 4 June 
		      * the amount is given to client 31 days from the due date attracting an interest for
		      * two 30 day intervals. 
		      * 
		      */
		       long daysBetweenDueAndPaid = ChronoUnit.DAYS.between(paidDate,dueDate)+1;
		       
		       System.out.println("The Days Between Due Date and Paid Date.."+daysBetweenDueAndPaid);
		       
		        
		        long beginInterval=30;
		        if(daysBetweenDueAndPaid <= beginInterval) {
		        	//System.out.println("Intrest rate Below 30 days");
		        	toUpdateInvoice.setFinancePercent(finance_rate);
		        	toUpdateInvoice.setFinancedAmount(finanaceAmount);
		        	toUpdateInvoice.setInterest(IntrestRate);
		        	toUpdateInvoice.setIntrestRate(IntrestRate);
		        	toUpdateInvoice.setSetup(SetUpAmount);
		        	toUpdateInvoice.setPaidAmt(paidAmount);
		        	
		        	     
		        }
		        else {
		        	
//		        	Double finanaceAmount = excelReader.getInvoiceAmt() * finance_rate / 100;
//					System.out.println("Calculate  finanaceAmount "+finanaceAmount);
					
					//Calculate  IntrestRate 
					Double NormalIntrestRate = finanaceAmount * instrestrate_percent *2 / 100;
					System.out.println("Calculate  IntrestRate "+IntrestRate);
					
					//Calculate  paidAmount 
					Double paidAmountIntrest = finanaceAmount - NormalIntrestRate - SetUpAmount;
					
					System.out.println("Calculate  paidAmount "+paidAmount);
		        	
		       
					
					toUpdateInvoice.setFinancePercent(finance_rate);
		        	toUpdateInvoice.setFinancedAmount(finanaceAmount);
		        	toUpdateInvoice.setInterest(NormalIntrestRate);
		        	toUpdateInvoice.setIntrestRate(NormalIntrestRate);
		        	toUpdateInvoice.setSetup(SetUpAmount);
		        	toUpdateInvoice.setPaidAmt(paidAmountIntrest);
		        	
		        }
		       
		
			
			
			

			}
			
			
			
/////////////////////////////////////////////  PAID DATE AND RECEIVED  DATE CALCULATION /////////////////////////////////			

			Double finance_rate = toUpdateIntrest.getFinance_percent();
			double setuprate_percent = toUpdateIntrest.getSetup_percent();
			double instrestrate_percent = toUpdateIntrest.getInstrest_rate();
			Double finanaceAmount = excelReader.getInvoiceAmt() * finance_rate / 100;
			
			Double SetUpAmount = finanaceAmount * setuprate_percent / 100;
			Double IntrestRate = finanaceAmount * instrestrate_percent  / 100;
			
			Double paidAmount = finanaceAmount - IntrestRate - SetUpAmount;
			
			Double balanceAmount = excelReader.getInvoiceAmt() - finanaceAmount -IntrestRate;
			
			Double balanceAmountOld = excelReader.getInvoiceAmt() - finanaceAmount;
			
			
			
			
		
			
			
		  Double updatedDueAndPaid = finanaceAmount * instrestrate_percent * 2  / 100;
			
			Double updatedDueAndPaidAmount = finanaceAmount - IntrestRate - SetUpAmount;

			
			
			if(toUpdateInvoice.getRecdDate()!=null) {
			  LocalDate startDate = excelReader.getPaidDate();
		       LocalDate endDate = excelReader.getRecdDate();
		       
		       LocalDate dueDate = excelReader.getDueDate();
		       
		        // Calculate the number of days between the two dates  :  paid date and received Date
		        long daysBetweenPaidAndRecDate = ChronoUnit.DAYS.between(startDate, endDate)+1;
		        System.out.println("Days in Between PaidAndRecDate...."+daysBetweenPaidAndRecDate);
		        
		        
		        
		        long daysBetweenDueAndPaid = ChronoUnit.DAYS.between(startDate, dueDate);
		        System.out.println("The Difference between Paid and Due Date");
		        
		        
		        long beginInterVal=30;
		        long lastInterval=60;
		        
		        
		        
		        
		         if(daysBetweenDueAndPaid <= beginInterVal &&  daysBetweenPaidAndRecDate <=lastInterval) {
		        	toUpdateInvoice.setIntrestRecDate(0.0);
		        	toUpdateInvoice.setBalAmt(balanceAmountOld);
		        	
		        }
		         
		         else if(daysBetweenDueAndPaid >=beginInterVal && daysBetweenPaidAndRecDate <=lastInterval) {
		        		toUpdateInvoice.setIntrestRecDate(0.0);
		        		toUpdateInvoice.setInterest(updatedDueAndPaid);
		        		toUpdateInvoice.setPaidAmt(updatedDueAndPaidAmount);
		        		toUpdateInvoice.setBalAmt(balanceAmountOld);
		         }
		        

		       
		        else {
		        	//System.out.println("Intrest rate"+instrestrate_percent1);
		        	toUpdateInvoice.setIntrestRecDate(IntrestRate);
		        	toUpdateInvoice.setBalAmt(balanceAmount);
		        	
		        
		        }
			}
			
///////////////////////////// Enable the Status Using the paid date, rec date , second paid date/////////////////////////////////	
			//
			if(excelReader.getPaidDate()==null) {
				toUpdateInvoice.setPaidDate(null);
				toUpdateInvoice.setStatusDays(0);
			}
			else if(excelReader.getPaidDate()!=null) {
				toUpdateInvoice.setPaidDate(excelReader.getPaidDate());
				toUpdateInvoice.setStatusDays(1);
				
				
				
			}
			
			
			if(excelReader.getPaidDate()!=null && excelReader.getRecdDate()==null ) {
				toUpdateInvoice.setRecdDate(null);
				toUpdateInvoice.setStatusDays(1);
				toUpdateInvoice.setIntrestRecDate(0.0);
				toUpdateInvoice.setBalAmt(null);
				
				
				
			}
			else if(excelReader.getPaidDate()!=null && excelReader.getRecdDate()!=null ) {
				toUpdateInvoice.setRecdDate(excelReader.getRecdDate());
				toUpdateInvoice.setStatusDays(2);
				
			}
			
			
			if(excelReader.getRecdDate()!=null && excelReader.getSecondPaidDate()==null ) {
				toUpdateInvoice.setSecondPaidDate(null);
				toUpdateInvoice.setStatusDays(2);
			}
			else if(excelReader.getRecdDate()!=null && excelReader.getSecondPaidDate()!=null ) {
				toUpdateInvoice.setSecondPaidDate(excelReader.getSecondPaidDate());
				toUpdateInvoice.setStatusDays(3);
				
			}
			
			
	
		
			
			updatedInvoice = excelReaderRepo.save(toUpdateInvoice);
		
		return updatedInvoice;
	}

	public IntrestData updateIntrestData(double finPercentage) {
	    if (finPercentage == 0) {
	        // If the finPercentage is 0, do not update and return the existing data
	        return intrestRepo.getIntrestDataById(1);
	    }

	    IntrestData updatedIntrestData = null;
	    IntrestData toUpdatePercentage = null;
	    toUpdatePercentage = intrestRepo.getIntrestDataById(1);
	    if (toUpdatePercentage != null) {
	        toUpdatePercentage.setFinance_percent(finPercentage);
	        updatedIntrestData = intrestRepo.save(toUpdatePercentage);
	    }
	    return updatedIntrestData;
	}

	@Override
	public IntrestData getIntrestDataById(int id) {
		// TODO Auto-generated method stub
		return intrestRepo.getIntrestDataById(id);
	}
	
	 public Page<ExcelReader> getProducts(int pageNo, int pageSize) {
	        Pageable pageable = PageRequest.of(pageNo, pageSize);
	        return excelReaderRepo.findAll(pageable);
	    }


}
