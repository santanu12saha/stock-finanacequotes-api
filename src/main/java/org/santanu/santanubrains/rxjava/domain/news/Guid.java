package org.santanu.santanubrains.rxjava.domain.news;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Guid {

	@JsonProperty("isPermaLink")
	private Boolean isPermaLink;
	@JsonProperty("content")
	private String content;

	@JsonProperty("isPermaLink")
	public Boolean getIsPermaLink() {
		return isPermaLink;
	}

	@JsonProperty("isPermaLink")
	public void setIsPermaLink(Boolean isPermaLink) {
		this.isPermaLink = isPermaLink;
	}

	@JsonProperty("content")
	public String getContent() {
		return content;
	}

	@JsonProperty("content")
	public void setContent(String content) {
		this.content = content;
	}
}
