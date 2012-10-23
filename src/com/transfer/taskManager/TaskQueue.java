package com.transfer.taskManager;

import java.util.ArrayList;
import java.util.List;

import com.transfer.socketManager.SocketPool;
import com.transfer.socketManager.SocketPoolManager;
import com.transfer.util.ITask;

public class TaskQueue implements IQueue {
	
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
	public boolean hasTasks() {
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
				
				if(!SocketPoolManager.isExist(task.getClient()))
					new SocketPool(task.getClient());
				else
					SocketPoolManager.getInstance(task.getClient()).run();
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
