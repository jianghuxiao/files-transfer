package com.transfer.reportManager;

import com.transfer.util.ReportMessage;

public interface IReport {
	/**
	 * Add
	 * @param reportMessage
	 */
	public void add(ReportMessage reportMessage);
	
	/**
	 * Remove
	 * @param reportMessage
	 */
	public void remove(ReportMessage reportMessage);
	
	/**
	 * Dequeue
	 * @return
	 */
	public ReportMessage dequeue();
	
	/**
	 * Whether have reports
	 * @return
	 */
	public boolean hasReports();
}
