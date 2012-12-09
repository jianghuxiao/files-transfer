package com.transfer.socket;

import java.util.HashMap;
import java.util.Map;

import com.util.custom.IClient;

/**
 * SendPool Manager
 * @author Roy
 *
 */
public class SendPoolManager {
	
	//Socket Pool Manage
	private static Map<IClient, SendPool> sPoolManager = new HashMap<IClient, SendPool>();
	
	/**
	 * get instance
	 * @param client
	 * @return
	 */
	public static SendPool get(IClient client){
		return sPoolManager.get(client);
	}
	
	/**
	 * Add
	 * @param client
	 * @param sp
	 */
	public static void add(IClient client, SendPool sp){
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
	public static boolean isContain(IClient client){
		return handleContext(HandleType.IsExist, client, null);
	}
	
	private enum HandleType{
		Add,
		Remove,
		IsExist
	}
	private synchronized static boolean handleContext(HandleType type, IClient client, SendPool sp){
		boolean result = false;
		
		if(type == HandleType.Add){
			if(!sPoolManager.containsKey(client))
				sPoolManager.put(client, sp);	
		}else if(type == HandleType.Remove){
			if(sPoolManager.containsKey(client))
				sPoolManager.remove(client);
		}else if(type == HandleType.IsExist){
			result = sPoolManager.containsKey(client);
		}
		
		return result;
	}
}
