package com.transfer.taskManager;

import com.transfer.custom.ITask;

public interface IQueue {
	/**
	 * Add
	 * @param task
	 */
	public void add(ITask task);
	
	/**
	 * Remove
	 * @param task
	 */
	public void remove(ITask task);
	
	/**
	 * Dequeue
	 * @return
	 */
	public ITask dequeue();
	
	/**
	 * Whether have tasks
	 * @return
	 */
	public boolean hasTasks();
}
