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
	private static Map<String, SendPool> sPoolManager = new HashMap<String, SendPool>();
	
	/**
	 * get instance
	 * @param client
	 * @return
	 */
	public static SendPool get(IClient client){
		return sPoolManager.get(client.getIP());
	}
	
	/**
	 * size
	 * @param client
	 * @return
	 */
	public static int poolSize(IClient client){
		int result = 0;
		if(SendPoolManager.get(client) != null)
			result = SendPoolManager.get(client).getSocketCount();
		
		return result;
	}
	
	/**
	 * Add
	 * @param client
	 * @param pool
	 */
	public static void add(IClient client){
		if(!sPoolManager.containsKey(client.getIP()))
			sPoolManager.put(client.getIP(), new SendPool(client));
		else
			SendPoolManager.get(client).run();
	}
	
	/**
	 * Remove
	 * @param client
	 */
	public static void remove(IClient client){
		handleContext(HandleType.Remove, client);
	}
	
	/**
	 * Whether is exist
	 * @param client
	 * @return
	 */
	public static boolean isContain(IClient client){
		return handleContext(HandleType.IsExist, client);
	}
	
	private enum HandleType{
		Add,
		Remove,
		IsExist
	}
	private synchronized static boolean handleContext(HandleType type, IClient client){
		boolean result = false;
		
		if(type == HandleType.Add){
			if(!sPoolManager.containsKey(client.getIP()))
				sPoolManager.put(client.getIP(), new SendPool(client));	
		}else if(type == HandleType.Remove){
			if(sPoolManager.containsKey(client.getIP()))
				sPoolManager.remove(client.getIP());
		}else if(type == HandleType.IsExist){
			result = sPoolManager.containsKey(client.getIP());
		}
		
		return result;
	}
}
