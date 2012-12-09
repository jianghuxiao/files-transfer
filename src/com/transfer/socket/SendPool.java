package com.transfer.socket;

import com.transfer.report.ReportPoolManager;
import com.transfer.task.ITaskTool;
import com.transfer.task.TaskToolManager;
import com.util.custom.IClient;
import com.util.custom.ITask;

/**
 * SendPool
 * @author Roy
 *
 */
class SendPool {
	
	private IClient mClient = null;
	
	private final int MAX_SOCKET_COUNT = 1;//Max socket count
	private int CURRENT_SOCKET_COUNT = 0;//current socket count

	/**
	 * construct
	 * @param client
	 */
	public SendPool(IClient client){
		mClient = client;
		
		run();
	}
	
	/**
	 * Run
	 */
	public void run(){
		ITaskTool queue = TaskToolManager.get(mClient);
		if(queue == null)
			return;
		
		if(queue.isEmpty()){
			if(CURRENT_SOCKET_COUNT >= MAX_SOCKET_COUNT)
				return;

			ITask task = queue.dequeue();

			CURRENT_SOCKET_COUNT++;
			new SendSingle(this, task);
		}
	}
	
	/**
	 * Reduce socket count
	 */
	public synchronized void reduceSocketCount(){
		CURRENT_SOCKET_COUNT --;
		
		if(CURRENT_SOCKET_COUNT == 0){
			if(TaskToolManager.get(mClient).isEmpty()){
				SendPoolManager.get(mClient).run();
			}else{
				SendPoolManager.remove(mClient);
				TaskToolManager.remove(mClient);
				ReportPoolManager.remove(mClient);
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
