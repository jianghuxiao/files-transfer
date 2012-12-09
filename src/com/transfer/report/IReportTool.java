package com.transfer.report;

import com.transfer.custom.Report;

/**
 * IReportTool
 * @author Roy
 *
 */
public interface IReportTool {
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
	public boolean isEmpty();
}
