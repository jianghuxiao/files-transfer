package com.transfer.reportManager;

import java.util.HashMap;
import java.util.Map;

import com.transfer.util.IClient;

public class ReportSocketManager {
		
		//Client queue
		private static Map<IClient, ReportSocket> sReportSocketQueue = new HashMap<IClient, ReportSocket>();
		
		/**
		 * Create reportSocket queue
		 */
		public synchronized static void createMessageSocket(IClient client){
			if(!sReportSocketQueue.containsKey(client)){
				sReportSocketQueue.put(client, new ReportSocket(client));
			}
		}
		
		/**
		 * Remove reportSocket queue
		 * @param client
		 */
		public synchronized static void removeMessageSocket(IClient client){
			if(sReportSocketQueue.containsKey(client)){
				ReportSocket rs = sReportSocketQueue.get(client);
				rs.stop();
				rs.resume();
				
				sReportSocketQueue.remove(client);
			}
		}
		
		/**
		 * Get ReportSocket Instance
		 * @return
		 */
		public synchronized static ReportSocket getInstance(IClient client){
			return sReportSocketQueue.get(client);
		}
		
		/**
		 * Whether is exist
		 * @param client
		 * @return
		 */
		public synchronized static boolean isExisted(IClient client){
			return sReportSocketQueue.containsKey(client);
		}
}
