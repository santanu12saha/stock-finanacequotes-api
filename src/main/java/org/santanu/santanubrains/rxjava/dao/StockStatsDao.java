package org.santanu.santanubrains.rxjava.dao;

import java.io.IOException;

import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockStats;

public class StockStatsDao {

	public static StockStats fetchStockStatistics(String ticker) {
		
		try {
			return YahooFinance.get(ticker).getStats();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
