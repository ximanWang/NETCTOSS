package com.tarena.dao.login;

import com.tarena.dao.DAOException;
import com.tarena.entity.Admin;

public interface ILoginDAO {
	/**
	 * 根据登录账号查询管理员
	 * @param adminCode 账号
	 * @return
	 * @throws DAOException
	 */
	Admin findByCode(String adminCode) throws DAOException;
}
