package com.tarena.action.cost;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.cost.ICostDAO;
import com.tarena.entity.Cost;

public class UpdateCostAction {
	
	//input
	private Cost cost;
	
	public String execute(){
		ICostDAO dao = DAOFactory.getCostDAO();
		try {
			dao.updateCost(cost);
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}
	
}
