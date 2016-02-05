package com.furuta.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.ibm.watson.developer_cloud.concept_expansion.v1.ConceptExpansion;
import com.ibm.watson.developer_cloud.concept_expansion.v1.model.Concept;
import com.ibm.watson.developer_cloud.concept_expansion.v1.model.Job;

@Service
public class ConceptExpansionService {

	private ConceptExpansion expansion;
	
	@PostConstruct
	private void init () {
		expansion = new ConceptExpansion();
	}
	
	public String createJob(final String[] seeds) {
		final Job job = expansion.createJob(seeds);
		return job.getId();
	}
	
	public List<Concept> getJobResult(final String jobId) {
		final Job job = new Job(jobId);
		return expansion.getJobResult(job);
	}
}
