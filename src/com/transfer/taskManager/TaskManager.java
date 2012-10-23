package com.transfer.taskManager;

import java.util.HashMap;
import java.util.Map;

import com.transfer.custom.IClient;

public class TaskManager {
	
	//Client queue
	private static Map<IClient, IQueue> sClientQueue = new HashMap<IClient, IQueue>();
	
	/**
	 * Create task form queue
	 */
	public synchronized static void createTaskQueue(IClient client){
		if(!sClientQueue.containsKey(client)){
			sClientQueue.put(client, new TaskQueue());
		}
	}
	
	/**
	 * Remove task form queue
	 * @param client
	 */
	public synchronized static void removeTaskQueue(IClient client){
		if(sClientQueue.containsKey(client)){
			sClientQueue.remove(client);
		}
	}
	
	/**
	 * Get TaskQueue Instance
	 * @return
	 */
	public synchronized static IQueue getInstance(IClient client){
		if(sClientQueue.get(client) == null)
			createTaskQueue(client);
		
		return sClientQueue.get(client);
	}
}
