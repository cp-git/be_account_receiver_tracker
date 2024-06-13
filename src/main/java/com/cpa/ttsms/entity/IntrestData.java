package com.cpa.ttsms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "intrest_data")
public class IntrestData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "setup_percent")
	private Double setup_percent;
	
	@Column(name = "finance_percent")
	private Double finance_percent;
	
	
	@Column(name = "instrest_rate")
	private Double instrest_rate;
	
	
	@Column(name = "credited_days")
	private Integer credited_days;


	public IntrestData(int id, Double setup_percent, Double finance_percent, Double instrest_rate,
			Integer credited_days) {
		super();
		this.id = id;
		this.setup_percent = setup_percent;
		this.finance_percent = finance_percent;
		this.instrest_rate = instrest_rate;
		this.credited_days = credited_days;
	}
	
	
	


	public IntrestData() {
		super();
		// TODO Auto-generated constructor stub
	}





	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Double getSetup_percent() {
		return setup_percent;
	}


	public void setSetup_percent(Double setup_percent) {
		this.setup_percent = setup_percent;
	}


	public Double getFinance_percent() {
		return finance_percent;
	}


	public void setFinance_percent(Double finance_percent) {
		this.finance_percent = finance_percent;
	}


	public Double getInstrest_rate() {
		return instrest_rate;
	}


	public void setInstrest_rate(Double instrest_rate) {
		this.instrest_rate = instrest_rate;
	}


	public Integer getCredited_days() {
		return credited_days;
	}


	public void setCredited_days(Integer credited_days) {
		this.credited_days = credited_days;
	}





	@Override
	public String toString() {
		return "IntrestData [id=" + id + ", setup_percent=" + setup_percent + ", finance_percent=" + finance_percent
				+ ", instrest_rate=" + instrest_rate + ", credited_days=" + credited_days + "]";
	}
	
	
	
	
	

}
