package com.furuta.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.FindByIndexOptions;
import com.cloudant.client.api.model.IndexField;
import com.cloudant.client.api.model.IndexField.SortOrder;
import com.cloudant.client.api.model.Response;
import com.furuta.bean.ConceptExpansionJob;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *  Repository for {@link ConceptExpansionJob}. 
 */
@Repository
public class ConceptExpansionJobRepository {

	@Autowired
	private CloudantClientManager manager;

	public Response save(final ConceptExpansionJob job) {
		try {
			final Database database = getDatabase();
			return database.save(job);
			
		} catch (final Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error saving job.", e);
		}
	}
	
	public List<ConceptExpansionJob> listAllSortByTimestampDesc() {

		try {
			final Database database = getDatabase();
			final String selectorJson = "\"selector\": { \"timestamp\": {\"$gt\": 0}}";
			final FindByIndexOptions options = 
					new FindByIndexOptions().sort(new IndexField("timestamp", SortOrder.desc))
											.fields("_id")
											.fields("_rev")
											.fields("seeds")
											.fields("timestamp")
											.fields("jobId")
											.fields("concepts");
			
			return database.findByIndex(selectorJson,
					 					ConceptExpansionJob.class,
				 					 	options);
		} catch (final Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error loading jobs.", e);
		}
	}

	private Database getDatabase() {
		final CloudantClient client = manager.getClient();
		return client.database("concept-expansion", false);
	}
}
