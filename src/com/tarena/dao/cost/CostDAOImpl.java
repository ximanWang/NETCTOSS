package com.tarena.dao.cost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Cost;
import com.tarena.util.DBUtil;

public class CostDAOImpl implements ICostDAO{

	public List<Cost> findAll() throws DAOException {
		List<Cost> list = new ArrayList<Cost>();
		String sql = "select * from COST";
		Connection conn = DBUtil.getConnection();
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Cost c = createCost(rs);
				list.add(c);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("查询资费数据失败",e);
		}finally{
			DBUtil.closeConnection();
		}
		return list;
	}
	private Cost createCost(ResultSet rs) throws SQLException {
		Cost c = new Cost();
		c.setId(rs.getInt("id"));
		c.setCost_name(rs.getString("name"));
		c.setBase_duration(rs.getInt("base_duration"));
		c.setBase_cost(rs.getDouble("base_cost"));
		c.setUnit_cost(rs.getDouble("unit_cost"));
		c.setStatus(rs.getString("status"));
		c.setDescr(rs.getString("descr"));
		c.setCreat_date(rs.getDate("creatime"));
		c.setStart_date(rs.getDate("startime"));
		c.setCost_type(rs.getString("cost_type"));
		return c;
	}
	public static void main(String[] args) throws Exception{
		ICostDAO dao = new CostDAOImpl();
//		List<Cost> list = dao.findAll();
//		List<Cost> list = dao.findByPage(1, 5);		
//		for(Cost  c : list){
//			System.out.println(c.getId()+" "+c.getCost_name()+" "+c.getCost_type());
//		}
//		System.out.println(dao.findTotalPage(5));
//		dao.deleteCost(6);
		
//		Cost cost = dao.findByName("6.9元套餐");
//		System.out.println(cost.getId()+" "+cost.getCost_name());
		
//		Cost cost = new Cost();
//		cost.setCost_name("aaa");
//		cost.setBase_duration(1000);
//		cost.setBase_cost(10.00);
//		cost.setDescr("dfadf");
//		cost.setCost_type("0");
//		cost.setUnit_cost(5.00);
//		dao.addCost(cost);
		
		dao.deleteCost(1003);
		
//		Cost cost = dao.findById(1007);
//		cost.setCost_name("fjak");
//		cost.setBase_cost(10.00);
//		dao.updateCost(cost);
//		System.out.println(cost.getCost_name()+","+cost.getBase_cost());
//		System.out.println(cost.getCost_name()+","+cost.getBase_duration());
//		dao.deleteCost(1);
	}
	public List<Cost> findByPage(int page, int pageSize) throws DAOException {
		/*
		 * 小于下一页的最小值；
		 * 大于上一页的最大值
		 */
		String sql="select * from (" +
				"select c.*,rownum r from cost c" +
				" where rownum<? )where r>?";
		Connection conn = DBUtil.getConnection();
		List<Cost> list = new ArrayList<Cost>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//小于下一页最小值
			int nextMin = page*pageSize + 1;
			pstmt.setInt(1, nextMin);
			//大于上一页最大值
			int lastMax = (page-1)*pageSize;
			pstmt.setInt(2, lastMax);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Cost c = createCost(rs);
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("分页查询失败！",e);
		}finally{
			DBUtil.closeConnection();
		}
		return list;
	}
	public int findTotalPage(int pageSize) throws DAOException {
		String sql = "select count(*) from cost";
		Connection conn = DBUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				int rows = rs.getInt(1);
				if(rows%pageSize == 0){
					return rows/pageSize;
				}else{
					return rows/pageSize+1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("查询总页数失败！",e);
		}		
		return 0;
	}
	public void deleteCost(int id) throws DAOException {
			String sql = "delete from cost where id=?";
			Connection con = DBUtil.getConnection();
			try {
				con.setAutoCommit(false);
				PreparedStatement ps =
					con.prepareStatement(sql);
				ps.setInt(1,id);
				int i = ps.executeUpdate();
				System.out.println(i);
				con.commit();
			} catch (SQLException e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				throw new DAOException(
						"删除资费数据失败！",e);
			} finally {
				DBUtil.closeConnection();
			}
		}
	public Cost findByName(String name) throws DAOException {
		if(name == null){
			return null;
		}
		String sql = "select * from cost where name=?";
		Connection conn = DBUtil.getConnection();
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement(sql);
			prep.setString(1, name);
			ResultSet rs = prep.executeQuery();
			if(rs.next()){
				Cost cost = createCost(rs);
				return cost;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("根据名字查询数据失败",e);
		}finally{
			DBUtil.closeConnection();
		}
		return null;
	}
	public void addCost(Cost cost) throws DAOException {
		if(cost == null){
			return;
		}
		String sql = "insert into cost values(cost_seq.nextval,?,?,?,?,'1',?,sysdate,null,?)";
		Connection conn = DBUtil.getConnection();
		PreparedStatement prep;
		try {
			//设置不自动提交
			conn.setAutoCommit(false);
			prep = conn.prepareStatement(sql);
			prep.setString(1, cost.getCost_name());
			prep.setObject(2, cost.getBase_duration());
			prep.setObject(3, cost.getBase_cost());
			prep.setObject(4, cost.getUnit_cost());
			prep.setString(5, cost.getDescr());
			prep.setObject(6, cost.getCost_type());
			prep.executeUpdate();
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new DAOException("新增资费数据失败",e);
		}finally{
			DBUtil.closeConnection();
		}
		
	}
	public Cost findById(int id) throws DAOException {
			String sql = "select * from cost " +
					"where id=?";
			Connection con = DBUtil.getConnection();
			try {
				PreparedStatement ps = 
					con.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					Cost c = createCost(rs);
					return c;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException(
						"根据ID查询资费数据失败！", e);
			} finally {
				DBUtil.closeConnection();
			}
			
			return null;
		}
	public void updateCost(Cost cost) throws DAOException {
		if(cost == null){
			return;
		}
		String sql = "update cost set name=?,base_duration=?," +
				"base_cost=?,unit_cost=?,descr=?,cost_type=?" +
				"where id=?";
		Connection conn = DBUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.setObject(1, cost.getCost_name());
			prep.setObject(2, cost.getBase_duration());
			prep.setObject(3, cost.getBase_cost());
			prep.setObject(4, cost.getUnit_cost());
			prep.setObject(5, cost.getDescr());
			prep.setObject(6, cost.getCost_type());
			prep.setObject(7, cost.getId());
			prep.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DAOException("更新数据失败",e);
		}finally{
			DBUtil.closeConnection();
		}
		
	}
}
