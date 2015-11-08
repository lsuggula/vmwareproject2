package com.sjsu.team7.ws;

import java.util.ArrayList;
import java.util.Map;

import com.sjsu.team7.vms.VM;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		//Services.addVM("T07-Ashish-abcd", "Windows", "ashish");
		//System.out.println("returned");
		//Map<String,String> m = Services.getVMInfo("T07-VM-Win");
		
		/*for(String key: m.keySet())
		{
			System.out.println(key + " : " + m.get(key));
		}*/
		
		//System.out.println(Services.signIn("ashish", "asdad"));
		//System.out.println(Services.isPoweredOn("T07-VM-Win"));
		
		//Database db = new Database();
		
		//System.out.println(db.addVM("ashishas", "t07-1lin", "linux"));
		
		//System.out.println(Services.getUserVMInfo("ashish"));
		//System.out.println(Services.getVMInfo("T07-VM-Win"));
		
		VM v = VM.getInstance();
		
		String[][] a =  v.getPerfMetrics("T07-VM-UBU");
		
		for(int i=0; i<a.length; i++)
		{
			System.out.println(a[i][0] +" " + a[i][1]+" " +a[i][2]);
		}
	}

}
