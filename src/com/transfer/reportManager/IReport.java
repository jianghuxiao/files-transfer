package com.transfer.reportManager;

import com.transfer.util.ReportMessage;

public interface IReport {
	/**
	 * Add
	 * @param reportMessage
	 */
	public void Add(ReportMessage reportMessage);
	
	/**
	 * Remove
	 * @param reportMessage
	 */
	public void Remove(ReportMessage reportMessage);
	
	/**
	 * Dequeue
	 * @return
	 */
	public ReportMessage Dequeue();
	
	/**
	 * Whether have reports
	 * @return
	 */
	public boolean HasReports();
}
