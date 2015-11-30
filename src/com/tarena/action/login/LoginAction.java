package com.tarena.action.login;

import com.tarena.action.BaseAction;
import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.login.ILoginDAO;
import com.tarena.entity.Admin;

public class LoginAction extends BaseAction{
	//input
	private String adminCode;
	private String password;
	private String userImageCode;//用户输入的验证码
	
	//output
	public String getUserImageCode() {
		return userImageCode;
	}
	public void setUserImageCode(String userImageCode) {
		this.userImageCode = userImageCode;
	}
	private String errMsg;
	public String getAdminCode() {
		return adminCode;
	}
	public void setAdminCode(String adminCode) {
		this.adminCode = adminCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String execute(){
		String imageCode = (String)session.get("imageCode");
		System.out.println(imageCode);
		System.out.println(userImageCode);
		if(!imageCode.equalsIgnoreCase(userImageCode)){
			errMsg = "验证码不正确，请重新输入！";
			return "fail"; 
		}
		ILoginDAO dao = DAOFactory.getLoginDAO();
		Admin admin = null;
		try {
			admin = dao.findByCode(adminCode);
		} catch (DAOException e) {
			e.printStackTrace();
			errMsg = "系统发生异常，请联系管理员";
			return "fail";
		}
		if(admin == null){
			//没有查到该管理员,失败
			errMsg = "账号不存在，请重新输入";
			return "fail";
		}else if(!admin.getPassword().equals(password)){
			//查到管理员，密码不对，失败
			errMsg = "密码不正确，请重新输入";
			return "fail";
		}else{
			//成功,將管理員信息記錄到session
			session.put("admin", admin);
			return "ok";
		}
	}
}
