package com.transfer.report;

import com.util.custom.IReport;

/**
 * IReportTool
 * @author Roy
 *
 */
public interface IReportQueue {
	/**
	 * Add
	 * @param report
	 */
	public void add(IReport report);
	
	/**
	 * Remove
	 * @param report
	 */
	public void remove(IReport report);
	
	/**
	 * Dequeue
	 * @return
	 */
	public IReport dequeue();
	
	/**
	 * Whether have reports
	 * @return
	 */
	public boolean isEmpty();
}
