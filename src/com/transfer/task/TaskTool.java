package com.transfer.task;

import java.util.ArrayList;
import java.util.List;

import com.transfer.socket.SendPoolManager;
import com.util.custom.ITask;

class TaskTool implements ITaskTool {
	
	//task queue
	private List<ITask> mTaskTool = new ArrayList<ITask>();

	private enum HandleType{
		Add,
		Remove,
		Dequeue
	}
	
	/**
	 * Add
	 * @param task
	 */
	public void add(ITask task){
		HandlerContext(HandleType.Add, task);
	}
	
	/**
	 * Remove
	 * @param task
	 */
	public void remove(ITask task){
		HandlerContext(HandleType.Remove, task);
	}
	
	/**
	 * Dequeue
	 * @return
	 */
	public ITask dequeue(){
		return HandlerContext(HandleType.Dequeue, null);
	}
	
	/**
	 * Whether have tasks
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return mTaskTool.size() > 0 ? true : false;
	}
	
	/**
	 * Handle Context
	 * @param type
	 * @param task
	 * @return
	 */
	private synchronized ITask HandlerContext(HandleType type, ITask task){
		ITask result = null;
		
		if(type == HandleType.Add){
			if(!mTaskTool.contains(task)){
				mTaskTool.add(task);
				
				SendPoolManager.add(task.getClient());
			}
		}else if(type == HandleType.Remove){
			if(mTaskTool.contains(task))
				mTaskTool.remove(task);
		}else if(type == HandleType.Dequeue){
			if(mTaskTool.size() > 0){
				result = mTaskTool.get(0);
				mTaskTool.remove(0);
			}
		}
		
		return result;
	}

}
