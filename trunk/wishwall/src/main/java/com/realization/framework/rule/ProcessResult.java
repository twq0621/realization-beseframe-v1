/**
 * 
 */
package com.realization.framework.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.realization.framework.messaging.IpcMessage;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-11   上午11:12:11
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class ProcessResult {

	private List<IpcMessage> results = new ArrayList<IpcMessage>();
	
	private Map<String,Object> resultMap = new HashMap<String, Object>();
	
	public void addResponseMessage(IpcMessage ipc){
		this.getResults().add(ipc);
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<IpcMessage> results) {
		this.results = results;
	}

	/**
	 * @return the results
	 */
	public List<IpcMessage> getResults() {
		return results;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}
	
	
}
