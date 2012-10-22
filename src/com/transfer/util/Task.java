package com.transfer.util;

public class Task implements ITask {
	private IClient Client = null;
	private String Filename = null;
	private String FilePath = null;
	
	public Task(String filePath){
		this.FilePath = filePath;
	}
	
	public void SetClient(IClient client){
		this.Client = client;
	}

	public IClient GetClient() {
		// TODO Auto-generated method stub
		return Client;
	}

	public String GetFilename() {
		// TODO Auto-generated method stub
		return Filename;
	}

	public String GetFilePath() {
		// TODO Auto-generated method stub
		return FilePath;
	}
}
