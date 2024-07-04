package com.cpa.ttsms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cpa.ttsms.entity.IntrestData;

public interface IntrestRepo  extends JpaRepository<IntrestData, Integer>  {
	
	
	@Query(value = "select * from intrest_data", nativeQuery = true)
	public IntrestData getSetupDataByID();

	
	public IntrestData getIntrestDataById(int id);
}
