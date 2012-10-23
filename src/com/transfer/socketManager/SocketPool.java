package com.transfer.socketManager;

import com.transfer.custom.IClient;
import com.transfer.custom.ITask;
import com.transfer.reportManager.ReportSocketManager;
import com.transfer.taskManager.IQueue;
import com.transfer.taskManager.TaskManager;

public class SocketPool {
	
	private IClient mClient = null;
	
	private final int MAX_SOCKET_COUNT = 1;//Max socket count
	private int CURRENT_SOCKET_COUNT = 0;//current socket count

	/**
	 * construct
	 * @param client
	 */
	public SocketPool(IClient client){
		mClient = client;
		
		//add SocketPool to SocketPoolManager
		SocketPoolManager.add(mClient, this);
		
		run();
	}
	
	/**
	 * Run
	 */
	public void run(){
		IQueue queue = TaskManager.getInstance(mClient);
		if(queue == null)
			return;
		
		if(queue.hasTasks()){
			if(CURRENT_SOCKET_COUNT >= MAX_SOCKET_COUNT)
				return;

			ITask task = queue.dequeue();

			CURRENT_SOCKET_COUNT++;
			new DataSocket(this, task);
		}
	}
	
	/**
	 * Reduce socket count
	 */
	public synchronized void reduceSocketCount(){
		CURRENT_SOCKET_COUNT --;
		
		if(CURRENT_SOCKET_COUNT == 0){
			if(TaskManager.getInstance(mClient).hasTasks()){
				SocketPoolManager.getInstance(mClient).run();
			}else{
				SocketPoolManager.remove(mClient);
				TaskManager.removeTaskQueue(mClient);
				
				if(ReportSocketManager.isExisted(mClient))
					ReportSocketManager.removeMessageSocket(mClient);
			}
		}
	}
	
	/**
	 * get Socket count
	 * @return
	 */
	public synchronized int getSocketCount(){
		return CURRENT_SOCKET_COUNT;
	}
}
