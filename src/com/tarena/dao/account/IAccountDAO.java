package com.tarena.dao.account;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Account;

public interface IAccountDAO {
	
	/**
	 * 根据条件查询账务账号
	 * @param idcardNo 身份证
	 * @param realName 真实姓名
	 * @param loginName 登录名
	 * @param status 状态
	 * @param page 页码
	 * @param pageSize 页容量
	 * @return
	 * @throws DAOException
	 */
	List<Account> findByCondition(
			String idcardNo,
			String realName,
			String loginName,
			String status,
			int page,
			int pageSize)
			throws DAOException;
	/**
	 * 查询总页数
	 * @param idcardNo 身份证
	 * @param realName 真实姓名
	 * @param loginName 登录名
	 * @param status 状态
	 * @param pageSize 页容量
	 * @return
	 * @throws DAOException
	 */
	int findTotalPage(
			String idcardNo,
			String realName,
			String loginName,
			String status,
			int pageSize)
		    throws DAOException;
	
	/**
	 * 开通账务账号
	 * @param id
	 * @throws DAOException
	 */
	void startAccount(int id ) throws DAOException;
	
	/**
	 * 删除账务账号
	 * @param id
	 * @throws DAOException
	 */
	void delelteAccount(int id) throws DAOException;
	
	/**
	 * 暂停账务账号
	 * @param id
	 * @throws DAOException
	 */
	void pauseAccount(int id) throws DAOException;
	
	/**
	 * 根据id查询数据
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	Account findById(int id) throws DAOException;
	
	/**
	 * 根据身份证号码查询
	 * @param idcardNo
	 * @return
	 * @throws DAOException
	 */
	Account findByIdcardNo(String idcardNo) throws DAOException;
	
	void  addAccount(Account account) throws DAOException; 
}
