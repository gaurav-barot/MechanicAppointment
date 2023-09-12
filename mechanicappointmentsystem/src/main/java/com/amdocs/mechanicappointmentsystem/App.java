package com.amdocs.mechanicappointmentsystem;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.amdocs.Exceptions.MechanicNotFoundException;
import com.amdocs.dao.CustomerDao;
import com.amdocs.dao.MechanicDao;
import com.amdocs.daoImpl.CustomerDaoImpl;
import com.amdocs.daoImpl.MechanicDaoImpl;
import com.amdocs.model.Customer;
import com.amdocs.model.Mechanic;

public class App 
{
	private static CustomerDao dao = new CustomerDaoImpl();
	private static MechanicDao mDao = new MechanicDaoImpl();

	private static Scanner sc = new Scanner(System.in);
	
    public static void main( String[] args ){
        
while (true) {
			
			System.out.println("\n-------------Please enter Your choice-------------");
			System.out.println("1. Customer");
			System.out.println("2. Mechanic");
			System.out.println("0. Stop");
		
			
			int ch = Integer.parseInt(sc.nextLine());
			
			
			switch (ch) {
			case 1:
				
				boolean customer = true;
				
				while(customer) {
					System.out.println("\n----------Please enter Your choice------------");
					System.out.println("1. Register Customer");
					System.out.println("2. Modify Customer Details");
					System.out.println("3. Delete Customer Record");
					System.out.println("4. View Single Record");
					System.out.println("5. View All Records");
					System.out.println("0. Exit");
					
					int ch2 = Integer.parseInt(sc.nextLine());
					
					switch (ch2) {
					
					case 1:
						addCustomer();
						break;
						
					case 2:
						try {
							updateCustomer();
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
						
					case 3: 
						deleteCustomer();
						break;
						
					case 4:
						findCustomerById();
						break;
						
					case 5:
						findAllCustomers();
						break;
						
					default:
						System.out.println("Exiting");
						customer = false;
						break;		
						
					}
				}
				
				break;
				
			case 2:
				
				boolean mechanic = true;
				
				while(mechanic) {
					
					System.out.println("\n----------Please enter Your choice----------");
					System.out.println("1. Book an appointment");
					System.out.println("2. Modify appointment details");
					System.out.println("3. Delete an appointment");
					System.out.println("4. View Single Record");
					System.out.println("5. View All Records");
					System.out.println("6. View Customer's Appointment History");
					System.out.println("0. Exit");
					
					
					int ch3 = Integer.parseInt(sc.nextLine());
					
					switch (ch3) {
					
					case 1:
						bookAppointment();
						break;
						
					case 2:
						try {
							modifyAppointment();
						} catch (MechanicNotFoundException e) {
							e.printStackTrace();
						}
						break;
						
					case 3: 
						
//						deleteAppointment();
						break;
						
					case 4:
//						findAppointmentById();
						break;
						
					case 5:
//						findAllAppointments();
						break;
						
					case 6:
						
//						viewAppointmentHistory();
						break;
						
					default:

						System.out.println("Exiting");
						mechanic = false;
						break;		
						
					}
				}

				break;
			
			default:
				System.out.println("PROGRAM TERMINATED");
				System.exit(0);
			}

		}
    	
    }

	private static void modifyAppointment() throws MechanicNotFoundException {
		System.out.println("Enter Customer ID");
		int id = Integer.parseInt(sc.nextLine());
		
		Mechanic mechanic = null;
		
		try {
			mechanic = mDao.findById(id);
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		
		System.out.println("\nEnter Mechanic Name:");
		String mechanicName = sc.nextLine();
		mechanic.setmechanicName(mechanicName);
		
		System.out.println("\nEnter Date:");
		String date = sc.nextLine();
		mechanic.setdate(date);
			
		try {
			if(mDao.update(mechanic)) {
				System.out.println("Customer is successfully registered!!");
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		
	}

	private static void bookAppointment() {
		System.out.println("\nEnter Customer ID:");
		int customerId = Integer.parseInt(sc.nextLine());
		
		System.out.println("\nEnter Mechanic Name:");
		String mechanicName = sc.nextLine();
		
		System.out.println("\nEnter Date");
		String date = sc.nextLine();

		
		Mechanic mechanic = new Mechanic(customerId, mechanicName, date);
		
		try {
			if(mDao.insert(mechanic)) {
				System.out.println("Mechanic appointment is successfully registered!!");
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		
	}

	private static void deleteCustomer() {
		
		System.out.println("\nEnter Customer Id");
		int id = sc.nextInt();
		sc.nextLine();
		
		try {
			dao.delete(id);
			System.out.println("Customer record is successfully deleted!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void findAllCustomers() {
		try {
			List<Customer> customers = dao.findAll();
			
			if(customers.size()==0) {
				System.out.println("No Record Found");
			}else {
				System.out.println("Complete customer record is as follows:");
				for(Customer c : customers) {
					System.out.println(c.toString());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void findCustomerById() {
		
		System.out.println("\nEnter Customer Id");
		int id = Integer.parseInt(sc.nextLine());
		
		try {
			Customer customer = dao.findById(id);
			System.out.println("Requested customer details are:");
			System.out.println(customer.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void updateCustomer() throws com.amdocs.Exceptions.CustomerNotFoundException {
		
		System.out.println("Enter Customer ID");
		int id = Integer.parseInt(sc.nextLine());
		
		Customer customer = null;
		
		try {
			customer = dao.findById(id);
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		
		System.out.println("\nEnter First Name:");
		String firstName = sc.nextLine();
		customer.setFirstName(firstName);
		
		System.out.println("\nEnter Last Name:");
		String lastName = sc.nextLine();
		customer.setLastName(lastName);
		
		System.out.println("\nEnter Email Address:");
		String email = sc.nextLine();
		customer.setEmailAddress(email);
		
			
		try {
			if(dao.update(customer)) {
				System.out.println("Customer is successfully registered!!");
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	private static void addCustomer() {
		System.out.println("\nEnter First Name:");
		String firstName = sc.nextLine();
		
		System.out.println("\nEnter Last Name:");
		String lastName = sc.nextLine();

		System.out.println("\nEnter Email Address:");
		String email = sc.nextLine();
		
		Customer customer = new Customer(firstName, lastName, email);
		
		try {
			if(dao.insert(customer)) {
				System.out.println("Customer is successfully registered!!");
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
}
