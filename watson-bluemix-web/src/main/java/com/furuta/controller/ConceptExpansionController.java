package com.furuta.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.furuta.repository.ConceptExpansionRepository;
import com.furuta.service.ConceptExpansionService;
import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.concept_expansion.v1.model.Concept;

@RestController
public class ConceptExpansionController {
	
	@Autowired
	private ConceptExpansionService service;
	
	@Autowired
	private ConceptExpansionRepository repository;
	
    @RequestMapping(value = "evaluate", method = RequestMethod.POST)
    @ResponseBody
    String evaluate(@RequestBody String seeds) {
    	final String jobId = service.createJob(seeds.split("\n"));
    	final List<Concept> concepts = service.getJobResult(jobId);
    	final String result = new Gson().toJson(concepts);
    	
    	final Map<String, Object> request = new HashMap<String, Object>();
    	request.put("id", jobId);
    	request.put("seeds", seeds.split("\n"));
    	request.put("result", result);
    	request.put("timestamp", new Date());
    	
    	repository.save(request);
    	
		return result;
    }
}
