package com.transfer.reportManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.transfer.constants.Config;
import com.transfer.custom.IClient;
import com.transfer.custom.Report;
import com.transfer.socketManager.SocketPoolManager;

public class ReportSocket {
	
	private IClient mClient = null;
	private Thread mCurrentThread = null;
	private boolean mIsBreak = false;
	private boolean mIsWaiting = false;
	
	/**
	 * construct
	 * @param client
	 */
	public ReportSocket(IClient client){
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
		if(SocketPoolManager.getInstance(mClient) == null ||
				!ReportManager.getInstance(mClient).hasReports())
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
				socket = new Socket(mClient.mIp, Config.PORT);
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
								
				reportMessageHandler(out);//report handler
				
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
			}
		}
	};
	
	/**
	 * report handler
	 * @param out
	 */
	private void reportMessageHandler(DataOutputStream out){
		try{
			while(true){
				
				Report rm = ReportManager.getInstance(mClient).dequeue();
				if(rm != null){
					out.writeUTF("");
					out.flush();
				}else if(SocketPoolManager.getInstance(mClient) == null ||
						SocketPoolManager.getInstance(mClient).getSocketCount() <= 0){
					break;
				}
				else{
					mIsWaiting = true;
					this.wait();
				}
				
				if(mIsBreak)
					break;
				
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
