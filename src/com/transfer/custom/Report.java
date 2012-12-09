package com.transfer.custom;

import com.util.custom.IClient;
import com.util.custom.IReport;


public class Report implements IReport{
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
