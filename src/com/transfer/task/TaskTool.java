package com.transfer.task;

import java.util.ArrayList;
import java.util.List;

import com.transfer.socket.SendPoolManager;
import com.util.custom.ITask;

class TaskTool implements ITaskTool {
	
	//task queue
	private List<ITask> mTaskList = new ArrayList<ITask>();

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
		return mTaskList.size() > 0 ? false : true;
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
			if(!mTaskList.contains(task)){
				mTaskList.add(task);
				
				SendPoolManager.add(task.getClient());
			}
		}else if(type == HandleType.Remove){
			if(mTaskList.contains(task))
				mTaskList.remove(task);
		}else if(type == HandleType.Dequeue){
			if(mTaskList.size() > 0){
				result = mTaskList.get(0);
				mTaskList.remove(0);
			}
		}
		
		return result;
	}

}
