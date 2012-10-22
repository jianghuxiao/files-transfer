package com.transfer.reportManager;

import java.util.HashMap;
import java.util.Map;

import com.transfer.util.IClient;

public class ReportSocketManager {
		//Client queue
		private static Map<IClient, ReportSocket> _ReportSocketQueue = new HashMap<IClient, ReportSocket>();
		
		/**
		 * Create task form queue
		 */
		public synchronized static void CreateMessageSocket(IClient client){
			if(!_ReportSocketQueue.containsKey(client)){
				_ReportSocketQueue.put(client, new ReportSocket(client));
			}
		}
		
		/**
		 * Remove task form queue
		 * @param client
		 */
		public synchronized static void RemoveMessageSocket(IClient client){
			if(_ReportSocketQueue.containsKey(client)){
				ReportSocket rs = _ReportSocketQueue.get(client);
				rs.breakThread();
				rs.activeReportThread();
				
				_ReportSocketQueue.remove(client);
			}
		}
		
		/**
		 * Get TaskQueue Instance
		 * @return
		 */
		public synchronized static ReportSocket GetInstance(IClient client){
			return _ReportSocketQueue.get(client);
		}
		
		/**
		 * Whether is exist
		 * @param client
		 * @return
		 */
		public synchronized static boolean IsExisted(IClient client){
			return _ReportSocketQueue.containsKey(client);
		}
}
