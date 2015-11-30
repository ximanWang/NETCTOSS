package com.tarena.action.cost;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.cost.ICostDAO;
import com.tarena.entity.Cost;

public class FindCostAction {
	
	//向页面输出查询到的资费数据
	private List<Cost> costs;
	
	private int pageSize = 5;//页容量
	
	//需要接收的分页条件
	private int page=1;//当前页
	//需要输出的分页数据
	private int totalPage;//总页数
	private List<Cost> cost;//每一页数据
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<Cost> getCost() {
		return cost;
	}

	public void setCost(List<Cost> cost) {
		this.cost = cost;
	}

	public List<Cost> getCosts() {
		return costs;
	}

	public void setCosts(List<Cost> costs) {
		this.costs = costs;
	}

	public String execute(){
		//查询资费数据
		ICostDAO dao = DAOFactory.getCostDAO();
		try {
//			 costs = dao.findAll();
			//查询当前页的数据
			 costs = dao.findByPage(page, pageSize);
			 //查询总页数
			 totalPage = dao.findTotalPage(pageSize);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		//正常执行结束，转向查询页面
		return "ok";
	}
	
	
}
