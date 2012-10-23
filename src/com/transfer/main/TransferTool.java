package com.transfer.main;

import com.transfer.custom.ITask;
import com.transfer.taskManager.TaskManager;

public class TransferTool {
	/**
	 * Add task
	 * @param task
	 */
	public static void AddTask(ITask task){
		TaskManager.getInstance(task.getClient()).add(task);
	}
	
	/**
	 * Remove task
	 * @param task
	 */
	public static void RemoveTask(ITask task){
		TaskManager.getInstance(task.getClient()).add(task);
	}
}
