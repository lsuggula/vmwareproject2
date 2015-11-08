package com.sjsu.team7.vms;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import com.vmware.vim25.PerfCompositeMetric;
import com.vmware.vim25.PerfEntityMetric;
import com.vmware.vim25.PerfEntityMetricBase;
import com.vmware.vim25.PerfEntityMetricCSV;
import com.vmware.vim25.PerfMetricId;
import com.vmware.vim25.PerfMetricIntSeries;
import com.vmware.vim25.PerfMetricSeries;
import com.vmware.vim25.PerfMetricSeriesCSV;
import com.vmware.vim25.PerfQuerySpec;
import com.vmware.vim25.PerfSampleInfo;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.PerformanceManager;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

public class PerfMetric {
	
	public static String[][] perfList;
	
	public static String[][] getPerfMetric(ServiceInstance si, VirtualMachine vm, String vmname) throws Exception
	  {
		
		PerformanceManager perfMgr = si.getPerformanceManager();

	    int perfInterval = 1800; // 30 minutes for PastWeek
	    
	    // retrieve all the available perf metrics for vm
	    PerfMetricId[] pmis = perfMgr.queryAvailablePerfMetric(vm, null, null, perfInterval);
	    
	    for(PerfMetricId id: pmis)
	    {
	    	id.setInstance("");
	    }
	    Calendar curTime = si.currentTime();
	    
	    PerfQuerySpec qSpec = new PerfQuerySpec();
	    qSpec.setEntity(vm.getRuntime().getHost());
	    //metricIDs must be provided, or InvalidArgumentFault 
	    qSpec.setMetricId(pmis);
	    qSpec.setFormat("normal"); //optional since it's default
	    qSpec.setIntervalId(perfInterval); 

	    Calendar startTime = (Calendar) curTime.clone();
	    startTime.roll(Calendar.DATE, -1);
	    System.out.println("start:" + startTime.getTime());
	    qSpec.setStartTime(startTime);
	    
	    Calendar endTime = (Calendar) curTime.clone();
	    endTime.roll(Calendar.DATE, -0);
	    System.out.println("end:" + endTime.getTime());
	    qSpec.setEndTime(endTime);
	    
	    PerfCompositeMetric pv = perfMgr.queryPerfComposite(qSpec);
	    if(pv != null)
	    {
	    	
	      printPerfMetric(pv.getEntity());
	     
	      PerfEntityMetricBase[] pembs = pv.getChildEntity();
	      for(int i=0; pembs!=null && i< pembs.length; i++)
	      {
	        //printPerfMetric(pembs[i]);
	      }
	    }
	    //si.getServerConnection().logout();
	    return perfList;
	  }

	  static void printPerfMetric(PerfEntityMetricBase val)
	  {
		 
	    String entityDesc = val.getEntity().getType() 
	        + ":" + val.getEntity().get_value();
	    System.out.println("Entity:" + entityDesc);
	    
	    if(val instanceof PerfEntityMetric)
	    { 	    
	      printPerfMetric((PerfEntityMetric)val);
	     
	    }
	    else if(val instanceof PerfEntityMetricCSV)
	    {  
	      printPerfMetricCSV((PerfEntityMetricCSV)val);
	     
	    }
	    else
	    {
	      System.out.println("UnExpected sub-type of " +
	      		"PerfEntityMetricBase.");
	    }
	  }
	  
	  static void printPerfMetric(PerfEntityMetric pem)
	  {
	    PerfMetricSeries[] vals = pem.getValue();
	    PerfSampleInfo[]  infos = pem.getSampleInfo();
	   	   	    
	    if(pem.getSampleInfo()==null)
	    {
	    	System.out.println("asasda");
	    	
	    }else
	    	System.out.println("ssdhfdjhf");
	    perfList = new String[infos.length][3];
	    
	    System.out.println("Sampling Times and Intervales:");
	    for(int i=0; infos!=null && i<infos.length; i++)
	    {
	        System.out.println("sample time: " 
	          + infos[i].getTimestamp().getTime());
	        System.out.println("sample interval (sec):" 
	          + infos[i].getInterval());
	        
	        perfList[i][0]=infos[i].getTimestamp().getTime().toString();
	    }
	    
	    System.out.println("\nSample values:");
	    for(int j=0; vals!=null && j<vals.length; ++j)
	    {
	      System.out.println("Perf counter ID:" 
	          + vals[j].getId().getCounterId());
	      System.out.println("Device instance ID:" 
	          + vals[j].getId().getInstance());
	      
	      if(vals[j] instanceof PerfMetricIntSeries)
	      {
	        PerfMetricIntSeries val = (PerfMetricIntSeries) vals[j];
	        long[] longs = val.getValue();
	        for(int k=0; k<longs.length; k++) 
	        {
	          System.out.print(longs[k] + " ");
	          if(j==0)
	          {
	        	perfList[k][1]= String.valueOf(longs[k]) ;  
	          } else if(j==4){
	        	  perfList[k][2]= String.valueOf(longs[k]) ;
	          }
	        }
	        System.out.println("Total:"+longs.length);
	      }
	      else if(vals[j] instanceof PerfMetricSeriesCSV)
	      { // it is not likely coming here...
	        PerfMetricSeriesCSV val = (PerfMetricSeriesCSV) vals[j];
	        System.out.println("CSV value:" + val.getValue());
	      }
	    }
	  }
	    
	  static void printPerfMetricCSV(PerfEntityMetricCSV pems)
	  {
	    System.out.println("SampleInfoCSV:" 
	        + pems.getSampleInfoCSV());
	    PerfMetricSeriesCSV[] csvs = pems.getValue();
	    for(int i=0; i<csvs.length; i++)
	    {
	      System.out.println("PerfCounterId:" 
	          + csvs[i].getId().getCounterId());
	      System.out.println("CSV sample values:" 
	          + csvs[i].getValue());
	    }
	  }
}
