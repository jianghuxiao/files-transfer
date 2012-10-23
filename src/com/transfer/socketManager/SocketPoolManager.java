package com.transfer.socketManager;

import java.util.HashMap;
import java.util.Map;

import com.transfer.util.IClient;

public class SocketPoolManager {
	
	//Socket Manage
	private static Map<IClient, SocketPool> sSockerPoolManager = new HashMap<IClient, SocketPool>();
	
	/**
	 * Add
	 * @param client
	 * @param sp
	 */
	public static void add(IClient client, SocketPool sp){
		handleContext(HandleType.Add, client, sp);
	}
	
	/**
	 * Remove
	 * @param client
	 */
	public static void remove(IClient client){
		handleContext(HandleType.Remove, client, null);
	}
	
	/**
	 * Whether is exist
	 * @param client
	 * @return
	 */
	public static boolean isExist(IClient client){
		return handleContext(HandleType.IsExist, client, null);
	}
	
	/**
	 * get instance
	 * @param client
	 * @return
	 */
	public static SocketPool getInstance(IClient client){
		return sSockerPoolManager.get(client);
	}
	
	private enum HandleType{
		Add,
		Remove,
		IsExist
	}
	private synchronized static boolean handleContext(HandleType type, IClient client, SocketPool sp){
		boolean result = false;
		
		if(type == HandleType.Add){
			if(!sSockerPoolManager.containsKey(client))
				sSockerPoolManager.put(client, sp);	
		}else if(type == HandleType.Remove){
			if(sSockerPoolManager.containsKey(client))
				sSockerPoolManager.remove(client);
		}else if(type == HandleType.IsExist){
			result = sSockerPoolManager.containsKey(client);
		}
		
		return result;
	}
}
