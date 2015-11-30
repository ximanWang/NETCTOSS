package com.tarena.action.cost;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.cost.ICostDAO;
import com.tarena.entity.Cost;

public class CheckCostNameAction {
	
	//input
	private String name;//资费名
	
	//output
	private boolean repeat;//是否重复
	
	public String execute(){
		ICostDAO dao = DAOFactory.getCostDAO();
		Cost cost = null;
		try {
			 cost = dao.findByName(name);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		if(cost ==null){
			//没查到资费数据，说明没重复
			repeat = false;
		}else{
			//查到了，说明重复了
			repeat = true;
		}
		return "success";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
}
