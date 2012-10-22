package com.transfer.reportManager;

import java.util.ArrayList;
import java.util.List;

import com.transfer.util.ReportMessage;

public class ReportQueue implements IReport{
	//task queue
	private List<ReportMessage> _ReportQueue = new ArrayList<ReportMessage>();

	private enum HandleType{
		Add,
		Remove,
		Dequeue
	}
	
	/**
	 * Add
	 * @param task
	 */
	public void Add(ReportMessage reportMessage){
		HandlerContext(HandleType.Add, reportMessage);
	}
	
	/**
	 * Remove
	 * @param task
	 */
	public void Remove(ReportMessage reportMessage){
		HandlerContext(HandleType.Remove, reportMessage);
	}
	
	/**
	 * Dequeue
	 * @return
	 */
	public ReportMessage Dequeue(){
		return HandlerContext(HandleType.Dequeue, null);
	}
	
	/**
	 * Whether have tasks
	 */
	public boolean HasReports() {
		// TODO Auto-generated method stub
		return _ReportQueue.size() > 0 ? true : false;
	}
	
	/**
	 * Handle Context
	 * @param type
	 * @param task
	 * @return
	 */
	private synchronized ReportMessage HandlerContext(HandleType type, ReportMessage reportMessage){
		ReportMessage result = null;
		
		if(type == HandleType.Add){
			
			if(!_ReportQueue.contains(reportMessage)){
				_ReportQueue.add(reportMessage);
				
				if(!ReportSocketManager.IsExisted(reportMessage.getClient()))
					new ReportSocket(reportMessage.getClient());
				else
					ReportSocketManager.GetInstance(reportMessage.getClient()).activeReportThread();
			}
			
		}else if(type == HandleType.Remove){
			
			if(_ReportQueue.contains(reportMessage))
				_ReportQueue.remove(reportMessage);
			
		}else if(type == HandleType.Dequeue){
			
			if(_ReportQueue.size() > 0){
				result = _ReportQueue.get(0);
				_ReportQueue.remove(0);
			}
			
		}
		
		return result;
	}
}
