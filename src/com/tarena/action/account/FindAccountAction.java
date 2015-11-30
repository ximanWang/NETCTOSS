package com.tarena.action.account;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.account.IAccountDAO;
import com.tarena.entity.Account;

public class FindAccountAction {
	
	//input
	private String idcardNo;
	private String realName;
	private String loginName;
	private String status;
	int page = 1;
	int pageSize;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	//output
	private List<Account> accounts;
	private int totalPage;

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public String execute(){
		IAccountDAO dao = DAOFactory.getAccountDAO();
		try {
			accounts = dao.findByCondition(
					idcardNo, realName,
					loginName, status,
					page, pageSize);
			totalPage = dao.findTotalPage(
					idcardNo, realName,
					loginName, status, pageSize);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
		
	}
}
