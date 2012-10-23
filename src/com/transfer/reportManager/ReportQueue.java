package com.transfer.reportManager;

import java.util.ArrayList;
import java.util.List;

import com.transfer.util.ReportMessage;

public class ReportQueue implements IReport{
	
	//report queue
	private List<ReportMessage> mReportQueue = new ArrayList<ReportMessage>();

	private enum HandleType{
		Add,
		Remove,
		Dequeue
	}
	
	/**
	 * Add
	 * @param reportMessage
	 */
	public void add(ReportMessage reportMessage){
		HandlerContext(HandleType.Add, reportMessage);
	}
	
	/**
	 * Remove
	 * @param reportMessage
	 */
	public void remove(ReportMessage reportMessage){
		HandlerContext(HandleType.Remove, reportMessage);
	}
	
	/**
	 * Dequeue
	 * @return
	 */
	public ReportMessage dequeue(){
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
	private synchronized ReportMessage HandlerContext(HandleType type, ReportMessage reportMessage){
		ReportMessage result = null;
		
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
