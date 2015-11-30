package com.tarena.action.account;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.account.IAccountDAO;
import com.tarena.entity.Account;

public class AddAccountAction {
	
	//input
	private Account account;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public String execute(){
		IAccountDAO dao = DAOFactory.getAccountDAO();
		try {
			dao.addAccount(account);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
}
