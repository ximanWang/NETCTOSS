package com.tarena.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.util.DBUtil;
import com.tarena.vo.ServiceVO;

public class ServiceDAOImpl implements IServiceDAO {
	
	public static void main(String[] args) throws Exception{
		IServiceDAO dao = new ServiceDAOImpl();
		List<ServiceVO> vos = dao.findByCondition(null, null, null, null, 2, 2);
		for(ServiceVO vo : vos){
			System.out.println(vo.getId()+" "+vo.getCostDeser());
		}
		int pages = dao.findTotalPage(null, null, null, null, 3);
		System.out.println(pages);
	}

	public List<ServiceVO> findByCondition(
			String osUserName,
			String unixHost,
			String idcardNo,
			String status, 
			int page,
			int pageSize)
			throws DAOException {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from(");
		sb.append(" select s.*,rownum r,a.idcard_no,a.real_name,c.name,c.descr from service s ");
		sb.append(" inner join account a on s.account_id = a.id ");
		sb.append("inner join cost c on s.cost_id = c.id ");
		sb.append(" where 1=1");
		if(osUserName != null
				&& osUserName.length()>0){
			sb.append("and s.os_username=? ");
			params.add(osUserName);
		}
		if(unixHost!=null
				&& unixHost.length()>0){
			sb.append(" and s.unix_host=? ");
			params.add(unixHost);
		}
		if(idcardNo != null
			&&idcardNo.length()>0){
			sb.append(" and a.idcard_no=? ");
			params.add(idcardNo);
		}
		if(status != null
				&& !status.equals("-1")){
			sb.append(" and s.status=? ");
			params.add(status);
		}
		sb.append("and rownum<?");
		int nextMin = page*pageSize+1;
		params.add(nextMin);
		sb.append(" )where r>?");
		int lastMax = (page-1)*pageSize;
		params.add(lastMax);
		
		List<ServiceVO> services = new ArrayList<ServiceVO>();
		Connection conn = DBUtil.getConnection();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sb.toString());
			for(int i=0; i<params.size(); i++){
				ps.setObject(i+1, params.get(i));
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ServiceVO vo = createServoiceVO(rs);
				services.add(vo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("查询业务账号失败",e);
		}finally{
			DBUtil.closeConnection();
		}
		
		return services;
	}
	
	private ServiceVO createServoiceVO(ResultSet rs) throws Exception{
		ServiceVO vo = new ServiceVO();
		vo.setId(rs.getInt("id"));
		vo.setAccountId(rs.getInt("account_id"));
		vo.setUnixHost(rs.getString("unix_host"));
		vo.setOsUserName(rs.getString("os_username"));
		vo.setLoginPassword(rs.getString("login_passwd"));
		vo.setStatus(rs.getString("status"));
		vo.setCreateDate(rs.getDate("create_date"));
		vo.setPauseDate(rs.getDate("pause_date"));
		vo.setClostDate(rs.getDate("close_date"));
		vo.setCostId(rs.getInt("cost_id"));
		vo.setIdcardNo(rs.getString("idcard_no"));
		vo.setRealName(rs.getString("real_name"));
		vo.setCostName(rs.getString("name"));
		vo.setCostDeser(rs.getString("descr"));
		
		return vo;
	}

	public int findTotalPage(
			String osUserName,
			String unixHost,
			String idcardNo,
			String status,
			int pageSize) throws DAOException {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from service s ");
		sb.append("inner join account a on s.account_id = a.ID ");
		sb.append("where 1=1 ");
		if(osUserName != null
				&& osUserName.length()>0){
			sb.append("and s.os_username=? ");
			params.add(osUserName);
		}
		if(unixHost!=null
				&& unixHost.length()>0){
			sb.append(" and s.unix_host=? ");
			params.add(unixHost);
		}
		if(idcardNo != null
			&&idcardNo.length()>0){
			sb.append(" and a.idcard_no=? ");
			params.add(idcardNo);
		}
		if(status != null
				&& !status.equals("-1")){
			sb.append(" and s.status=? ");
			params.add(status);
		}
		Connection conn = DBUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			for(int i=0; i<params.size(); i++){
				pstmt.setObject(i+1, params.get(i));
			}
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				int rows = rs.getInt(1);
				if(rows % pageSize == 0){
					return rows/pageSize;
				}else{
					return rows/pageSize+1;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("查询总页数失败",e);
		}finally{
			DBUtil.closeConnection();
		}
		return 0;
	}

}
