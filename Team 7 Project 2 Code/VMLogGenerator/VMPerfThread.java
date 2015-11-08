package project2;

import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileOutputStream;
import java.io.FileWriter;
//import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//import java.util.HashMap;
//import java.util.concurrent.Callable;
import com.vmware.vim25.InvalidProperty;
//import com.vmware.vim25.PerfCounterInfo;
import com.vmware.vim25.PerfEntityMetric;
import com.vmware.vim25.PerfEntityMetricBase;
import com.vmware.vim25.PerfMetricId;
import com.vmware.vim25.PerfMetricIntSeries;
import com.vmware.vim25.PerfMetricSeries;
import com.vmware.vim25.PerfProviderSummary;
import com.vmware.vim25.PerfQuerySpec;
//import com.vmware.vim25.PerfSampleInfo;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.PerformanceManager;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

public class VMPerfThread implements Runnable{
	private String vm;
	private static final int SELECTED_COUNTER_ID = 6; // Active (mem) in KB
	static Integer[] a = { 5, 23, 124, 142, 133 };
	static String[] aName = { "cpu", "mem", "disk", "net", "I/O" };
	private HashMap<String, String> infoList = new HashMap<String, String>();
	int counter = 0;

	public VMPerfThread(String vm) {
		this.vm=vm;
	}

	public void run() {
		try {

			while (true) {

				//String url = ProjectUtil.getVmwareHostURL() + "/vimService";
				try {
					ServiceInstance si = new ServiceInstance(new URL(ProjectUtil.getVmwareHostURL()),
							ProjectUtil.getVmwareLogin(), ProjectUtil.getVmwarePassword(), true);
					VirtualMachine host = (VirtualMachine) new InventoryNavigator(
							si.getRootFolder()).searchManagedEntity(
							"VirtualMachine", vm);
					PerformanceManager perfMgr = si.getPerformanceManager();
					PerfProviderSummary summary = perfMgr
							.queryPerfProviderSummary(host);
					int perfInterval = summary.getRefreshRate();
					PerfMetricId[] queryAvailablePerfMetric = perfMgr
							.queryAvailablePerfMetric(host, null, null,
									perfInterval);
					//PerfCounterInfo[] pci = perfMgr.getPerfCounter();
					ArrayList<PerfMetricId> list = new ArrayList<PerfMetricId>();
					System.out.println("length"+queryAvailablePerfMetric.length);
					for (int i2 = 0; i2 < queryAvailablePerfMetric.length; i2++) {
						PerfMetricId perfMetricId = queryAvailablePerfMetric[i2];
						if (SELECTED_COUNTER_ID == perfMetricId.getCounterId()) {
							list.add(perfMetricId);
						}
					}
					PerfMetricId[] pmis = list.toArray(new PerfMetricId[list
							.size()]);
					PerfQuerySpec qSpec = new PerfQuerySpec();
					qSpec.setEntity(host.getMOR());
					qSpec.setMetricId(pmis);

					qSpec.intervalId = perfInterval;
					PerfEntityMetricBase[] pembs = perfMgr
							.queryPerf(new PerfQuerySpec[] { qSpec });

					for (int i = 0; pembs != null && i < pembs.length; i++) {

						PerfEntityMetricBase val = pembs[i];
						PerfEntityMetric pem = (PerfEntityMetric) val;
						PerfMetricSeries[] vals = pem.getValue();
						//PerfSampleInfo[] infos = pem.getSampleInfo();

						for (int j = 0; vals != null && j < vals.length; ++j) {
							PerfMetricIntSeries val1 = (PerfMetricIntSeries) vals[j];
							long[] longs = val1.getValue();

							for (int k : a) {
								infoList.put(aName[counter],
										String.valueOf(longs[k]));
								counter++;
							}
							counter = 0;

						}
					}
					si.getServerConnection().logout();
				} catch (InvalidProperty e) {
					e.printStackTrace();
				} catch (RuntimeFault e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				infoList.put("vmName",vm);
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				infoList.put("datetime",sd.format(new Date()));
				counter = 0;
				try {
					System.out.println("Reached File Writing Stage...");
					File file = new File("/home/test/logStats.log");
					 
					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
		 
					FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
					BufferedWriter bw = new BufferedWriter(fw);

					for(String key : infoList.keySet()){
							System.out.println(key +":"+ infoList.get(key)+",");
							bw.append(key +":"+ infoList.get(key)+",");
						//}
					}
					bw.append('\n');
					bw.flush();
					bw.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				Thread.sleep(20000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}	
}
