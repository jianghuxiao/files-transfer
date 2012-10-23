package com.transfer.util;

public class ReportMessage {
	private IClient mClient;
	
	/**
	 * set
	 * @param client
	 */
	public void setClient(IClient client){
		this.mClient = client;
	}
	
	/**
	 * get
	 * @return
	 */
	public IClient getClient(){
		return this.mClient;
	}
}
