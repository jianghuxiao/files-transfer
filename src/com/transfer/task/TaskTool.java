package com.transfer.task;

import java.util.ArrayList;
import java.util.List;

import com.transfer.socket.SendPool;
import com.transfer.socket.SendPoolManager;
import com.util.custom.ITask;

public class TaskTool implements ITaskTool {
	
	//task queue
	private List<ITask> mTaskQueue = new ArrayList<ITask>();

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
		return mTaskQueue.size() > 0 ? true : false;
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
			if(!mTaskQueue.contains(task)){
				mTaskQueue.add(task);
				
				if(!SendPoolManager.isContain(task.getClient()))
					new SendPool(task.getClient());
				else
					SendPoolManager.get(task.getClient()).run();
			}
		}else if(type == HandleType.Remove){
			if(mTaskQueue.contains(task))
				mTaskQueue.remove(task);
		}else if(type == HandleType.Dequeue){
			if(mTaskQueue.size() > 0){
				result = mTaskQueue.get(0);
				mTaskQueue.remove(0);
			}
		}
		
		return result;
	}

}
