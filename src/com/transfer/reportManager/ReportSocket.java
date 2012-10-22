package com.transfer.reportManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.transfer.socketManager.SocketPoolManager;
import com.transfer.util.Config;
import com.transfer.util.IClient;
import com.transfer.util.ReportMessage;

public class ReportSocket {
	private IClient _Client = null;
	private Thread _CurrentThread = null;
	private boolean isBreak = false;
	private boolean isWaiting = false;
	
	public ReportSocket(IClient client){
		_Client = client;
		
		_CurrentThread = new Thread(runnable);
		_CurrentThread.start();
	}
	
	/**
	 * notify thread
	 */
	public synchronized void activeReportThread(){
		if(isWaiting){
			isWaiting = false;
			_CurrentThread.notify();
		}
	}
	
	/**
	 * bread thread run
	 */
	public synchronized void breakThread(){
		if(SocketPoolManager.getInstance(_Client) == null ||
				!ReportManager.GetInstance(_Client).HasReports())
		isBreak = true;
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
				socket = new Socket(_Client.Ip, Config.Port);
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				
				while(true){
					ReportMessage rm = ReportManager.GetInstance(_Client).Dequeue();
					if(rm != null){
						out.writeUTF("");
						out.flush();
					}else if(SocketPoolManager.getInstance(_Client) == null ||
							SocketPoolManager.getInstance(_Client).getSocketCount() <= 0){
						break;
					}
					else{
						isWaiting = true;
						this.wait();
					}
					
					if(isBreak)
						break;
				}	
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
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
	
}
