package com.tarena.dao.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tarena.dao.DAOException;
import com.tarena.entity.Admin;
import com.tarena.util.DBUtil;

public class LoginDAOImpl implements ILoginDAO{

	public Admin findByCode(String adminCode) throws DAOException {
		if(adminCode==null || adminCode.length()==0){
			return null;
		}
		String sql = "select * from admin_info " +
				"where admin_code=?";
		Connection conn = DBUtil.getConnection();
		try {
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.setString(1, adminCode);
			ResultSet rs = prep.executeQuery();
			while(rs.next()){
				return createAdmin(rs);
			}
		} catch (SQLException e) {
			throw new DAOException("查询数据失败",e);
		}finally{
			DBUtil.closeConnection();
		}
		return null;
	}
	//Shift + Alt + m
	private Admin createAdmin(ResultSet rs) throws SQLException {
		Admin a = new Admin();
		a.setId(rs.getInt("id"));
		a.setAdmin_code(rs.getString("admin_code"));
		a.setEmail(rs.getString("email"));
		a.setName(rs.getString("name"));
		a.setPassword(rs.getString("password"));
		a.setTelephone(rs.getString("telephone"));
		a.setEnrolldate(rs.getDate("enrolldate"));
		return a;
	}
	public static void main(String[] args) throws DAOException {
		ILoginDAO dao = new LoginDAOImpl();
		Admin a = dao.findByCode("lhh");
		System.out.println(a.getId()+" "+a.getEmail());
	}

}
