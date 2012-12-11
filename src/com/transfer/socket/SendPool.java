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
	
	private final int MAX_SOCKET_COUNT = 3;//max socket count
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
		
		if(queue.isEmpty())
			return;
		
		if(CURRENT_SOCKET_COUNT >= MAX_SOCKET_COUNT)
			return;

		ITask task = queue.dequeue();
		new SendSingle(task);
	}
	
	/**
	 * increase socket count
	 */
	public synchronized void increase(){
		CURRENT_SOCKET_COUNT++;
	}
	
	/**
	 * reduce socket count
	 */
	public synchronized void reduce(){
		CURRENT_SOCKET_COUNT --;
		
		run();
		
		if(CURRENT_SOCKET_COUNT == 0)
			handleClient();
	}
	
	/**
	 * get Socket count
	 * @return
	 */
	public synchronized int getSocketCount(){
		return CURRENT_SOCKET_COUNT;
	}
	
	/**
	 * handle client
	 */
	private void handleClient(){
		if(!TaskToolManager.get(mClient).isEmpty()){
			SendPoolManager.get(mClient).run();
		}else{
			SendPoolManager.remove(mClient);
			TaskToolManager.remove(mClient);
			ReportPoolManager.remove(mClient);
		}
	}
}
