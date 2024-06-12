package com.cpa.ttsms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cpa.ttsms.entity.IntrestData;

public interface IntrestRepo  extends JpaRepository<IntrestData, Integer>  {
	
	
	@Query(value = "Select * from setup_data where id=?1", nativeQuery = true)
	public IntrestData getSetupDataByID(int id);

}
