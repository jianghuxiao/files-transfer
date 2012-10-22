package com.transfer.main;

import com.transfer.taskManager.TaskManager;
import com.transfer.util.ITask;

public class TransferTool {
	/**
	 * Add task
	 * @param task
	 */
	public static void AddTask(ITask task){
		TaskManager.GetInstance(task.GetClient()).Add(task);
	}
	
	/**
	 * Remove task
	 * @param task
	 */
	public static void RemoveTask(ITask task){
		TaskManager.GetInstance(task.GetClient()).Add(task);
	}
}
