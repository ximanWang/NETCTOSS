package com.tarena.dao.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Account;
import com.tarena.util.DBUtil;

public class AccountDAOImpl implements IAccountDAO{

	public List<Account> findByCondition(
			String idcardNo,
			String realName,
			String loginName, 
			String status,
			int page,
			int pageSize)throws DAOException {
		//用来封住查询条件的值
		List<Object> params = new ArrayList<Object>();
		StringBuffer sb = buildFIndSQL(
				idcardNo, realName,
				loginName, status,
				page, pageSize,
				 params);
		List<Account> accs = new ArrayList<Account>();
		Connection conn = DBUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			for(int i=0; i<params.size(); i++){
				pstmt.setObject(i+1, params.get(i));
			}
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Account a = createAccount(rs);
				accs.add(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("查询账务账户失败",e);
		}finally{
			DBUtil.closeConnection();
		}
		
		return accs;
	}

	private StringBuffer buildFIndSQL(
			String idcardNo, 
			String realName,
			String loginName,
			String status,
			int page,
			int pageSize,
			List<Object> params) {
		//动态拼SQL，主要是处理其条件 shift+alt+M
		StringBuffer sb = new StringBuffer();
		sb.append("select * from (");
		sb.append("select a.*, rownum r from account a where 1=1 ");
		if(idcardNo!=null && idcardNo.length()>0){
			sb.append("and idcard_no=? ");
			params.add(idcardNo);
		}
		if(realName!=null && realName.length()>0){
			sb.append("and real_name=? ");
			params.add(realName);
		}
		if(loginName!=null && loginName.length()>0){
			sb.append("and login_name=? ");
			params.add(loginName);
		}
		if(status!=null && !status.equals("-1")){
			//下拉选默认是有值的，下拉选不为空或者不是全部选项时我们才拼条件进去
			sb.append("and status=? ");
			params.add(status);
		}
		sb.append("and rownum<?");
		int nextMin = page*pageSize+1;
		params.add(nextMin);
		
		sb.append(")where r>?");
		int lastMax = (page-1)*pageSize;
		params.add(lastMax);
		
//		System.out.println(sb.toString());
		return sb;
	}

	private Account createAccount(ResultSet rs) throws Exception {
		Account a = new Account();
		a.setId(rs.getInt("id"));
		a.setRecommenderId(rs.getInt("recommender_id"));
		a.setLoginName(rs.getString("login_name"));
		a.setLoginPassword(rs.getString("login_passwd"));
		a.setStatus(rs.getString("status"));
		a.setCreateDate(rs.getDate("create_date"));
		a.setPauseDate(rs.getDate("pause_date"));
		a.setCloseDate(rs.getDate("close_date"));
		a.setRealName(rs.getString("real_name"));
		a.setIdcardNo(rs.getString("idcard_no"));
		a.setBirthdate(rs.getDate("birthdate"));
		a.setGender(rs.getString("gender"));
		a.setOccupation(rs.getString("occupation"));
		a.setTelephone(rs.getString("telephone"));
		a.setEmail(rs.getString("email"));
		a.setMailaddress(rs.getString("mailaddress"));
		a.setZipcode(rs.getString("zipcode"));
		a.setQq(rs.getString("qq"));
		a.setLastLoginTime(rs.getDate("last_login_time"));
		a.setLastLoginIp(rs.getString("last_login_ip"));
		return a;
	}
	public static void main(String[] args) throws Exception{
		IAccountDAO dao = new AccountDAOImpl();
//		List<Account> accs = 
//				dao.findByCondition(null, null, null, null,2,5);
//		for(Account acc : accs){
//			System.out.println(acc.getId()+","+acc.getIdcardNo()+","+acc.getRealName());
//		}
//		int totalPage = dao.findTotalPage(null, null, null, null, 5);
//		System.out.println(totalPage);
		
//		dao.startAccount(1018);
//		Account account = dao.findById(1018);
//		System.out.println(account.getStatus());
//		Account acc = dao.findByIdcardNo("330682196903190613");
//		System.out.println(acc.getId());
		Account account = new Account();
		account.setEmail("583823@qq.com");
		account.setLoginName("dkfj");
		account.setIdcardNo("130281199207271123");
		account.setLoginPassword("dfad");
		account.setRealName("小米");
		account.setTelephone("41551");
		dao.addAccount(account);
	}

