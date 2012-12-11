package com.transfer.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.transfer.cmd.DataPackage;
import com.transfer.custom.Task;
import com.transfer.task.TaskToolManager;
import com.util.Config;
import com.util.LogTool;
import com.util.custom.IClient;
import com.util.custom.ITask;

/**
 * Send Operation
 * @author Roy
 *
 */
class SendSingle {
	private IClient mClient = null;
	private ITask mTask = null;
	
	/**
	 * construct
	 * @param pool
	 * @param task
	 */
	public SendSingle(ITask task){
		if(task == null || task.getClient() == null)
			return;

		mClient = task.getClient();
		mTask = task;
		
		SendPoolManager.get(mTask.getClient()).increase();
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
			
			boolean isException = false;
			
			try {
				socket = new Socket(mClient.getIP(), Config.PORT);
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				
				while(mTask != null){
					FileInfo fileInfo = parseFile(mTask);
					
					sendHeadInfo(out, fileInfo.filename, fileInfo.fileSize);
					sendContent(fileInfo.in, out);
					
					Delay(10);	
					
					mTask = TaskToolManager.get(mClient).dequeue();
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				LogTool.printException(e);
				
				isException = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LogTool.printException(e);
				
				isException = true;
			}finally{
				try{
					in.close();
					out.close();
					socket.close();
				}catch(IOException e){}
				
				SendPoolManager.get(mTask.getClient()).reduce();
				
				if(isException)
					handleException(mTask);
			}
		}
	};
	
	private class FileInfo{
		String filename = null;
		long fileSize = 0;
		FileInputStream in = null;
	}
	
	/**
	 * parse file
	 * @param task
	 * @return
	 * @throws FileNotFoundException
	 */
	private FileInfo parseFile(ITask task) throws FileNotFoundException{
		FileInfo info = new FileInfo();
		
		File file = new File(task.getFilePath());
		FileInputStream fileIn = new FileInputStream(file);
		
		info.filename = file.getName();
		info.fileSize = file.length();
		info.in = fileIn;
		
		System.out.println("filename: " + info.filename + "  socket Count : " + SendPoolManager.get(mTask.getClient()).getSocketCount());
		
		return info;
	}
	
	/**
	 * send head file infomation
	 * @param out
	 * @param filename
	 * @param fileSize
	 * @throws IOException
	 */
	private void sendHeadInfo(DataOutputStream out, String filename, long fileSize) throws IOException{
		out.writeUTF(DataPackage.createHeadInfo(filename, fileSize, null));
		out.flush();
	}
	
	/**
	 * send file content infomation
	 * @param fileIn
	 * @param out
	 * @throws IOException
	 */
	private void sendContent(FileInputStream fileIn, DataOutputStream out) throws IOException{        
        int bufferCapacity = 1024;	//default: 1k; filesize >10M : 1M; filesize > 100M : 10M
        int off = bufferCapacity;
        int readSize;
        int readTotalSize = 0;
        int fileSize = fileIn.available();
        
        
        if(fileSize > 1024*1024*100)
        	bufferCapacity *= 100;
        else if(fileSize > 1024*1024*10)
        	bufferCapacity *= 10;
        
        byte[] bytes = new byte[bufferCapacity];

        if(fileSize - readTotalSize < off)
        	off = fileSize - readTotalSize;
        
        while ((readSize = fileIn.read(bytes, 0, off)) > 0) {
        	readTotalSize += readSize;
        	out.write(bytes, 0, readSize);
		    out.flush();
		    
		    if(fileSize - readTotalSize < off)
	        	off = fileSize - readTotalSize;
        }
        
        System.out.println("Completed");
	}
	
	/**
	 * handle exception 
	 * @param task
	 */
	private void handleException(ITask task){
		Task _task = (Task)task;
		if(_task != null && _task.tryCount < 2){
			TaskToolManager.get(mClient).add(task);
			_task.tryCount++;
		}
	}
	
	/**
	 * delay
	 * @param time
	 */
	private void Delay(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			LogTool.printException(e);
		}
	}
	
}
