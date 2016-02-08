package com.furuta.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.model.Response;
import com.furuta.bean.ConceptExpansionJob;
import com.furuta.repository.ConceptExpansionJobRepository;
import com.ibm.watson.developer_cloud.concept_expansion.v1.ConceptExpansion;
import com.ibm.watson.developer_cloud.concept_expansion.v1.model.Concept;
import com.ibm.watson.developer_cloud.concept_expansion.v1.model.Dataset;
import com.ibm.watson.developer_cloud.concept_expansion.v1.model.Job;

/**
 *  This service delegates the user request (seeds) to Watson Concept Expansion service. 
 */
@Service
public class ConceptExpansionService {

	@Autowired
	private ConceptExpansionJobRepository repository;

	private ConceptExpansion expansion;
	
	@PostConstruct
	private void init() {
		expansion = new ConceptExpansion();
	}
	
	public List<com.furuta.bean.Concept> evaluate(final String datasetId, final String[] seeds) {
		
		final Dataset dataset = getDatasetById(datasetId);
		expansion.setDataset(dataset);
		
		final Job job = expansion.createJob(seeds);

		while (expansion.getJobStatus(job) == Job.Status.AWAITING_WORK || 
			   expansion.getJobStatus(job) == Job.Status.IN_FLIGHT) {
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		final List<Concept> jobResult = expansion.getJobResult(job);
		final List<com.furuta.bean.Concept> concepts = convert(jobResult);
		
		saveJob(seeds, job, concepts);

		return concepts;
	}
	
	public List<ConceptExpansionJob> listAll() {
		return repository.listAllSortByTimestampDesc();
	}
	
	private Dataset getDatasetById(final String datasetId) {
		switch (datasetId) {
		case "twitter" : return Dataset.TWITTER;
		case "mtsamples" : return Dataset.MT_SAMPLES;
		default : throw new AssertionError("Invalid datasetId: " + datasetId);
		}
	}
	
	private List<com.furuta.bean.Concept> convert(final List<Concept> concepts) {
		return concepts.stream()
					   .map((concept) -> new com.furuta.bean.Concept(concept.getName(), concept.getPrevalence()))
					   .collect(Collectors.<com.furuta.bean.Concept>toList());
	}
	
	private Response saveJob(final String[] seeds, final Job job, final List<com.furuta.bean.Concept> concepts) {
		
		final String jobId = job.getId();
		final String timestamp = getTimestamp();
		
		final ConceptExpansionJob conceptExpansionJob = new ConceptExpansionJob();
		conceptExpansionJob.setJobId(jobId);
		conceptExpansionJob.setSeeds(seeds);
		conceptExpansionJob.setConcepts(concepts);
		conceptExpansionJob.setTimestamp(timestamp);
		
		return repository.save(conceptExpansionJob);
	}
	
	private String getTimestamp() {
		final Date date = new Date();
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return format.format(date);
	}
}
