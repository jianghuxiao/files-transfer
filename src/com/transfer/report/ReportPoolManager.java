package com.transfer.report;

import java.util.HashMap;
import java.util.Map;

import com.util.custom.IClient;

/**
 * Report Pool Manager
 * @author Roy
 *
 */
public class ReportPoolManager {
	//Client queue
	private static Map<String, ReportQueue> sReportPoolManager = new HashMap<String, ReportQueue>();
	
	/**
	 * Get ReportQueue Instance
	 * @return
	 */
	public synchronized static IReportQueue get(IClient client){
		if(sReportPoolManager.get(client.getIP()) == null)
			add(client);
		
		return sReportPoolManager.get(client.getIP());
	}
	
	/**
	 * Create report queue
	 */
	public synchronized static void add(IClient client){
		if(!sReportPoolManager.containsKey(client.getIP())){
			sReportPoolManager.put(client.getIP(), new ReportQueue());
		}
	}
	
	/**
	 * Remove report queue
	 * @param client
	 */
	public synchronized static void remove(IClient client){
		if(sReportPoolManager.containsKey(client.getIP())){
			sReportPoolManager.remove(client.getIP());
		}
	}
}
