package com.tarena.action.service;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.account.IAccountDAO;
import com.tarena.entity.Account;

public class SearchAccountAction {
	
	//input
	private String idcardNo;
	
	//output
	private Account account;

	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public String execute(){
		IAccountDAO dao = DAOFactory.getAccountDAO();
		try {
			account = dao.findByIdcardNo(idcardNo);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
}