	public int findTotalPage(String idcardNo, String realName,
			String loginName, String status, int pageSize) throws DAOException {
		//拼查询总行数的sql
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from account where 1=1");
		List<Object> params = new ArrayList<Object>();
		if(idcardNo!=null && idcardNo.length()>0){
			sb.append("and idcard_no=? ");
			params.add(idcardNo);
		}
		if(realName!=null && realName.length()>0){
			sb.append("and real_name=? ");
			params.add(realName);
		}
		if(loginName!=null && loginName.length()>0){
			sb.append("and login_name=? ");
			params.add(loginName);
		}
		if(status!=null && !status.equals("-1")){
			sb.append("and status=? ");
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
				if(rows%pageSize == 0){
					return rows/pageSize;
				}else{
					return rows/pageSize + 1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("查询总页数失败！",e);
		}
		
		return 0;
	}

	public void startAccount(int id) throws DAOException {
		String sql="update account set status='0',pause_date=null where id=?";
		Connection conn = DBUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DAOException("开通账务账号失败！",e);
		}finally{
			DBUtil.closeConnection();
		}
	}

	public Account findById(int id) throws DAOException {
		String sql="select * from account where id=?";
		Connection conn = DBUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				 return createAccount(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("根据id查询账务账号信息失败！",e);
		}finally{
			DBUtil.closeConnection();
		}
		return null;
	}

	public void delelteAccount(int id) throws DAOException {
		String sql="update account set status='2'," +
				"close_date=sysdate where id=?";
		Connection conn = DBUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new DAOException("删除账务账单失败！",e);
		}finally{
			DBUtil.closeConnection();
		}
		
	}

	public void pauseAccount(int id) throws DAOException {
		String sql="update account set status='1',pause_date=sysdate where id=?";
		Connection conn = DBUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DAOException("暂停账务账号失败！",e);
		}finally{
			DBUtil.closeConnection();
		}
		
	}

	public Account findByIdcardNo(String idcardNo) throws DAOException {
		if(idcardNo == null){
			return null;
		}
		String sql="select * from account where idcard_no=?";
		Connection conn = DBUtil.getConnection();
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, idcardNo);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return createAccount(rs);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection();
		}
		
		return null;
	}

	public void addAccount(Account account) throws DAOException {
		if(account == null){
			return;
		}
		String sql="insert into account values(seq_account.nextval,?,?,?,'1',sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection conn = DBUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, account.getRecommenderId());
			pstmt.setObject(2, account.getLoginName());
			pstmt.setObject(3, account.getLoginPassword());
			pstmt.setObject(4, account.getPauseDate());
			pstmt.setObject(5, account.getCloseDate());
			pstmt.setObject(6, account.getRealName());
			pstmt.setObject(7, account.getIdcardNo());
			pstmt.setObject(8, account.getBirthdate());
			pstmt.setObject(9, account.getGender());
			pstmt.setObject(10, account.getOccupation());
			pstmt.setObject(11, account.getTelephone());
			pstmt.setObject(12, account.getEmail());
			pstmt.setObject(13, account.getMailaddress());
			pstmt.setObject(14, account.getZipcode());
			pstmt.setObject(15, account.getQq());
			pstmt.setObject(16, account.getLastLoginTime());
			pstmt.setObject(17, account.getLastLoginIp());
			pstmt.executeUpdate();
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new DAOException("账务账号插入失败！",e);
		}
		
	}

}
