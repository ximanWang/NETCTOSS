package com.tarena.action.service;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.cost.ICostDAO;
import com.tarena.entity.Cost;

public class ToAddServiceAction {
	
	//output
	private List<Cost> costs;//用于构建资费下拉选

	public String execute(){
		ICostDAO costdao = DAOFactory.getCostDAO();
		try {
			costs = costdao.findAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	public List<Cost> getCosts() {
		return costs;
	}

	public void setCosts(List<Cost> costs) {
		this.costs = costs;
	}
	
	
}
