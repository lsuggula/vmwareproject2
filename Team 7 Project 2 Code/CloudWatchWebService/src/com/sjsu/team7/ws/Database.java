package com.sjsu.team7.ws;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {

	Connection con = null;
	static ResultSet rs;
	static ResultSetMetaData rsmd;
	Statement stmt = null;
	
	public Database() {
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vmdb","root","beplanned");
			stmt = con.createStatement();
			if(!con.isClosed())
				System.out.println("Successfully connected!");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}	catch (IllegalAccessException e) {
			e.printStackTrace();
		}	catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public String signUp(String email, String pwd, String firstName, String lastName) {
		String result = "";
		int rowcount;
		try {
			String query = "Insert into user_info (email,password,fname,lname) values ('" + email + "','" + pwd + "','" + firstName + "','" + lastName +"')";
			rowcount=stmt.executeUpdate(query);
			if(rowcount>0){
				result="success";
				System.out.println("Insert Successful");
			}
			else {
				result = "failure: the data could not be inserted into database";
			}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return result;
	}
	
	public String signIn(String email, String pwd) {
		String username = "";
		try {
			String query = "select fname from user_info where email='" + email + "' and password='" + pwd + "';";
						
			rs=stmt.executeQuery(query);
			
				if(rs!=null){
					rs.next();
					username=rs.getString("fname");
					System.out.println("Login Successful");
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		rs=null;
		return username;
	}
	
	
	public String addVM(String email, String vm_name, String os) {
		String result = "";
		int rowcount;
		String givenName = vm_name.substring(0, (vm_name.length() - (Integer.toString(getCurrCount()).length()+1)));
		try {
			String query = "Insert into vm_info (email,vm_name,os,created_on,vm_id) values ('" + email + "','" + givenName + "','" + os +"',curdate(),'" +vm_name + "')";
			rowcount=stmt.executeUpdate(query);
			if(rowcount>0){
				incrementCount();
				result="success";
				System.out.println("Insert Successful");
			}
			else {
				result = "false: the data could not be inserted into database";
			}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return result;
	}
	
	public HashMap<String,ArrayList<String>> getUserVMInfo(String email)
	{
		HashMap<String,ArrayList<String>> result= new HashMap<>();
		
		ArrayList<String> vm = new ArrayList<>();
		ArrayList<String> os = new ArrayList<>();
		ArrayList<String> con = new ArrayList<>();
		ArrayList<String> vid = new ArrayList<>();
		
		result.put("vm_name", vm);
		result.put("os", os);
		result.put("created_on", con);
		result.put("vm_id", vid);
		
		String query = "select vm_name, os, created_on, vm_id from vm_info where email='"+email+"';";
		System.out.println(query);
		try {
			rs=stmt.executeQuery(query);
			
			if(rs!=null){
				while(rs.next())
				{	System.out.println(rs.getString(1));
					vm.add(rs.getString(1));
					os.add(rs.getString(2));
					con.add(rs.getString(3));
					vid.add(rs.getString(4));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	return result;
	}
	
	public void incrementCount()
	{
		String queryGet = "select count from vm_id;";
		String queryPut;
		int count=0;
		try {
			rs=stmt.executeQuery(queryGet);
			rs.next();
			if(rs!=null){
				count=rs.getInt("count");
			}
			count++;
			queryPut= "update vm_id set count=" + count + " where count=" + (count-1) + " ;" ;
			stmt.executeUpdate(queryPut);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getCurrCount()
	{
		String queryGet = "select count from vm_id;";
		int count=0;
		try {
			rs=stmt.executeQuery(queryGet);
			rs.next();
			if(rs!=null){
				count=rs.getInt("count");
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	//new for part 2
	
	public String createAlarm(String email, String vm_name, String alarm_name, String type, int value, String condition) 
	{
		String result = "";
		int rowcount;
		
		try {
			String query = "Insert into vm_alarm (email,vm_name,alarm_name,alarm_type,alarm_value,alarm_condition,alarm_status,triggered) values ('" + email + "','" + vm_name + "','" + alarm_name + "','" + type + "'," + value + ",'" + condition + "','off','no');" ;
			System.out.println(query);
			rowcount=stmt.executeUpdate(query);
			if(rowcount>0){
				incrementCount();
				result="success";
				System.out.println("Insert Successful");
			}
			else {
				result = "false: the data could not be inserted into database";
			}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return result;
	}
	
	public ArrayList<HashMap<String,String>> getAlarms(String email, String vm_name)
	{
		String query = "select alarm_name, alarm_type, alarm_value, alarm_condition, alarm_status, triggered from vm_alarm where email='"+email+"' and vm_name='"+vm_name+"';";
		System.out.println(query);
		ArrayList<HashMap<String,String>> result = new ArrayList<>();
		
		try {
			rs=stmt.executeQuery(query);
			
			if(rs!=null){
				while(rs.next())
				{	
					HashMap<String,String> data = new HashMap<>();
					data.put("alarm_name", rs.getString(1)) ;
					data.put("alarm_type", rs.getString(2)) ;
					data.put("condition", (rs.getString(4).equals("gt")?"> ":"< ") + rs.getString(3) + " %") ;
					data.put("status", rs.getString(5)) ;
					data.put("triggered",rs.getString(6));
										
					result.add(data);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean deleteAlarm(String emailId, String vm_name, String alarm_name)
	{
		String query = "delete from vm_alarm where email='"+emailId+"' and vm_name='" + vm_name + "' and alarm_name='"+ alarm_name+"';";
				System.out.println(query);
		try {
			stmt.execute(query);
			
			return(true);
		} catch (SQLException e) {
			e.printStackTrace();
			return(false);
		}
	}
	
	public void fetchAlarms(ArrayList<HashMap<String,String>> alarms)
	{
		String query = "select email, vm_name, alarm_name, alarm_type, alarm_value, alarm_condition from vm_alarm where alarm_status='on' and triggered='no';";
		System.out.println(query);
		try {
			rs=stmt.executeQuery(query);
			
			if(rs!=null){
				while(rs.next())
				{	
					System.out.println(rs.getString(3));
					HashMap<String,String> data = new HashMap<>();
					data.put("email", rs.getString(1)) ;
					data.put("vm_name", rs.getString(2)) ;
					data.put("alarm_name", rs.getString(3)) ;
					data.put("alarm_type", rs.getString(4));
					data.put("alarm_value", rs.getString(5)) ;
					data.put("alarm_condition", rs.getString(6)) ;
										
					alarms.add(data);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void alarmTriggered(String email, String vm_name, String alarm_name)
	{
		String query = "update vm_alarm set triggered='yes' where email='"+email+"' and vm_name='"+vm_name+"' and alarm_name='"+alarm_name+"';";
		System.out.println(query);
		
		try {
			stmt.executeUpdate(query);
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void toggleAlarm(String email, String vm_name, String alarm_name, String status)
	{
		String query = "update vm_alarm set triggered='no', alarm_status='"+status+"' where email='"+email+"' and vm_name='" + vm_name+"' and alarm_name='" + alarm_name+"';";
		System.out.println(query);
		
		try {
			stmt.executeUpdate(query);
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
