package com.app;

import com.transfer.DataTransfer;
import com.transfer.custom.Client;
import com.transfer.custom.Task;
import com.util.custom.IClient;

public class App {
	public static void main(String[] args){
		String[] files = new String[]{
				"/home/roy_luo/Roy/photo/eclipse-jee-juno-SR1-linux-gtk.tar.gz",
				"/home/roy_luo/Roy/photo/nav_logo114.png",
				"/home/roy_luo/Roy/photo/nav_logo114_4.png",
				"/home/roy_luo/Roy/photo/nav_logo114_2.png",
				"/home/roy_luo/Roy/photo/nav_logo114_3.png",
				"/home/roy_luo/Roy/photo/nav_logo114_5.png",
				"/home/roy_luo/Roy/photo/69f760edb76ebbee.jpg",
				"/home/roy_luo/Roy/photo/6366355_092834195136_2.jpg",
				"/home/roy_luo/Roy/photo/6906103_194648285000_2.jpg",
				"/home/roy_luo/Roy/photo/01300000860253128749409343844_s.jpg",
		};
		
		String[] files1 = new String[]{
				"/home/roy_luo/Roy/photo/p1/nav_logo1141.png",
				"/home/roy_luo/Roy/photo/p1/nav_logo114_41.png",
				"/home/roy_luo/Roy/photo/p1/nav_logo114_21.png",
				"/home/roy_luo/Roy/photo/p1/nav_logo114_31.png",
				"/home/roy_luo/Roy/photo/p1/nav_logo114_51.png",
				"/home/roy_luo/Roy/photo/p1/69f760edb76ebbee1.jpg",
				"/home/roy_luo/Roy/photo/p1/6366355_092834195136_21.jpg",
				"/home/roy_luo/Roy/photo/p1/6906103_194648285000_21.jpg",
				"/home/roy_luo/Roy/photo/p1/01300000860253128749409343844_s1.jpg",
		};
		
		System.out.println("Thread Start");
		
		DataTransfer.AddTask("127.0.0.1", files);
	}
}