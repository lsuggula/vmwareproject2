package com.sjsu.team7.ws;

public class AddVM implements Runnable{

	public static String vm_name;
	public static String os;
	public static String email;
	
	public AddVM(String vm_name, String os, String email)
	{
		this.vm_name=vm_name;
		this.os=os;
		this.email=email;
	}
	
	@Override
	public void run() {
		
		VMService vms = new VMService();
		vms.addVM(vm_name, os, email);
		// TODO Auto-generated method stub
		
	}

	
}
