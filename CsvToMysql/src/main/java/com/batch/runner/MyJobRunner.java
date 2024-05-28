package com.batch.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


public class MyJobRunner implements CommandLineRunner {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;
	
//	@Scheduled(cron ="*/1 * * * * " )
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		JobParameters jobParameters=
				new JobParametersBuilder().addLong("time", System.currentTimeMillis())
															  .toJobParameters();
		jobLauncher.run(job, jobParameters);
		
		System.out.println("JOB EXECUTION DONE");
		
	}

}
