package com.amdocs.dao;

import java.sql.SQLException;
import java.util.List;

import com.amdocs.Exceptions.MechanicNotFoundException;
import com.amdocs.model.Mechanic;

public interface MechanicDao {

boolean insert(Mechanic mechanic) throws SQLException;
	
	boolean update(Mechanic mechanic) throws SQLException, MechanicNotFoundException;
	
	boolean delete(int customerId) throws MechanicNotFoundException, SQLException;
	
	Mechanic findById(int customerId) throws MechanicNotFoundException, SQLException;
	
	List<Mechanic> findAll() throws SQLException;
}
