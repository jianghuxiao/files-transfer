package com.transfer.task;

import java.util.HashMap;
import java.util.Map;

import com.transfer.custom.Client;
import com.util.custom.IClient;

public class TaskToolManager {
	
	//Client queue
	private static Map<String, ITaskTool> sTaskToolManager = new HashMap<String, ITaskTool>();
	
	/**
	 * Create task form queue
	 */
	public synchronized static void create(IClient client){
		if(!sTaskToolManager.containsKey(client.getIP())){
			sTaskToolManager.put(client.getIP(), new TaskTool());
		}
	}
	
	/**
	 * Remove task form queue
	 * @param client
	 */
	public synchronized static void remove(IClient client){
		if(sTaskToolManager.containsKey(client.getIP())){
			sTaskToolManager.remove(client.getIP());
		}
	}
	
	/**
	 * Get TaskQueue Instance
	 * @return
	 */
	public synchronized static ITaskTool get(IClient client){
		if(sTaskToolManager.get(client.getIP()) == null)
			create(client);
		
		return sTaskToolManager.get(client.getIP());
	}
}
