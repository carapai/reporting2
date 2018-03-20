package org.openmrs.module.reporting2.web.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultFilter {
	
	private String condition;
	
	private String joiner;
	
	@JsonProperty("condition")
	public String getCondition() {
		return condition;
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	@JsonProperty("joiner")
	public String getJoiner() {
		return joiner;
	}
	
	public void setJoiner(String joiner) {
		this.joiner = joiner;
	}
}
