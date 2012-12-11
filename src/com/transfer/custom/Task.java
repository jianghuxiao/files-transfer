package com.transfer.custom;

import com.util.custom.IClient;
import com.util.custom.ITask;

public class Task implements ITask {
	private IClient client = null;
	private String filename = null;
	private String filePath = null;
	
	public int tryCount = 0;
	
	public Task(String filePath){
		this.filePath = filePath;
	}
	
	public void setClient(IClient client){
		this.client = client;
	}

	public IClient getClient() {
		// TODO Auto-generated method stub
		return client;
	}
	
	public void setFilename(String filename){
		this.filename = filename;
	}

	public String getFilename() {
		// TODO Auto-generated method stub
		return filename;
	}

	public String getFilePath() {
		// TODO Auto-generated method stub
		return filePath;
	}
}
