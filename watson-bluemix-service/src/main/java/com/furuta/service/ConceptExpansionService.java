package com.furuta.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furuta.bean.ConceptExpansionJob;
import com.furuta.repository.ConceptExpansionRepository;
import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.concept_expansion.v1.ConceptExpansion;
import com.ibm.watson.developer_cloud.concept_expansion.v1.model.Concept;
import com.ibm.watson.developer_cloud.concept_expansion.v1.model.Job;

@Service
public class ConceptExpansionService {

	@Autowired
	private ConceptExpansionRepository repository;

	private ConceptExpansion expansion;

	@PostConstruct
	private void init() {
		expansion = new ConceptExpansion();
	}

	public String evaluate(final String[] seeds) {
		
		final Job job = expansion.createJob(seeds);

		while (expansion.getJobStatus(job) == Job.Status.AWAITING_WORK || 
			   expansion.getJobStatus(job) == Job.Status.IN_FLIGHT) {
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		final List<Concept> concepts = expansion.getJobResult(job);

		final String jobId = job.getId();
		final String jobResult = new Gson().toJson(concepts);
		final String timestamp = getTimestamp();
		
		final ConceptExpansionJob conceptExpansionJob = new ConceptExpansionJob();
		conceptExpansionJob.setJobId(jobId);
		conceptExpansionJob.setSeeds(seeds);
		conceptExpansionJob.setJobResult(jobResult);
		conceptExpansionJob.setTimestamp(timestamp);
		
		repository.save(conceptExpansionJob);
		
		return jobResult;
	}
	
	private String getTimestamp() {
		final Date date = new Date();
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return format.format(date);
	}
}
