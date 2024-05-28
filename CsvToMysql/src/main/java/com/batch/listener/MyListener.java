package com.batch.listener;



import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.BeforeJob;

public class MyListener implements JobExecutionListener {
	
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
	System.out.println("Started Date and Time : "+new Date());
	System.out.println("status : "+jobExecution.getStatus());
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("Endeded Date and Time : "+new Date());
		System.out.println("status : "+jobExecution.getStatus());
	} 
	
}
