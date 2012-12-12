package com.util;

public class LogTool {
	public static void printException(Exception ex){
		System.out.println(ex.getMessage());
		ex.printStackTrace();
	}
}
