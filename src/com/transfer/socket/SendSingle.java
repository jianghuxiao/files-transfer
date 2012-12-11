package com.transfer.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.transfer.cmd.DataPackage;
import com.transfer.custom.Task;
import com.transfer.task.TaskToolManager;
import com.util.Config;
import com.util.custom.IClient;
import com.util.custom.ITask;

/**
 * Send Operation
 * @author Roy
 *
 */
class SendSingle {
	
	private SendPool sSocketPool = null;
	
	private IClient mClient = null;
	private ITask mTask = null;
	
	/**
	 * construct
	 * @param pool
	 * @param task
	 */
	public SendSingle(SendPool pool, ITask task){
		sSocketPool = pool;
		mClient = task.getClient();
		mTask = task;
		
		(new Thread(runnable)).start();
	}
	
	/**
	 * define a runnable class
	 */
	Runnable runnable = new Runnable(){
		public void run() {
			// TODO Auto-generated method stub
			Socket socket = null;
			DataOutputStream out = null;
			DataInputStream in = null;
			try {
				socket = new Socket(mClient.getIP(), Config.PORT);
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				
				while(mTask != null){
					File file = new File(mTask.getFilePath());
					FileInputStream fileIn = new FileInputStream(file);
					
					System.out.println("filename: " + file.getName() + "  socket Count : " + sSocketPool.getSocketCount());
					out.writeUTF(DataPackage.generateStartDataPack(file.getName(), fileIn.available()));
					out.flush();
					
					WriteStream(fileIn, out);
					
					System.out.println("Completed");
					
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					mTask = TaskToolManager.get(mClient).dequeue();
				}
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				Task task = (Task)mTask;
				if(task != null && task.tryCount < 2){
					TaskToolManager.get(mClient).add(mTask);
					task.tryCount++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				Task task = (Task)mTask;
				if(task != null && task.tryCount < 2){
					TaskToolManager.get(mClient).add(mTask);
					task.tryCount++;
				}
			}finally{
				try{
					in.close();
					out.close();
					socket.close();
				}catch(IOException e){}
				
				sSocketPool.reduceSocketCount();
			}
		}
	};
	
	/*
	 * Write Stream
	 */
	private void WriteStream(FileInputStream fileIn, DataOutputStream out) throws IOException{
		//write file
        int readCompletedCount;
        int MaxOpacity = 1024;	//default: 1k; >10M : 1M; > 100M : 10M
        if(fileIn.available() > 1024*1024*100)
        	MaxOpacity *= 100;
        else if(fileIn.available() > 1024*1024*10)
        	MaxOpacity *= 10;
        
        byte[] bytes = new byte[MaxOpacity];
        int readCount = 0;
        int readLen = MaxOpacity;
        int fileSize = fileIn.available();
        if(fileSize - readCount < readLen)
        	readLen = fileSize - readCount;
        while ((readCompletedCount = fileIn.read(bytes, 0, readLen)) > 0) {
        	readCount += readCompletedCount;
        	out.write(bytes, 0, readCompletedCount);
		    out.flush();
		    
		    if(fileSize - readCount < readLen)
	        	readLen = fileSize - readCount;
        }
	}
	
}
