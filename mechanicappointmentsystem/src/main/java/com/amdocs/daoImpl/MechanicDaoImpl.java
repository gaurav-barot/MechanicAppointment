package com.amdocs.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amdocs.Exceptions.CustomerNotFoundException;
import com.amdocs.Exceptions.MechanicNotFoundException;
import com.amdocs.dao.MechanicDao;
import com.amdocs.model.Customer;
import com.amdocs.model.Mechanic;
import com.amdocs.utilities.DatabaseUtility;

public class MechanicDaoImpl implements MechanicDao{
	
	private final static String SELECT_ALL = "SELECT * FROM MECHANIC";
	private final static String SELECT_BY_ID = "SELECT * FROM MECHANIC WHERE customerId=?";
	private final static String INSERT = "INSERT INTO MECHANIC(mechanicName,date) values(?,?)";
	private final static String UPDATE = "UPDATE MECHANIC SET mechanicName = ?, date = ? WHERE customerId = ?";
	private final static String DELETE = "DELETE FROM MECHANIC WHERE mechanicId = ?";
	
	private Connection connection = DatabaseUtility.getConnection();

	@Override
	public boolean insert(Mechanic mechanic) throws SQLException {
		boolean result=false;
		PreparedStatement stmt = connection.prepareStatement(INSERT);
		stmt.setString(1,mechanic.getmechanicName());
		stmt.setString(2, mechanic.getdate());
		if(stmt.executeUpdate()>0) {
			result = true;
		}
		stmt.close();
		return result;
	}

	@Override
	public boolean update(Mechanic mechanic) throws SQLException, MechanicNotFoundException {
		PreparedStatement stmt = connection.prepareStatement(UPDATE);
		stmt.setString(1,mechanic.getmechanicName());
		stmt.setString(2, mechanic.getdate());
		stmt.setInt(3, mechanic.getcustomerId());
		
		int rowsAffected = stmt.executeUpdate();
		System.out.println("Rows Affected  :" + rowsAffected);
		
		if(rowsAffected >= 1) {
			Mechanic updatedCustomer = findById(mechanic.getcustomerId());
			System.out.println("Updated Customer is: "+ updatedCustomer.toString());
		}else {
			throw new SQLException();
		}
		return true;
	}

	@Override
	public boolean delete(int customerId) throws MechanicNotFoundException, SQLException {
		PreparedStatement stmt = connection.prepareStatement(DELETE);
		stmt.setInt(1, customerId);
		
		int rowsAffected = stmt.executeUpdate();
		System.out.println("Rows Affected: " + rowsAffected);
		
		if(rowsAffected >= 1) {
			
		}else {
			throw new MechanicNotFoundException("Mechanic Not Found With Id: " + customerId);
		}
		return true;
	}

	@Override
	public Mechanic findById(int customerId) throws MechanicNotFoundException, SQLException {
		Mechanic mechanic = null;
		PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID);
		stmt.setInt(1, customerId);
		ResultSet rs = stmt.executeQuery();
		System.out.println("Rs  : "  + rs);
		if(rs.next()) {
			mechanic = new Mechanic(
					rs.getInt("customerId"), 
					rs.getString("mechanicName"), 
					rs.getString("date"));
			
		}else {
			throw new MechanicNotFoundException("Mechanic Not Found With Id: " + customerId);
		}
		rs.close();
		stmt.close();
		return mechanic;
	}

	@Override
	public List<Mechanic> findAll() throws SQLException {
		List<Mechanic> mechanics = new ArrayList<>();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SELECT_ALL);
		while (rs.next()) {
			Mechanic mechanic = new Mechanic(
					rs.getInt("customerId"), 
					rs.getString("mechanicName"), 
					rs.getString("date"));
			mechanics.add(mechanic);
		}
		rs.close();
		stmt.close();
		return mechanics;
	}

}
