package com.transfer.task;

import java.util.HashMap;
import java.util.Map;

import com.util.custom.IClient;

public class TaskToolManager {
	
	//Client queue
	private static Map<IClient, ITaskTool> sClientQueue = new HashMap<IClient, ITaskTool>();
	
	/**
	 * Create task form queue
	 */
	public synchronized static void create(IClient client){
		if(!sClientQueue.containsKey(client)){
			sClientQueue.put(client, new TaskTool());
		}
	}
	
	/**
	 * Remove task form queue
	 * @param client
	 */
	public synchronized static void remove(IClient client){
		if(sClientQueue.containsKey(client)){
			sClientQueue.remove(client);
		}
	}
	
	/**
	 * Get TaskQueue Instance
	 * @return
	 */
	public synchronized static ITaskTool get(IClient client){
		if(sClientQueue.get(client) == null)
			create(client);
		
		return sClientQueue.get(client);
	}
}
