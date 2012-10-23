package com.transfer.cmd;

public class DataPackage {
	public static String generateStartDataPack(String filename, double filesize){
		return Command.START_SEND + "," + filename + "," + filesize;
	}
	
	public static String generateEndDataPack(){
		return Command.END_SEND + "";
	}
	
	public static String[] parsingCommand(String command){
		String[] arr = command.split(",");
		return arr;
	}
}
