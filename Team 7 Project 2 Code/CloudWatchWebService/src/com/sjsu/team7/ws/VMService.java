package com.sjsu.team7.ws;


import java.util.HashMap;

import com.sjsu.team7.vms.VM;

public class VMService {
	
	VM vmachine;
	
	public VMService()
	{
		try {
			vmachine = VM.getInstance() ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean addVM(String vm_name, String os, String email)
	{
		boolean flag = false;
		try {
			if(vmachine.createVM(vm_name, os))
			//if(true)
			{
				flag=true;
				Services sr = Services.getInstance();
				sr.addVMtoDB(vm_name, os, email);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	public HashMap<String,String> getVMInfo(String vm_id)
	{
		try {
			return(vmachine.getVMInfo(vm_id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public boolean isPoweredOn(String vm_name) throws Exception
	{
		return(vmachine.isPoweredOn(vm_name));
	}
	
	public boolean powerOn(String vm_name) throws Exception
	{
		return(vmachine.powerOn(vm_name));
	}
	
	public boolean powerOff(String vm_name) throws Exception
	{
		return(vmachine.powerOff(vm_name));
		
	}
	
	public String[][] getPerfMetric(String vm_name) throws Exception
	{
		return(vmachine.getPerfMetrics(vm_name));
	}
	
	public void checkAlarms() throws Exception
	{
		vmachine.checkAlarms();
	}
}
