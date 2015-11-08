package com.sjsu.team7.vms;

import java.util.HashMap;
import java.util.Map;

public interface Constants {

	public final static String VCENTER_URL = "https://130.65.132.107/sdk";
	public final static String VCENTER_UID = "administrator";
	public final static String VCENTER_PASSWORD = "12!@qwQW";
	public final static String ME_NAME = "maName";
	public final static String VHOST_UID = "root";
	public final static String VHOST_PASSWORD = "12!@qwQW";
	public final static String ADMIN_URL = "https://130.65.132.19/sdk";
	public final static String ADMIN_UID = "student@vsphere.local";
	public final static String ADMIN_PASSWORD = "12!@qwQW";
	public final static String RESOURCE_POOL = "Team07_vHost";
	public final static int SNAPSHOT_INTERVAL = 300000;
	public final static int RECOVERY_INTERVAL = 20000;
	public final static int STATISTICS_INTERVAL=20000;
	
	// Ping constants
	public final static int PING_ATTEMPTS = 5;
	public final static int PING_INTERVAL = 10000;

	//VM names
	public final static String VM_WIN = "T07-VM-Win";
	public final static String VM_UBU = "T07-VM-UBU";
	
	
	public final static Map<String, String> HOST_MAP = new HashMap<String, String>() {

		private static final long serialVersionUID = 1L;

		{
			put("130.65.132.191", "T07-vHost01_132.191");
			put("130.65.132.192", "T07-vHost02_132.192");
			put("130.65.132.193", "T07-vHost03_132.193");
			put("130.65.132.194", "T07-vHost04_132.194");
			put("130.65.132.195", "T07-vHost05_132.195");
			put("130.65.132.196", "T07-vHost06_132.196");
			put("130.65.132.197", "T07-vHost07_132.197");
			put("130.65.132.198", "T07-vHost08_132.198");
			
		}
	};
}
