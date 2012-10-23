package com.transfer.socketManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.transfer.command.DataPackage;
import com.transfer.taskManager.TaskManager;
import com.transfer.util.Config;
import com.transfer.util.IClient;
import com.transfer.util.ITask;

public class DataSocket {
	
	private SocketPool sSocketPool = null;
	
	private IClient mClient = null;
	private ITask mTask = null;
	
	/**
	 * construct
	 * @param sp
	 * @param task
	 */
	public DataSocket(SocketPool sp, ITask task){
		sSocketPool = sp;
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
				socket = new Socket(mClient.mIp, Config.PORT);
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				
				while(mTask != null){
					File file = new File(mTask.getFilePath());
					FileInputStream fileIn = new FileInputStream(file);

					System.out.println("filename: " + file.getName());
					out.writeUTF(DataPackage.generateStartDataPack(file.getName(), fileIn.available()));
					out.flush();
					
					WriteStream(fileIn, out);
					
					System.out.println("Completed");
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					mTask = TaskManager.getInstance(mClient).dequeue();
				}
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
        while ((readCompletedCount = fileIn.read(bytes, readCount, bytes.length - readCount)) > 0) {
        	readCount += readCompletedCount;
        	if(readCount == bytes.length){
        		out.write(bytes);
		        out.flush();
		        readCount = 0;
		        
		        System.out.println("Read Count: " + readCompletedCount);
        	}
        }
        
        if(readCount > 0){
        	out.write(bytes);
        	out.flush();
        }
	}
	
}
