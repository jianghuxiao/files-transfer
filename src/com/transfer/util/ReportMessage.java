package com.transfer.util;

public class ReportMessage {
	private IClient client;
	
	/**
	 * set
	 * @param client
	 */
	public void setClient(IClient client){
		this.client = client;
	}
	
	/**
	 * get
	 * @return
	 */
	public IClient getClient(){
		return this.client;
	}
}
