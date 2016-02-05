package com.furuta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.furuta.service.ConceptExpansionService;
import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.concept_expansion.v1.model.Concept;

@RestController
public class ConceptExpansionController {
	
	@Autowired
	private ConceptExpansionService service;
	
    @RequestMapping(value = "createJob/{seed}", method = RequestMethod.GET)
    @ResponseBody
    String createJob(@PathVariable String seed) {
    	return service.createJob(new String[] {seed});
    }
    
    @RequestMapping(value = "jobResult/{jobId}", method = RequestMethod.GET)
    @ResponseBody
    String jobResult(@PathVariable String jobId) {
    	final List<Concept> concepts = service.getJobResult(jobId);
    	return new Gson().toJson(concepts);
    }
}
