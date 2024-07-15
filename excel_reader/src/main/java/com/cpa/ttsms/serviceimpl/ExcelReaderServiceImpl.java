package com.cpa.ttsms.serviceimpl;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    private IntrestRepo intrestRepo;

    private static Logger logger = Logger.getLogger(ExcelReaderServiceImpl.class);

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

                    invoiceDetails.setInvoiceNo(getCellValueAsString(row.getCell(0)));
                    invoiceDetails.setInvoiceDate(convertToLocalDate(row.getCell(1)));
                    invoiceDetails.setInvoiceAmt(getCellValueAsDouble(row.getCell(2)));
                    
                    // Set additional fields as necessary
                    // For example, setting the companyId from the Excel sheet if available
                    invoiceDetails.setCompanyId(getCellValueAsLong(row.getCell(13))); // Assuming companyId is at cell index 13

                    // Validate mandatory fields
                    if (invoiceDetails.getInvoiceNo() == null || invoiceDetails.getInvoiceNo().isEmpty()) {
                        throw new IllegalArgumentException("InvoiceNo cannot be null or empty");
                    }

                    invoiceDetailsList.add(invoiceDetails);

                    // Calculate finance amount
                    Double financeAmount = invoiceDetails.getInvoiceAmt() * 0.9;

                    // Balance amount
                    Double balanceAmount = invoiceDetails.getInvoiceAmt() - financeAmount;

                    // Setup
                    Double setupAmount = financeAmount * 0.005;

                    // Interest rate
                    Double interestRate = financeAmount * 0.025;

                    // Paid amount
                    Double paidAmount = financeAmount - interestRate - setupAmount;

                    invoiceDetails.setFinancedAmount(financeAmount);
                    invoiceDetails.setBalAmt(balanceAmount);
                    invoiceDetails.setSetup(setupAmount);
                    invoiceDetails.setInterest(interestRate);
                    invoiceDetails.setPaidAmt(paidAmount);

                    LocalDate currentDate = invoiceDetails.getInvoiceDate();
                    // Set due date if necessary
                    // invoiceDetails.setDueDate(currentDate.plusDays(invoiceDetails.getCreditDays()));
                }
            }
        } catch (Exception e) {
            logger.error("Error reading Excel file", e);
        }

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

    private Long getCellValueAsLong(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC) {
            return null;
        }
        return (long) cell.getNumericCellValue();
    }

    @Override
    public ExcelReader getDataByInvoiceId(String invoiceNo) {
        return excelReaderRepo.getAllDataByInvoiceNumber(invoiceNo);
    }

    @Override
    public List<ExcelReader> getExcelReadersByDateRange(LocalDate startDate, LocalDate endDate) {
        return excelReaderRepo.findAllByinvoiceDateBetween(startDate, endDate);
    }

    @Override
    public List<ExcelReader> getExcelReadersByDueDateRange(LocalDate startDate, LocalDate endDate) {
        return excelReaderRepo.findAllBydueDateBetween(startDate, endDate);
    }

    @Override
    public List<ExcelReader> GetAllData() {
        return excelReaderRepo.findAllByOrderByInvoiceAddedDateDesc();
    }

    @Override
    public ExcelReader insertExcelReader(ExcelReader excelReader) {
        LocalDateTime today = LocalDateTime.now();

        IntrestData interestData = intrestRepo.getSetupDataByID();
        double financeRate = interestData.getFinance_percent();
        double setupRatePercent = interestData.getSetup_percent();
        double interestRatePercent = interestData.getInstrest_rate();

        Double financeAmount = excelReader.getInvoiceAmt() * financeRate / 100;
        Double balanceAmount = excelReader.getInvoiceAmt() - financeAmount;
        Double setupAmount = financeAmount * setupRatePercent / 100;
        Double interestRate = financeAmount * interestRatePercent / 100;
        Double paidAmount = financeAmount - interestRate - setupAmount;

        excelReader.setFinancedAmount(financeAmount);
        excelReader.setSetup(setupAmount);
        excelReader.setInterest(interestRate);
        excelReader.setPaidAmt(paidAmount);
        excelReader.setBalAmt(balanceAmount);
        excelReader.setInvoiceAddedDate(today);
        excelReader.setStatusDays(0);

        LocalDate currentDate = excelReader.getInvoiceDate();
        excelReader.setDueDate(currentDate.plusDays(interestData.getCredited_days()));
        excelReader.setCreditDays(interestData.getCredited_days());

        return excelReaderRepo.save(excelReader);
    }

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
                    excelReaderRepo.save(toUpdateDetails);
                } else {
                    allUpdated = false;
                }
            } catch (Exception e) {
                allUpdated = false;
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
                    toUpdateDetails.setRecdDate(today);
                    toUpdateDetails.setStatusDays(2);
                    excelReaderRepo.save(toUpdateDetails);
                } else {
                    allUpdated = false;
                }
            } catch (Exception e) {
                allUpdated = false;
            }
        }
        return allUpdated;
    }

    @Override
    public boolean updateSecondDateAsTodaysDate(List<String> invoiceNumbers) {
        boolean allUpdated = true;
        LocalDate today = LocalDate.now();
        for (String invoiceNo : invoiceNumbers) {
            try {
                ExcelReader toUpdateDetails = excelReaderRepo.findByInvoiceNo(invoiceNo);
                if (toUpdateDetails != null) {
                    toUpdateDetails.setSecondPaidDate(today);
                    toUpdateDetails.setStatusDays(3);
                    excelReaderRepo.save(toUpdateDetails);
                } else {
                    allUpdated = false;
                }
            } catch (Exception e) {
                allUpdated = false;
            }
        }
        return allUpdated;
    }

    @Override
    public List<ExcelReader> getExcelReaderByStatusId(int statusDays) {
        return excelReaderRepo.findByStatusDays(statusDays);
    }

    @Override
    public List<ExcelReader> getInvoicesByRangeDatesOfInvoiceDateAndStatus(LocalDate startDate, LocalDate endDate, int status) {
        List<ExcelReader> result;
        if (status == 0) {
            result = excelReaderRepo.findAllByinvoiceDateBetweenAndStatusDays(startDate, endDate, status);
        } else if (status == 1) {
            result = excelReaderRepo.findAllByPaidDateBetweenAndStatusDays(startDate, endDate, status);
        } else if (status == 2) {
            result = excelReaderRepo.findAllByRecdDateBetweenAndStatusDays(startDate, endDate, status);
        } else if (status == 3) {
            result = excelReaderRepo.findAllBySecondPaidDateBetweenAndStatusDays(startDate, endDate, status);
        } else {
            result = new ArrayList<>(); // Return an empty list or handle this case as needed
        }
        return result;
    }
 



	@Override
	public List<ExcelReader> getExcelReaderByCompanyIdAndStatus(Long companyId, int statusDays) {
		// TODO Auto-generated method stub
		   return excelReaderRepo.findByCompanyIdAndStatusDays(companyId, statusDays);

	}

	@Override
	public List<ExcelReader> getExcelReaderByCompanyId(Long companyId) {
		// TODO Auto-generated method stub
		return excelReaderRepo.findByCompanyId(companyId);
		
	}
}
