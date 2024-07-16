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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	 
	 ExcelReader findById(int id);
	 
	 List<ExcelReader> findAllBydueDateBetween(LocalDate startDate, LocalDate endDate);
	 
	 
	  List<ExcelReader> findAllByOrderByInvoiceAddedDateDesc();
	  List<ExcelReader> findAllByOrderByInvoiceDateAsc();
	  
//	  List<ExcelReader> findAllByOrderByInvoiceDateAscInvoiceAddedDateDesc();
	  
	 
	  List<ExcelReader> findByStatusDaysOrderByInvoiceDateAsc(int statusDays);
	  
	  	
	  List<ExcelReader> findAllByinvoiceDateBetweenAndStatusDays(LocalDate startDate, LocalDate endDate , int statusDay);
	  List<ExcelReader> findAllByPaidDateBetweenAndStatusDays(LocalDate startDate, LocalDate endDate , int statusDay);
	  List<ExcelReader> findAllBySecondPaidDateBetweenAndStatusDays(LocalDate startDate, LocalDate endDate , int statusDay);
	  List<ExcelReader> findAllByRecdDateBetweenAndStatusDays(LocalDate startDate, LocalDate endDate , int statusDay);

	  
	  List<ExcelReader> findAllByPaidDateBetween(LocalDate startDate , LocalDate endDate);
	  List<ExcelReader> findAllByRecdDateBetween(LocalDate startDate , LocalDate endDate);
	  List<ExcelReader> findAllByInvoiceDateBetween(LocalDate startDate , LocalDate endDate);
	  
	  
	  List<ExcelReader> findAllByPaidDateBetweenAndRecdDateIsNull(LocalDate startDate, LocalDate endDate);
	  
	// By order
		List<ExcelReader> findAllByInvoiceDateBetweenOrderByInvoiceDateDesc(LocalDate startDate, LocalDate endDate);
		List<ExcelReader> findAllByPaidDateBetweenOrderByPaidDateDesc(LocalDate startDate, LocalDate endDate);
		    List<ExcelReader> findAllByRecdDateBetweenOrderByRecdDateDesc(LocalDate startDate, LocalDate endDate);
		    List<ExcelReader> findAllByPaidDateBetweenAndRecdDateIsNullOrderByPaidDateDesc(LocalDate startDate, LocalDate endDate);
		    List<ExcelReader> findAllBySecondPaidDateBetweenAndStatusDaysOrderBySecondPaidDateDesc(LocalDate startDate, LocalDate endDate, int statusDays);
		
		    
		    Page<ExcelReader> findBystatusDays(int statusdays,Pageable pagable);

}
