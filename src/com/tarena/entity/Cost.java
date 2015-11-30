package com.tarena.entity;

import java.io.Serializable;
import java.sql.Date;

public class Cost implements Serializable{
	
	private Integer id;
	private String cost_name;
	private Integer base_duration;
	private Double base_cost;
	private Double unit_cost;
	private String status;
	private String descr;
	private Date creat_date;
	private Date start_date;
	private String cost_type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCost_name() {
		return cost_name;
	}
	public void setCost_name(String cost_name) {
		this.cost_name = cost_name;
	}
	public Integer getBase_duration() {
		return base_duration;
	}
	public void setBase_duration(Integer base_duration) {
		this.base_duration = base_duration;
	}
	public Double getBase_cost() {
		return base_cost;
	}
	public void setBase_cost(Double base_cost) {
		this.base_cost = base_cost;
	}
	public Double getUnit_cost() {
		return unit_cost;
	}
	public void setUnit_cost(Double unit_cost) {
		this.unit_cost = unit_cost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Date getCreat_date() {
		return creat_date;
	}
	public void setCreat_date(Date creat_date) {
		this.creat_date = creat_date;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public String getCost_type() {
		return cost_type;
	}
	public void setCost_type(String cost_type) {
		this.cost_type = cost_type;
	}      
	
}
