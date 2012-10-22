package com.transfer.reportManager;

import com.transfer.util.ReportMessage;

public interface IReport {
	/**
	 * Add
	 * @param task
	 */
	public void Add(ReportMessage reportMessage);
	
	/**
	 * Remove
	 * @param task
	 */
	public void Remove(ReportMessage reportMessage);
	
	/**
	 * Dequeue
	 * @return
	 */
	public ReportMessage Dequeue();
	
	/**
	 * Whether have tasks
	 * @return
	 */
	public boolean HasReports();
}
