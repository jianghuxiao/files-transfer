package com.transfer;

import com.transfer.custom.Client;
import com.transfer.custom.Task;
import com.transfer.task.TaskToolManager;
import com.util.custom.IClient;
import com.util.custom.ITask;

public class DataTransfer {
	/**
	 * Add task
	 * @param task
	 */
	public static void Send(String ipAddress, String[] filesPath){
		IClient client = new Client(ipAddress);
		for(int i=0;i<filesPath.length;i++){
			Task task = new Task(filesPath[i]);
			task.setClient(client);
			
			TaskToolManager.get(client).add(task);
		}
	}
	
	/**
	 * Remove task
	 * @param task
	 */
	public static void RemoveTask(ITask task){
		TaskToolManager.get(task.getClient()).add(task);
	}
}
