package com.tarena.action.account;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.account.IAccountDAO;

public class StartAccountAction {
	
	//input
	private int id;
	
	//output
	private boolean pass;//是否更新成功

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}
	
	public String execute(){
		IAccountDAO dao = DAOFactory.getAccountDAO();
		try {
			dao.startAccount(id);
		} catch (DAOException e) {
			e.printStackTrace();
			pass = false;
		}
		pass = true;
		return "success";
	}
}
