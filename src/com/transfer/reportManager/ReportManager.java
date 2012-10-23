package com.transfer.reportManager;

import java.util.HashMap;
import java.util.Map;

import com.transfer.util.IClient;

public class ReportManager {
	//Client queue
	private static Map<IClient, ReportQueue> _ReportQueue = new HashMap<IClient, ReportQueue>();
	
	/**
	 * Create report queue
	 */
	public synchronized static void CreateReportQueue(IClient client){
		if(!_ReportQueue.containsKey(client)){
			_ReportQueue.put(client, new ReportQueue());
		}
	}
	
	/**
	 * Remove report queue
	 * @param client
	 */
	public synchronized static void RemoveReportQueue(IClient client){
		if(_ReportQueue.containsKey(client)){
			_ReportQueue.remove(client);
		}
	}
	
	/**
	 * Get ReportQueue Instance
	 * @return
	 */
	public synchronized static IReport GetInstance(IClient client){
		if(_ReportQueue.get(client) == null)
			CreateReportQueue(client);
		
		return _ReportQueue.get(client);
	}
}
