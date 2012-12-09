package com.app;

import java.io.File;

import com.transfer.DataTransfer;

public class App {
	public static void main(String[] args){
//		String[] files = new String[]{
//				"/home/roy_luo/Roy/photo/eclipse-jee-juno-SR1-linux-gtk.tar.gz",
//				"/home/roy_luo/Roy/photo/nav_logo114.png",
//				"/home/roy_luo/Roy/photo/nav_logo114_4.png",
//				"/home/roy_luo/Roy/photo/nav_logo114_2.png",
//				"/home/roy_luo/Roy/photo/nav_logo114_3.png",
//				"/home/roy_luo/Roy/photo/nav_logo114_5.png",
//				"/home/roy_luo/Roy/photo/69f760edb76ebbee.jpg",
//				"/home/roy_luo/Roy/photo/6366355_092834195136_2.jpg",
//				"/home/roy_luo/Roy/photo/6906103_194648285000_2.jpg",
//				"/home/roy_luo/Roy/photo/01300000860253128749409343844_s.jpg",
//		};
		
		System.out.println("Thread Start");

		File file = new File("E:\\Photo");
		String[] files = new String[file.listFiles().length];
		for(int i=0;i<file.listFiles().length;i++){
			files[i] = file.listFiles()[i].getAbsolutePath();
		}
		
		DataTransfer.Send("127.0.0.1", files);
	}
}
