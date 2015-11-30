package com.tarena.dao;

import com.tarena.dao.account.AccountDAOImpl;
import com.tarena.dao.account.IAccountDAO;
import com.tarena.dao.cost.CostDAOImpl;
import com.tarena.dao.cost.ICostDAO;
import com.tarena.dao.login.ILoginDAO;
import com.tarena.dao.login.LoginDAOImpl;

public class DAOFactory {
	private static ICostDAO costDAO = 
			new CostDAOImpl();
	private static ILoginDAO loginDAO = 
			new LoginDAOImpl();
	private static IAccountDAO accountDAO = 
			new AccountDAOImpl();
	/**
	 * 返回ICostDAO对象
	 * @return
	 */
	public static ICostDAO getCostDAO(){
		return costDAO;
	}
	/**
	 * 返回ILoginDAO对象
	 * @return
	 */
	public static ILoginDAO getLoginDAO(){
		return loginDAO;
	}
	/**
	 * 返回IAccountDAO对象
	 * @return
	 */
	public static IAccountDAO getAccountDAO(){
		return accountDAO;
	}
}
