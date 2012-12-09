package com.transfer.report;

import java.util.ArrayList;
import java.util.List;

import com.util.custom.IReport;

/**
 * Report Tool
 * @author Roy
 *
 */
class ReportQueue implements IReportQueue{
	
	//report queue
	private List<IReport> mReportTool = new ArrayList<IReport>();

	private enum HandleType{
		Add,
		Remove,
		Dequeue
	}
	
	/**
	 * Add
	 * @param report
	 */
	public void add(IReport report){
		HandlerContext(HandleType.Add, report);
	}
	
	/**
	 * Remove
	 * @param report
	 */
	public void remove(IReport report){
		HandlerContext(HandleType.Remove, report);
	}
	
	/**
	 * Dequeue
	 * @return
	 */
	public IReport dequeue(){
		return HandlerContext(HandleType.Dequeue, null);
	}
	
	/**
	 * Whether have reports
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return mReportTool.size() > 0 ? true : false;
	}
	
	/**
	 * Handle Context
	 * @param type
	 * @param report
	 * @return
	 */
	private synchronized IReport HandlerContext(HandleType type, IReport report){
		IReport result = null;
		
		if(type == HandleType.Add){
			
			if(!mReportTool.contains(report)){
				mReportTool.add(report);
				
				if(!ReportPool.isContain(report.getClient()))
					new ReportSingle(report.getClient());
				else
					ReportPool.get(report.getClient()).resume();
			}
			
		}else if(type == HandleType.Remove){
			
			if(mReportTool.contains(report))
				mReportTool.remove(report);
			
		}else if(type == HandleType.Dequeue){
			
			if(mReportTool.size() > 0){
				result = mReportTool.get(0);
				mReportTool.remove(0);
			}
			
		}
		
		return result;
	}
}
