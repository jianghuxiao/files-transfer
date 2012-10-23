package com.transfer.reportManager;

import com.transfer.custom.Report;

public interface IReport {
	/**
	 * Add
	 * @param reportMessage
	 */
	public void add(Report reportMessage);
	
	/**
	 * Remove
	 * @param reportMessage
	 */
	public void remove(Report reportMessage);
	
	/**
	 * Dequeue
	 * @return
	 */
	public Report dequeue();
	
	/**
	 * Whether have reports
	 * @return
	 */
	public boolean hasReports();
}
