package com.transfer.report;

import java.util.ArrayList;
import java.util.List;

import com.transfer.custom.Report;

/**
 * Report Tool
 * @author Roy
 *
 */
public class ReportTool implements IReportTool{
	
	//report queue
	private List<Report> mReportTool = new ArrayList<Report>();

	private enum HandleType{
		Add,
		Remove,
		Dequeue
	}
	
	/**
	 * Add
	 * @param reportMessage
	 */
	public void add(Report reportMessage){
		HandlerContext(HandleType.Add, reportMessage);
	}
	
	/**
	 * Remove
	 * @param reportMessage
	 */
	public void remove(Report reportMessage){
		HandlerContext(HandleType.Remove, reportMessage);
	}
	
	/**
	 * Dequeue
	 * @return
	 */
	public Report dequeue(){
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
	 * @param reportMessage
	 * @return
	 */
	private synchronized Report HandlerContext(HandleType type, Report reportMessage){
		Report result = null;
		
		if(type == HandleType.Add){
			
			if(!mReportTool.contains(reportMessage)){
				mReportTool.add(reportMessage);
				
				if(!ReportPool.isContain(reportMessage.getClient()))
					new ReportSingle(reportMessage.getClient());
				else
					ReportPool.get(reportMessage.getClient()).resume();
			}
			
		}else if(type == HandleType.Remove){
			
			if(mReportTool.contains(reportMessage))
				mReportTool.remove(reportMessage);
			
		}else if(type == HandleType.Dequeue){
			
			if(mReportTool.size() > 0){
				result = mReportTool.get(0);
				mReportTool.remove(0);
			}
			
		}
		
		return result;
	}
}
