package com.furuta.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.FindByIndexOptions;
import com.cloudant.client.api.model.IndexField;
import com.cloudant.client.api.model.IndexField.SortOrder;
import com.furuta.bean.ConceptExpansionJob;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Repository
public class ConceptExpansionRepository {

	private Database database;
	
	@PostConstruct
	private void init() {
		final CloudantClient client = getCloudantClient();
		this.database = client.database("concept-expansion", false);
	}

	public void save(final ConceptExpansionJob job) {
		database.save(job);
	}
	
	public List<ConceptExpansionJob> listAll() {

		final String selectorJson = "\"selector\": { \"timestamp\": {\"$gt\": 0}}";
		final FindByIndexOptions options = 
				new FindByIndexOptions().sort(new IndexField("timestamp", SortOrder.desc))
										.fields("_id")
										.fields("_rev")
										.fields("seeds")
										.fields("timestamp")
										.fields("jobId")
										.fields("jobResult");
		
		return database.findByIndex(selectorJson,
				 					ConceptExpansionJob.class,
			 					 	options);
	}
	

	private CloudantClient getCloudantClient() {
		final JsonObject credentials = getCredentials();
		final String username = credentials.get("username").getAsString();
		final String password = credentials.get("password").getAsString();
		return ClientBuilder.account(username)
							.username(username)
							.password(password)
							.build();
	}
	
	private JsonObject getCredentials() {
		final String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
		final JsonObject vcapServices = (JsonObject) new JsonParser().parse(VCAP_SERVICES);
		final JsonElement cloudantElement = vcapServices.get("cloudantNoSQLDB");
		final JsonObject service = (JsonObject) ((JsonArray) cloudantElement).get(0);
		return (JsonObject) service.get("credentials");
	}
}
