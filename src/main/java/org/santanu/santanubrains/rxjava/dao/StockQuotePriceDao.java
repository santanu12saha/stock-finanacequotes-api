package org.santanu.santanubrains.rxjava.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import yahoofinance.YahooFinance;

public class StockQuotePriceDao {

	public static Map<String, BigDecimal> fetch(String ticker){
		
		try {
			Map<String, BigDecimal> mapPrice = new HashMap<>();
			BigDecimal price = YahooFinance.get(ticker).getQuote().getPrice();
			mapPrice.put(ticker, price);
			return mapPrice;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
