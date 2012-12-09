package com.transfer.task;

import com.util.custom.ITask;

public interface ITaskTool {
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
	public boolean isEmpty();
}
