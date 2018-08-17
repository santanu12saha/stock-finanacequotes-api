package org.santanu.santanubrains.rxjava.domain.news;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "link", "description", "guid", "title", "pubDate" })
public class News {

	@JsonProperty("link")
	private String link;
	@JsonProperty("description")
	private String description;
	@JsonProperty("guid")
	private Guid guid;
	@JsonProperty("title")
	private String title;
	@JsonProperty("pubDate")
	private String pubDate;

	@JsonProperty("link")
	public String getLink() {
		return link;
	}

	@JsonProperty("link")
	public void setLink(String link) {
		this.link = link;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("guid")
	public Guid getGuid() {
		return guid;
	}

	@JsonProperty("guid")
	public void setGuid(Guid guid) {
		this.guid = guid;
	}

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("pubDate")
	public String getPubDate() {
		return pubDate;
	}

	@JsonProperty("pubDate")
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

}
