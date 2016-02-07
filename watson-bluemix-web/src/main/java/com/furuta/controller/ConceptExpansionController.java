package com.furuta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.furuta.service.ConceptExpansionService;

@RestController
public class ConceptExpansionController {
	
	@Autowired
	private ConceptExpansionService service;

    @RequestMapping(value = "evaluate", method = RequestMethod.POST)
    @ResponseBody
    String evaluate(@RequestBody String seeds) {
    	final String[] seedsArray = seeds.split("\n");
		return service.evaluate(seedsArray);
    }
}
