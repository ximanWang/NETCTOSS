package com.tarena.vo;

import com.tarena.entity.Service;

/**
 * 用于封装业务账号查询结果的对象
 * @author ximan
 *
 */
public class ServiceVO extends Service{
	
	private String idcardNo;
	private String realName;
	private String costName;
	private String costDeser;
	public String getIdcardNo() {
		return idcardNo;
	}
	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCostName() {
		return costName;
	}
	public void setCostName(String costName) {
		this.costName = costName;
	}
	public String getCostDeser() {
		return costDeser;
	}
	public void setCostDeser(String costDeser) {
		this.costDeser = costDeser;
	}
}
