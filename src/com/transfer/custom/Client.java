package com.transfer.custom;

import com.util.custom.IClient;

public class Client implements IClient {

	public String mIp = null;
	
	public Client(String ip){
		this.mIp = ip;
	}

}
