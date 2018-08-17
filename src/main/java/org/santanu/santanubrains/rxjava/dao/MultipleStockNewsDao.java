package org.santanu.santanubrains.rxjava.dao;

import java.util.HashMap;
import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;
import org.santanu.santanubrains.rxjava.domain.news.News;
import org.santanu.santanubrains.rxjava.httpClient.StockNewsHttpClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MultipleStockNewsDao {

	public static Map<String, News[]> multipleNewsRead(MultipleStockQuery multipleStockQuery) throws Exception {

		Map<String, News[]> mapMultipleNewsResponse = new HashMap<>();
		for (String ticker : multipleStockQuery.getStockSymbols()) {
			JsonNode rootNode = new ObjectMapper().readTree(StockNewsHttpClient.getRemoteNews(ticker.toUpperCase()))
					.get("rss");
			JsonNode channelNode = rootNode.path("channel");
			JsonNode itemNodeArr = channelNode.path("item");
			if (!itemNodeArr.isMissingNode()) {
				News[] newsJsonNode = new ObjectMapper().treeToValue(itemNodeArr, News[].class);
				mapMultipleNewsResponse.put(ticker.toUpperCase(), newsJsonNode);
			} else {
				if(mapMultipleNewsResponse == null) {
					throw new RuntimeException("No Stock News found for search query");
				}
				
			}
		}
		return mapMultipleNewsResponse;

	}
}
