package org.santanu.santanubrains.rxjava.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockStats;

public class MultipleStockStatsDao {

	public static Map<String, StockStats> fetchMultipleStockStatistics(MultipleStockQuery multipleStockQuery) {

		try {
			Map<String, StockStats> mapStockStatistics = new HashMap<>();
			Map<String, Stock> stocks = YahooFinance.get(multipleStockQuery.getStockSymbols()
					.toArray(new String[multipleStockQuery.getStockSymbols().size()]));
			for (String ticker : multipleStockQuery.getStockSymbols()) {
				StockStats stockStatistic = stocks.get(ticker.toUpperCase()).getStats();
				mapStockStatistics.put(ticker.toUpperCase(), stockStatistic);
			}
			return mapStockStatistics;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
}
