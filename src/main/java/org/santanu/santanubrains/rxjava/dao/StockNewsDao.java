package org.santanu.santanubrains.rxjava.dao;

import java.util.HashMap;

import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.news.News;
import org.santanu.santanubrains.rxjava.httpClient.StockNewsHttpClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StockNewsDao {

	public static Map<String, News[]> newsRead(String ticker) throws Exception {

		Map<String, News[]> mapNewsResponse = null;
		JsonNode rootNode = new ObjectMapper().readTree(StockNewsHttpClient.getRemoteNews(ticker)).get("rss");
		JsonNode channelNode = rootNode.path("channel");
		JsonNode itemNodeArr = channelNode.path("item");
		if (!itemNodeArr.isMissingNode()) {
			mapNewsResponse = new HashMap<>();
			News[] newsJsonNode =  new ObjectMapper().treeToValue(itemNodeArr, News[].class);
			mapNewsResponse.put(ticker, newsJsonNode);
		} else {
			throw new RuntimeException("No Stock News found for search query: " + ticker);
		}
		return mapNewsResponse;

	}
}
