package com.furuta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.furuta.bean.Concept;
import com.furuta.bean.ConceptExpansionJob;
import com.furuta.service.ConceptExpansionService;
import com.google.gson.Gson;

@RestController
public class ConceptExpansionController {
	
	@Autowired
	private ConceptExpansionService service;

    @RequestMapping(value = "evaluate", method = RequestMethod.POST)
    @ResponseBody
    String evaluate(@RequestBody String seeds) {
    	final String[] seedsArray = seeds.split("\n");
		final List<Concept> result = service.evaluate(seedsArray);
		return new Gson().toJson(result);
    }
    
    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    String listAll() {
    	final List<ConceptExpansionJob> result = service.listAll();
    	return new Gson().toJson(result);
    }
}
