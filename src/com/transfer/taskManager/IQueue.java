package com.transfer.taskManager;

import com.transfer.util.ITask;

public interface IQueue {
	/**
	 * Add
	 * @param task
	 */
	public void Add(ITask task);
	
	/**
	 * Remove
	 * @param task
	 */
	public void Remove(ITask task);
	
	/**
	 * Dequeue
	 * @return
	 */
	public ITask Dequeue();
	
	/**
	 * Whether have tasks
	 * @return
	 */
	public boolean HasTasks();
}
