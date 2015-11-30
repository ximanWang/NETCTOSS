package com.tarena.action.cost;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.cost.ICostDAO;
import com.tarena.entity.Cost;

public class ToUpdateCostAction {
	
	//input
	private int id;
	
	//output
	private Cost cost;
	
	public String execute(){
		ICostDAO dao = DAOFactory.getCostDAO();
		try {
			cost = dao.findById(id);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}
}
