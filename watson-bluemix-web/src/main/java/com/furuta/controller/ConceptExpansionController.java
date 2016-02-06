package com.furuta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
	
    @RequestMapping(value = "evaluate", method = RequestMethod.POST)
    @ResponseBody
    String evaluate(@RequestBody String seeds) {
    	final String jobId = service.createJob(seeds.split("\n"));
    	final List<Concept> concepts = service.getJobResult(jobId);
    	return new Gson().toJson(concepts);
    }
}
