package com.sjsu.team7.vms;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.vmware.vim25.PerfCompositeMetric;
import com.vmware.vim25.PerfCounterInfo;
import com.vmware.vim25.PerfEntityMetric;
import com.vmware.vim25.PerfEntityMetricBase;
import com.vmware.vim25.PerfEntityMetricCSV;
import com.vmware.vim25.PerfMetricId;
import com.vmware.vim25.PerfMetricIntSeries;
import com.vmware.vim25.PerfMetricSeries;
import com.vmware.vim25.PerfMetricSeriesCSV;
import com.vmware.vim25.PerfQuerySpec;
import com.vmware.vim25.PerfSampleInfo;
import com.vmware.vim25.VirtualMachineCapability;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.PerformanceManager;
import com.vmware.vim25.mo.ServerConnection;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.sjsu.team7.ws.Database;

public class VM
{
	ServiceInstance si;
	Folder rootFolder;
	public static VM virtualMachine;
	
	private VM() throws Exception
	{
		si = new ServiceInstance(new URL(Constants.VCENTER_URL),Constants.VCENTER_UID,Constants.VCENTER_PASSWORD,true);
		rootFolder  = si.getRootFolder();
		
	}
	
	public static VM getInstance() throws Exception
	{
		if(virtualMachine==null)
			virtualMachine=new VM();
		return virtualMachine;
	}
	
	public boolean createVM(String vm_name, String os) throws Exception
	{
		VirtualMachine vm;
		if(os.equals("windows"))
			vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", Constants.VM_WIN);
		else
			vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", Constants.VM_UBU);
		
		VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();
		cloneSpec.setLocation(new VirtualMachineRelocateSpec());
		cloneSpec.setPowerOn(false);
		cloneSpec.setTemplate(false);
		if(vm==null)
			System.out.println("vm");
		if(cloneSpec==null)
			System.out.println("cloneSpec");
		Task task = vm.cloneVM_Task((Folder) vm.getParent(), vm_name, cloneSpec);
		    System.out.println("Launching the VM clone task. " +"Please wait ...");

		    String status = task.waitForTask();
		    if(status==Task.SUCCESS)
		    {
		      System.out.println("VM got cloned successfully.");
		      powerOn(vm_name);
		      return true;
		    }
		    else
		    {
		      System.out.println("Failure -: VM cannot be cloned");
		      return false;
		    }
	}
	
	public HashMap<String,String> getVMInfo(String vm_id) throws Exception
	{
		HashMap<String,String> vmi = new HashMap<>();
		
		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vm_id);
		VirtualMachineConfigInfo vminfo = vm.getConfig();
		VirtualMachineCapability vmc = vm.getCapability();
		VirtualMachineRuntimeInfo vmri = vm.getRuntime();
		
		vmi.put("GuestOs" , vm.getGuest().getGuestFullName());
		vmi.put("VMVersion" , vm.getConfig().getVersion());
		vmi.put("CPU",Integer.toString(vminfo.getHardware().getNumCPU()));
		vmi.put("Memory",Integer.toString(vminfo.getHardware().getMemoryMB())+" MB");
		vmi.put("MemOverhead",vmri.getMemoryOverhead().toString());
		vmi.put("IPAddress",vm.getGuest().getIpAddress());
		vmi.put("DNSName",vm.getGuest().getHostName());
		vmi.put("State" , vmri.getPowerState().toString());
		vmi.put("Host",vmri.getHost().get_value());
		
		//vmi.put("Parent" , vm.getParent().toString());
		//vmi.put("Resouce pool" , vm.getResourcePool().toString());
		//vmi.put("Runtime" , vm.getRuntime().getDynamicType());
		//vmi.put("Multiple snapshot supported", Boolean.toString(vmc.isMultipleSnapshotsSupported()));
		
		vmi.put("MaxMemoryUsage", vmri.getMaxMemoryUsage().toString());
		vmi.put("MaxCPUUsage" , vmri.getMaxCpuUsage().toString());
		vmi.put("ConnectionState", vmri.getConnectionState().toString());
		vmi.put("BootTime",vmri.getBootTime().getTime().toString());
		
		
	
		//vmi.put("Location Id",vminfo.getLocationId());
		//vmi.put("UUID",vminfo.getUuid());
		//vmi.put("VM Name",vm.getName());
		
		
		
