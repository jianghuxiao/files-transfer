package com.transfer.reportManager;

import java.util.ArrayList;
import java.util.List;

import com.transfer.custom.Report;

public class ReportQueue implements IReport{
	
	//report queue
	private List<Report> mReportQueue = new ArrayList<Report>();

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
	public boolean hasReports() {
		// TODO Auto-generated method stub
		return mReportQueue.size() > 0 ? true : false;
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
			
			if(!mReportQueue.contains(reportMessage)){
				mReportQueue.add(reportMessage);
				
				if(!ReportSocketManager.isExisted(reportMessage.getClient()))
					new ReportSocket(reportMessage.getClient());
				else
					ReportSocketManager.getInstance(reportMessage.getClient()).resume();
			}
			
		}else if(type == HandleType.Remove){
			
			if(mReportQueue.contains(reportMessage))
				mReportQueue.remove(reportMessage);
			
		}else if(type == HandleType.Dequeue){
			
			if(mReportQueue.size() > 0){
				result = mReportQueue.get(0);
				mReportQueue.remove(0);
			}
			
		}
		
		return result;
	}
}
