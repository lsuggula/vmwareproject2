package com.sjsu.team7.ws;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Services {
	public Database db;
	public VMService vms ;
	public static Services service;
	
	private Services() throws Exception
	{
		vms = new VMService();
		db= new Database();
		vms.checkAlarms();
	}
	
	public static Services getInstance() throws Exception
	{
		if(service==null)
		{
			service=new Services();
		}
		return service;
	}
	
	//database functions
	public String signUp(String email, String pwd, String firstName, String lastName)
	{
		System.out.println("Inside Signup");
		String result;
		
		result=db.signUp(email,pwd,firstName,lastName);
		return result;
	}
	
	public String signIn(String email, String pwd)
	{
		System.out.println("Inside SignIn");
		String result;
		
		result=db.signIn(email,pwd);
		return result;
	}
	
	public HashMap<String,ArrayList<String>> getUserVMInfo(String email)
	{
		System.out.println("Inside getUserVMInfo");
			
		return(db.getUserVMInfo(email));
		
	}
	
	public void addVM(String vm_name, String os, String email)
	{
		System.out.println("Inside addVM");
		System.out.println(vm_name+" " + os +" " + email);
		vm_name+="_"+(db.getCurrCount()+1);
		Runnable r= new AddVM(vm_name, os, email);
		new Thread(r).start();
	}
	
	public void addVMtoDB(String vm_name, String os, String email)
	{
		System.out.println("Inside addVM to DB");
		db.addVM(email, vm_name, os);
	}
	
	public HashMap<String,String> getVMInfo(String vm_id)
	{
		System.out.println("Inside getVMInfo");
		return(vms.getVMInfo(vm_id));
	}
	
	public boolean isPoweredOn(String vm_id) throws Exception
	{
		System.out.println("Inside isPoweredOn");
		return(vms.isPoweredOn(vm_id));
	}
	
	public boolean powerOn(String vm_id) throws Exception
	{
		System.out.println("Inside powerOn");
		return(vms.powerOn(vm_id));
	}
	
	public boolean powerOff(String vm_id) throws Exception
	{
		System.out.println("Inside powerOff");
		return(vms.powerOff(vm_id));
	}
	
	public String[][] getPerfMetrics(String vm_id) throws Exception
	{
		System.out.println("Inside powerOff");
		return(vms.getPerfMetric(vm_id));
	}
	
	public String createAlarm(String email, String vm_name, String alarm_name, String type, int value, String condition)
	{
		System.out.println("Inside createAlarm");
		String result;
		
		result=db.createAlarm(email, vm_name, alarm_name, type, value, condition);
		return result;
	}
	
	public ArrayList<HashMap<String,String>> getAlarms(String email, String vm_name)
	{
		System.out.println("Inside getAlarms");
				
		ArrayList<HashMap<String,String>> result=db.getAlarms(email, vm_name);
		return result;
	}
	
	public boolean deleteAlarm(String emailId, String vm_name, String alarm_name)
	{
		System.out.println("Inside deleteAlarm");
		
		return(db.deleteAlarm(emailId, vm_name, alarm_name));
	}
	
	public void fetchAlarms(ArrayList<HashMap<String,String>> alarms)
	{
		System.out.println("Inside deleteAlarm");
		db.fetchAlarms(alarms);
	}
	
	public void alarmTriggered(String email, String vm_name, String alarm_name)
	{
		System.out.println("Inside alarmTriggered");
		db.alarmTriggered(email, vm_name, alarm_name);
	}
	
	public void toggleAlarm(String email, String vm_name, String alarm_name, String status)
	{
		System.out.println("Inside toggleAlarm");
		db.toggleAlarm(email, vm_name, alarm_name, status);
	}
}
