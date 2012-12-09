package com.transfer.report;

import java.util.HashMap;
import java.util.Map;

import com.util.custom.IClient;

/**
 * Report Pool
 * @author Roy
 *
 */
public class ReportPool {
		
		//Client queue
		private static Map<IClient, ReportSingle> sReportPool = new HashMap<IClient, ReportSingle>();
		
		/**
		 * Get ReportSocket Instance
		 * @return
		 */
		public synchronized static ReportSingle get(IClient client){
			return sReportPool.get(client);
		}
		
		/**
		 * Create reportSocket queue
		 */
		public synchronized static void create(IClient client){
			if(!sReportPool.containsKey(client)){
				sReportPool.put(client, new ReportSingle(client));
			}
		}
		
		/**
		 * Remove reportSocket queue
		 * @param client
		 */
		public synchronized static void remove(IClient client){
			if(sReportPool.containsKey(client)){
				ReportSingle rs = sReportPool.get(client);
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
			return sReportPool.containsKey(client);
		}
}
