package com.transfer.report;

import java.util.HashMap;
import java.util.Map;

import com.util.custom.IClient;

/**
 * Report Pool
 * @author Roy
 *
 */
class ReportPool {
		
		//Client queue
		private static Map<String, ReportSingle> sReportPool = new HashMap<String, ReportSingle>();
		
		/**
		 * Get ReportSocket Instance
		 * @return
		 */
		public synchronized static ReportSingle get(IClient client){
			return sReportPool.get(client.getIP());
		}
		
		/**
		 * Create reportSocket queue
		 */
		public synchronized static void create(IClient client){
			if(!sReportPool.containsKey(client.getIP())){
				sReportPool.put(client.getIP(), new ReportSingle(client));
			}
		}
		
		/**
		 * Remove reportSocket queue
		 * @param client
		 */
		public synchronized static void remove(IClient client){
			if(sReportPool.containsKey(client.getIP())){
				ReportSingle rs = sReportPool.get(client.getIP());
				rs.stop();
				rs.resume();
				
				sReportPool.remove(client);
			}
		}
		
		/**
		 * Whether is exist
		 * @param client
		 * @return
		 */
		public synchronized static boolean isContain(IClient client){
			return sReportPool.containsKey(client.getIP());
		}
}
