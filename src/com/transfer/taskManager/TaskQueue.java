package com.transfer.taskManager;

import java.util.ArrayList;
import java.util.List;

import com.transfer.socketManager.SocketPool;
import com.transfer.socketManager.SocketPoolManager;
import com.transfer.util.ITask;

public class TaskQueue implements IQueue {
	//task queue
	private List<ITask> _TaskQueue = new ArrayList<ITask>();

	private enum HandleType{
		Add,
		Remove,
		Dequeue
	}
	
	/**
	 * Add
	 * @param task
	 */
	public void Add(ITask task){
		HandlerContext(HandleType.Add, task);
	}
	
	/**
	 * Remove
	 * @param task
	 */
	public void Remove(ITask task){
		HandlerContext(HandleType.Remove, task);
	}
	
	/**
	 * Dequeue
	 * @return
	 */
	public ITask Dequeue(){
		return HandlerContext(HandleType.Dequeue, null);
	}
	
	/**
	 * Whether have tasks
	 */
	public boolean HasTasks() {
		// TODO Auto-generated method stub
		return _TaskQueue.size() > 0 ? true : false;
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
			if(!_TaskQueue.contains(task)){
				_TaskQueue.add(task);
				
				if(!SocketPoolManager.isExist(task.GetClient()))
					new SocketPool(task.GetClient());
				else
					SocketPoolManager.getInstance(task.GetClient()).run();
			}
		}else if(type == HandleType.Remove){
			if(_TaskQueue.contains(task))
				_TaskQueue.remove(task);
		}else if(type == HandleType.Dequeue){
			if(_TaskQueue.size() > 0){
				result = _TaskQueue.get(0);
				_TaskQueue.remove(0);
			}
		}
		
		return result;
	}

}
