/**
 * @author  - Code Generator
 * @createdOn -  07/06/2024
 * @Description Entity class for ExcelReader
 * 
 */

package com.cpa.ttsms.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cpa.ttsms.entity.ExcelReader;
import com.cpa.ttsms.entity.IntrestData;

@Repository
public interface ExcelReaderRepo extends JpaRepository<ExcelReader, Integer> {

//	public ExcelReader findByExcelReaderlocationId(String locationid);
//
//	public List<Object> findByExcelReaderIsActiveTrue();
//
//	@Transactional
//	@Modifying
//	@Query(value = "UPDATE joblocation SET is_active=false WHERE locationid = ?1", nativeQuery = true)
//	public int deleteExcelReaderBylocationId(String locationid);
	
	@Query(value = "select * from invoicedetails WHERE invoiceno = ?1", nativeQuery = true)
	public ExcelReader getAllDataByInvoiceNumber(String invoiceNo);
	
	 List<ExcelReader> findAllByinvoiceDateBetween(LocalDate startDate, LocalDate endDate);
	 
	 ExcelReader findByInvoiceNo(String inovoiceNo);
	 
	 List<ExcelReader> findAllBydueDateBetween(LocalDate startDate, LocalDate endDate);
	 
	 
	  List<ExcelReader> findAllByOrderByInvoiceAddedDateDesc();
	  
	  
	  List<ExcelReader> findByStatusDays(int statusDays);
	  
		
	

}
