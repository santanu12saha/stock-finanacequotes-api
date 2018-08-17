package org.santanu.santanubrains.rxjava.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class MultipleStockQuotePriceDao {


	public static Map<String, BigDecimal> fetch(MultipleStockQuery multipleStockQuery) {

		try {
			Map<String, BigDecimal> mapStockQuotePrice = new HashMap<>();
			Map<String, Stock> stocks = YahooFinance.get(multipleStockQuery.getStockSymbols()
					.toArray(new String[multipleStockQuery.getStockSymbols().size()]));
			for(String ticker : multipleStockQuery.getStockSymbols()) {
				BigDecimal stockQuotePrice = stocks.get(ticker).getQuote().getPrice();
				mapStockQuotePrice.put(ticker.toUpperCase(), stockQuotePrice);
			}
			return mapStockQuotePrice;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
}
