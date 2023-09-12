package com.amdocs.dao;

import java.sql.SQLException;
import java.util.List;

import com.amdocs.Exceptions.CustomerNotFoundException;
import com.amdocs.model.Customer;
import com.amdocs.model.Mechanic;

public interface CustomerDao {
	
	boolean insert(Customer customer) throws SQLException;
	
	boolean update(Customer customer) throws SQLException, CustomerNotFoundException;
	
	boolean delete(int id) throws CustomerNotFoundException, SQLException;
	
	Customer findById(int id) throws CustomerNotFoundException, SQLException;
	
	List<Customer> findAll() throws SQLException;

}
