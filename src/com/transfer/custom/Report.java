package com.transfer.custom;

public class Report {
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
