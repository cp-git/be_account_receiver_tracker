/**
 * @author - Code Generator
 * @createdOn 07/06/2024
 * @Description Controller class for excelReader
 * 
 */

package com.cpa.ttsms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cpa.ttsms.entity.ExcelReader;
import com.cpa.ttsms.entity.IntrestData;
import com.cpa.ttsms.exception.CPException;
import com.cpa.ttsms.helper.ResponseHandler;
import com.cpa.ttsms.service.ExcelReaderService;

@RestController
@CrossOrigin
@RequestMapping("/excel")
public class ExcelReaderController {

	@Autowired
	private ExcelReaderService excelReaderService;;

	private ResourceBundle resourceBunde;
	private static Logger logger;

	ExcelReaderController() {
		resourceBunde = ResourceBundle.getBundle("ErrorMessage", Locale.US);
		logger = Logger.getLogger(ExcelReaderController.class);
	}

	
	   @PostMapping("/import")
	    public ResponseEntity<String> importInvoiceDetails(@RequestParam("file") MultipartFile file) {
	        if (file.isEmpty()) {
	            return ResponseEntity.badRequest().body("Please upload a file");
	        }

	        try {
	            excelReaderService.readExcelData(file.getInputStream());
	            return ResponseEntity.ok("File uploaded successfully");
	        } catch (IOException e) {
	            logger.error("Error reading the Excel file", e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
	        }
	    }
	   
		@GetMapping("/getInvoice/{invoiceNo}")
		public ExcelReader getDetailsByInvoiceData(@PathVariable("invoiceNo") String invoiceNo)
				throws CPException {

			ExcelReader Excelreader = null;
			try {
				Excelreader=excelReaderService.getDataByInvoiceId(invoiceNo);
				
				System.out.println(Excelreader);
				
			
			} catch (Exception ex) {
				logger.error("Failed getting all emp : " + ex.getMessage());
				
			}
			return Excelreader;
		}
		
		
		@GetMapping("/date-range")
	    public List<ExcelReader> getExcelReadersByDateRange(
	            @RequestParam("startDate") String startDate,
	            @RequestParam("endDate") String endDate) {
	        LocalDate start = LocalDate.parse(startDate);
	        LocalDate end = LocalDate.parse(endDate);
	        return excelReaderService.getExcelReadersByDateRange(start, end);
	    }
		
		
		@GetMapping("/due-date-range")
	    public List<ExcelReader> getExcelReadersByDueDateRange(
	            @RequestParam("startDate") String startDate,
	            @RequestParam("endDate") String endDate) {
	        LocalDate start = LocalDate.parse(startDate);
	        LocalDate end = LocalDate.parse(endDate);
	        return excelReaderService.getExcelReadersByDueDateRange(start, end);
	    }

		
		
		@GetMapping("/getAllData")
	    public List<ExcelReader> GetAllData() {
	        
	        return excelReaderService.GetAllData();
	    }
	  
	
	
}
