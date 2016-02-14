package com.furuta.repository;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class CloudantClientManager {
	
	private CloudantClient client;
	
	@PostConstruct
	private void init() {
		this.client = getCloudantClient();
	}

	public CloudantClient getClient() {
		return client;
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
