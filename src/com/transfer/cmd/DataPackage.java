package com.transfer.cmd;

public class DataPackage {
	
	public static String createHeadInfo(String filename, long filesize, String message){
		if(message == null)
			message = "null";
		
		return filename + "," + filesize + "," + message;
	}
	
	public static String[] parsingCommand(String command){
		String[] arr = command.split(",");
		return arr;
	}
	
}
