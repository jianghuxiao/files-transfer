package com.transfer.reportManager;

import java.util.HashMap;
import java.util.Map;

import com.transfer.util.IClient;

public class ReportManager {
	//Client queue
	private static Map<IClient, ReportQueue> sReportQueue = new HashMap<IClient, ReportQueue>();
	
	/**
	 * Create report queue
	 */
	public synchronized static void createReportQueue(IClient client){
		if(!sReportQueue.containsKey(client)){
			sReportQueue.put(client, new ReportQueue());
		}
	}
	
	/**
	 * Remove report queue
	 * @param client
	 */
	public synchronized static void removeReportQueue(IClient client){
		if(sReportQueue.containsKey(client)){
			sReportQueue.remove(client);
		}
	}
	
	/**
	 * Get ReportQueue Instance
	 * @return
	 */
	public synchronized static IReport getInstance(IClient client){
		if(sReportQueue.get(client) == null)
			createReportQueue(client);
		
		return sReportQueue.get(client);
	}
}
