package com.transfer.custom;

import com.util.custom.IClient;

public class Client implements IClient {

	private String mIp = null;
	
	public Client(String ip){
		this.mIp = ip;
	}

	public String getIP() {
		// TODO Auto-generated method stub
		return mIp;
	}

}
