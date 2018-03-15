package com.company.elasticsearch.service;

import com.xinwei.nnl.common.domain.ProcessResult;

public class ProcessResultException extends Exception {
	private ProcessResult processResult = null;
	public ProcessResultException(ProcessResult processResult)
	{
		this.processResult = processResult;
	}
	public ProcessResult getProcessResult() {
		return processResult;
	}
	public void setProcessResult(ProcessResult processResult) {
		this.processResult = processResult;
	}
	
}
