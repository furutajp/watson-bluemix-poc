package com.furuta.bean;

import java.io.Serializable;

public class ConceptExpansionJob implements Serializable {

	private static final long serialVersionUID = 3428247774423611042L;
	
	private String _id;
	private String _rev;
	private String jobId;
	private String jobResult;
	private String[] seeds;
	private String timestamp;
	
	public String get_id() {
		return _id;
	}
	
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public String get_rev() {
		return _rev;
	}
	
	public void set_rev(String _rev) {
		this._rev = _rev;
	}
	
	public String getJobId() {
		return jobId;
	}
	
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getJobResult() {
		return jobResult;
	}
	
	public void setJobResult(String jobResult) {
		this.jobResult = jobResult;
	}
	
	public String[] getSeeds() {
		return seeds;
	}
	
	public void setSeeds(String[] seeds) {
		this.seeds = seeds;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
