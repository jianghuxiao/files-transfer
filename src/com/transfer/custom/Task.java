package com.transfer.custom;

public class Task implements ITask {
	private IClient mClient = null;
	private String mFilename = null;
	private String mFilePath = null;
	
	public Task(String filePath){
		this.mFilePath = filePath;
	}
	
	public void setClient(IClient client){
		this.mClient = client;
	}

	public IClient getClient() {
		// TODO Auto-generated method stub
		return mClient;
	}

	public String getFilename() {
		// TODO Auto-generated method stub
		return mFilename;
	}

	public String getFilePath() {
		// TODO Auto-generated method stub
		return mFilePath;
	}
}
