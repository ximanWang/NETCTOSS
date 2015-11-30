package com.tarena.dao.service;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.vo.ServiceVO;

public interface IServiceDAO {
	/**
	 * 查询业务账号
	 * @param osUserName 账号
	 * @param unixHost ip
	 * @param idcardNo 身份证
	 * @param status 状态
	 * @param page 页码
	 * @param pageSize 页容量
	 * @return
	 * @throws DAOException
	 */
	List<ServiceVO> findByCondition(
			String osUserName,
			String unixHost,
			String idcardNo,
			String status,
			int page,
			int pageSize
			)throws DAOException;
	
	/**
	 * 查询总页数
	 * @param osUserName
	 * @param unixHost
	 * @param idcardNo
	 * @param status
	 * @param pageSize
	 * @return
	 * @throws DAOException
	 */
	int findTotalPage(
			String osUserName,
			String unixHost,
			String idcardNo,
			String status,
			int pageSize
			) throws DAOException;
}
