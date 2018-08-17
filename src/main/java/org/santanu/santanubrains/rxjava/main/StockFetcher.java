package org.santanu.santanubrains.rxjava.main;

import java.io.IOException;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class StockFetcher {

	public static Stock fetch(String ticker) {

		try {
			return YahooFinance.get(ticker);
		} catch (IOException e) {
			e.printStackTrace();

		}
		return null;

	}
}
