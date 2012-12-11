package com.transfer.report;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.transfer.socket.SendPoolManager;
import com.util.Config;
import com.util.LogTool;
import com.util.custom.IClient;
import com.util.custom.IReport;

/**
 * Report Operation
 * @author Roy
 *
 */
class ReportSingle {
	
	private IClient mClient = null;
	private Thread mCurrentThread = null;
	private boolean mIsBreak = false;
	private boolean mIsWaiting = false;
	
	/**
	 * construct
	 * @param client
	 */
	public ReportSingle(IClient client){
		mClient = client;
		
		mCurrentThread = new Thread(runnable);
		mCurrentThread.start();
	}
	
	/**
	 * notify thread
	 */
	public synchronized void resume(){
		if(mIsWaiting){
			mIsWaiting = false;
			mCurrentThread.notify();
		}
	}
	
	/**
	 * bread thread run
	 */
	public synchronized void stop(){
		if(SendPoolManager.get(mClient) == null ||
				!ReportPoolManager.get(mClient).isEmpty())
		mIsBreak = true;
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
								
				reportMessageHandler(out);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				LogTool.printException(e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LogTool.printException(e);
			} catch(InterruptedException e){
				// TODO Auto-generated catch block
				LogTool.printException(e);
			}finally{
				try{
					in.close();
					out.close();
					socket.close();
				}catch(IOException e){
					LogTool.printException(e);
				}
			}
		}
	};
	
	/**
	 * report handler
	 * @param out
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	private void reportMessageHandler(DataOutputStream out) throws IOException, InterruptedException{
		while(true){
			IReport rm = ReportPoolManager.get(mClient).dequeue();
			if(rm != null){
				out.writeUTF("");
				out.flush();
			}else if(SendPoolManager.poolSize(mClient) <= 0){
				break;
			}
			else{
				mIsWaiting = true;
				this.wait();
			}
				
			if(mIsBreak)
				break;
		}	
	}
	
}
