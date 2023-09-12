package com.amdocs.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amdocs.Exceptions.CustomerNotFoundException;
import com.amdocs.dao.CustomerDao;
import com.amdocs.model.Customer;
import com.amdocs.utilities.DatabaseUtility;

public class CustomerDaoImpl implements CustomerDao {
	
	private final static String SELECT_ALL = "SELECT * FROM CUSTOMER";
	private final static String SELECT_BY_ID = "SELECT * FROM CUSTOMER WHERE id=?";
	private final static String INSERT = "INSERT INTO CUSTOMER(firstName,lastName,email) values(?,?,?)";
	private final static String UPDATE = "UPDATE CUSTOMER SET firstName = ?, lastName = ?, email = ? WHERE id = ?";
	private final static String DELETE = "DELETE FROM CUSTOMER WHERE id = ?";
	
	private Connection connection = DatabaseUtility.getConnection();

	@Override
	public boolean insert(Customer customer) throws SQLException {
		boolean result=false;
		PreparedStatement stmt = connection.prepareStatement(INSERT);
		stmt.setString(1,customer.getFirstName());
		stmt.setString(2, customer.getLastName());
		stmt.setString(3,customer.getEmailAddress());
		if(stmt.executeUpdate()>0) {
			result = true;
		}
		stmt.close();
		return result;
	}

	@Override
	public boolean delete(int id) throws SQLException, CustomerNotFoundException {
		PreparedStatement stmt = connection.prepareStatement(DELETE);
		stmt.setInt(1, id);
		
		int rowsAffected = stmt.executeUpdate();
		System.out.println("Rows Affected: " + rowsAffected);
		
		if(rowsAffected >= 1) {
			
		}else {
			throw new CustomerNotFoundException("Customer Not Found With Id: " + id);
		}
		return true;
		
	}

	@Override
	public Customer findById(int id) throws SQLException, CustomerNotFoundException {
		Customer customer = null;
		PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		System.out.println("Rs  : "  + rs);
		if(rs.next()) {
			customer = new Customer(
					rs.getInt("id"), 
					rs.getString("firstName"), 
					rs.getString("lastName"), 
					rs.getString("email"));
			
		}else {
			throw new CustomerNotFoundException("Customer Not Found With Id: " + id);
		}
		rs.close();
		stmt.close();
		return customer;
	}

	@Override
	public List<Customer> findAll() throws SQLException {
		List<Customer> customers = new ArrayList<>();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SELECT_ALL);
		while (rs.next()) {
			Customer customer = new Customer(
					rs.getInt("id"), 
					rs.getString("firstName"), 
					rs.getString("lastName"), 
					rs.getString("email"));
			customers.add(customer);
		}
		rs.close();
		stmt.close();
		return customers;
	}

	@Override
	public boolean update(Customer customer) throws SQLException, CustomerNotFoundException {
		PreparedStatement stmt = connection.prepareStatement(UPDATE);
		stmt.setString(1,customer.getFirstName());
		stmt.setString(2, customer.getLastName());
		stmt.setString(3,customer.getEmailAddress());
		stmt.setInt(4, customer.getId());
		
		int rowsAffected = stmt.executeUpdate();
		System.out.println("Rows Affected  :" + rowsAffected);
		
		if(rowsAffected >= 1) {
			Customer updatedCustomer = findById(customer.getId());
			System.out.println("Updated Customer is: "+ updatedCustomer.toString());
		}else {
			throw new SQLException();
		}
		return true;
	}

}
