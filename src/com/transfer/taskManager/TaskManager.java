package com.transfer.taskManager;

import java.util.HashMap;
import java.util.Map;

import com.transfer.util.IClient;

public class TaskManager {
	//Client queue
	private static Map<IClient, IQueue> _ClientQueue = new HashMap<IClient, IQueue>();
	
	/**
	 * Create task form queue
	 */
	public synchronized static void CreateTaskQueue(IClient client){
		if(!_ClientQueue.containsKey(client)){
			_ClientQueue.put(client, new TaskQueue());
		}
	}
	
	/**
	 * Remove task form queue
	 * @param client
	 */
	public synchronized static void RemoveTaskQueue(IClient client){
		if(_ClientQueue.containsKey(client)){
			_ClientQueue.remove(client);
		}
	}
	
	/**
	 * Get TaskQueue Instance
	 * @return
	 */
	public synchronized static IQueue GetInstance(IClient client){
		if(_ClientQueue.get(client) == null)
			CreateTaskQueue(client);
		
		return _ClientQueue.get(client);
	}
}
