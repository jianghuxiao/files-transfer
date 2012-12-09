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
	private static Map<IClient, ReportTool> sReportPoolManager = new HashMap<IClient, ReportTool>();
	
	/**
	 * Create report queue
	 */
	public synchronized static void create(IClient client){
		if(!sReportPoolManager.containsKey(client)){
			sReportPoolManager.put(client, new ReportTool());
		}
	}
	
	/**
	 * Remove report queue
	 * @param client
	 */
	public synchronized static void remove(IClient client){
		if(sReportPoolManager.containsKey(client)){
			sReportPoolManager.remove(client);
		}
	}
	
	/**
	 * Get ReportQueue Instance
	 * @return
	 */
	public synchronized static IReportTool get(IClient client){
		if(sReportPoolManager.get(client) == null)
			create(client);
		
		return sReportPoolManager.get(client);
	}
}