		/*PerformanceManager perMan = si.getPerformanceManager();
			PerfCounterInfo[] pi = p.getPerfCounter();
		
		for(PerfCounterInfo pci: pi)
		{
			System.out.println(pci.getNameInfo().getSummary());
		}*/
		return vmi;
	}
	
	public boolean isPoweredOn(String vm_name) throws Exception
	{
		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vm_name);
		VirtualMachineRuntimeInfo vmri = vm.getRuntime();
		if(vmri.getPowerState().toString().equalsIgnoreCase("poweredOn"))
			return true;
		else
			return false;
	}
	
	public synchronized boolean powerOn(String vm_name) throws Exception {
		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vm_name);
		Task task = vm.powerOnVM_Task(null);
		System.out.println(vm.getName() + " is powering on...");

		if (task.waitForTask() == Task.SUCCESS)
		{
			System.out.println(vm.getName() + " is running now.");
			return true;
		} else {
			return false;
		}
	}
	
	public synchronized boolean powerOff(String vm_name) throws Exception {
		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vm_name);
		Task task = vm.powerOffVM_Task();
		System.out.println(vm.getName() + " is powering off...");

		if (task.waitForTask() == Task.SUCCESS)
		{
			System.out.println(vm.getName() + " is not running now.");
			return true;
		} else {
			return false;
		}
	}
	
	public String[][] getPerfMetrics(String vm_name) throws Exception
	{
		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vm_name);
		return(PerfMetric.getPerfMetric(si, vm, vm_name));
	}
	
	public void checkAlarms() throws Exception
	{
			Thread alarmManager = new Thread() {
			
				ArrayList<HashMap<String,String>> alarms = new ArrayList<>();
				
				Database db = new Database();
				VirtualMachine vm;
			
				public void run() {

				try {
					while (true) {
						
						System.out.println("Checking VM alarms");
						
						db.fetchAlarms(alarms);
		
						if(alarms.size()>0)
						{
							for(HashMap<String,String> alarm : alarms)
							{
								vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", alarm.get("vm_name"));
								System.out.println("cpu "+vm.getSummary().quickStats.overallCpuUsage+"mem "+ vm.getSummary().quickStats.guestMemoryUsage);
								System.out.println("type: " + alarm.get("alarm_type") + " condition " + alarm.get("alarm_condition") );
								switch(alarm.get("alarm_type"))
								{
								
									case "cpu": 
									if(alarm.get("alarm_condition").equals("gt"))
									{
										
										if(((int)(vm.getSummary().quickStats.overallCpuUsage*100)/2400) > Integer.parseInt(alarm.get("alarm_value")))
										{
											System.out.println("inp " + (int)((vm.getSummary().quickStats.overallCpuUsage*100)/2400) + " out " + Integer.parseInt(alarm.get("alarm_value")));
		
											//email
											db.alarmTriggered(alarm.get("email"), alarm.get("vm_name"), alarm.get("alarm_name"));
										}
										
									} else {
										if((int)((vm.getSummary().quickStats.overallCpuUsage*100)/2400) < Integer.parseInt(alarm.get("alarm_value")))
										{
											//email
											db.alarmTriggered(alarm.get("email"), alarm.get("vm_name"), alarm.get("alarm_name"));
										}
									}
												
									break;
								
									case "mem": 
										if(alarm.get("alarm_condition").equals("gt"))
										{
											if((int)((vm.getSummary().quickStats.guestMemoryUsage*100)/1024) <Integer.parseInt(alarm.get("alarm_value")))
											{
												//email
												db.alarmTriggered(alarm.get("email"), alarm.get("vm_name"), alarm.get("alarm_name"));
											}
											
										} else {
											if((int)((vm.getSummary().quickStats.guestMemoryUsage*100)/1024) >Integer.parseInt(alarm.get("alarm_value")))
											{
												//email
												db.alarmTriggered(alarm.get("email"), alarm.get("vm_name"), alarm.get("alarm_name"));
											}
										}
													
										break;
								case "disk": break;
								}
							}
						}
						alarms.clear();
						Thread.sleep(30000);
		
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		}};
		alarmManager.start();
	}
	
}
