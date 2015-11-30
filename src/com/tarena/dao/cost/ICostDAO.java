package com.tarena.dao.cost;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Cost;

public interface ICostDAO {
	List<Cost> findAll() throws DAOException;
	
	/**
	 * 分页查询资费数据
	 * @param page 当前页
	 * @param pageSize 页容量
	 * @return
	 * @throws DAOException
	 */
	List<Cost> findByPage(int page, int pageSize) throws DAOException;
	
	/**
	 * 查询总页数
	 * @param pageSize 页容量
	 * @return
	 * @throws DAOException
	 */
	int findTotalPage(int pageSize) throws DAOException;
	
	/**
	 * 根據id刪除資費数据
	 * @param id
	 * @throws DAOException
	 */
	void deleteCost(int id) throws DAOException;
	
	/**
	 * 根据名称查信息
	 * @param name
	 * @return
	 * @throws DAOException
	 */
	Cost findByName(String name) throws DAOException;
	
	/**
	 * 新增一条资费数据
	 * @param cost
	 * @throws DAOException
	 */
	void addCost(Cost cost) throws DAOException;
	
	/**
	 * 根据id查询id
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	Cost findById(int id) throws DAOException;
	
	/**
	 * 更新
	 * @param cost
	 * @throws DAOException
	 */
	void updateCost(Cost cost) throws DAOException;
}
