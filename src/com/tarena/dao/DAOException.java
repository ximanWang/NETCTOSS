package com.tarena.dao;

public class DAOException extends Exception{
	public DAOException(String message, Throwable ex){
		super(message,ex);
	}
}
