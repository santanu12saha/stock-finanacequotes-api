package org.santanu.santanubrains.rxjava.dao;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;
import org.santanu.santanubrains.rxjava.utility.DateComparison;
import org.santanu.santanubrains.rxjava.utility.StringToCalender;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockDividend;

public class MultipleStockDividendDao {

	public static Map<String, StockDividend> fetch(MultipleStockQuery multipleStockQuery) {

		Map<String, StockDividend> mapStockDividend = new HashMap<>();
		try {
			Map<String, Stock> stocks = YahooFinance.get(multipleStockQuery.getStockSymbols()
					.toArray(new String[multipleStockQuery.getStockSymbols().size()]));
			for (String ticker : multipleStockQuery.getStockSymbols()) {
				StockDividend stockDividend = stocks.get(ticker.toUpperCase()).getDividend();
				mapStockDividend.put(ticker.toUpperCase(), stockDividend);
			}
			return mapStockDividend;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Map<String, List<HistoricalDividend>> fetchMultipleDividendHistory(
			MultipleStockQuery multipleStockQuery) {

		Map<String, List<HistoricalDividend>> mapStockHistoricalDividend = new HashMap<>();
		try {

			Map<String, Stock> stocks = YahooFinance.get(multipleStockQuery.getStockSymbols()
					.toArray(new String[multipleStockQuery.getStockSymbols().size()]));
			for (String ticker : multipleStockQuery.getStockSymbols()) {
				List<HistoricalDividend> stockDividendHistory = stocks.get(ticker.toUpperCase()).getDividendHistory();
				mapStockHistoricalDividend.put(ticker.toUpperCase(), stockDividendHistory);
			}
			return mapStockHistoricalDividend;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, List<HistoricalDividend>> fetchMultipleDividendHistoryFromDate(
			MultipleStockQuery multipleStockQuery, String fromDate) {

		Map<String, List<HistoricalDividend>> mapStockHistoricalDividendFromDate = new HashMap<>();

		try {
			if (DateComparison.fromLesserCurrent(fromDate)) {
				Map<String, Stock> stocks = YahooFinance.get(multipleStockQuery.getStockSymbols()
						.toArray(new String[multipleStockQuery.getStockSymbols().size()]));
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					List<HistoricalDividend> stockDividendHistoryFromDate = stocks.get(ticker.toUpperCase())
							.getDividendHistory(StringToCalender.convert(fromDate));
					mapStockHistoricalDividendFromDate.put(ticker.toUpperCase(), stockDividendHistoryFromDate);
				}
				return mapStockHistoricalDividendFromDate;

			} else {
				throw new RuntimeException(
						"From Date: " + fromDate + " should be less than Current Date : " + new Date());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Map<String, List<HistoricalDividend>> fetchMultipleDividendHistoryFromToDate(
			MultipleStockQuery multipleStockQuery, String fromDate, String toDate) {

		Map<String, List<HistoricalDividend>> mapStockHistoricalDividendFromToDate = new HashMap<>();

		try {

			if (DateComparison.fromDateLesserToDate(fromDate, toDate)) {

				if (DateComparison.fromLesserCurrent(fromDate)) {
					if (DateComparison.toDateLesserCurrent(toDate)) {

						Map<String, Stock> stocks = YahooFinance.get(multipleStockQuery.getStockSymbols()
								.toArray(new String[multipleStockQuery.getStockSymbols().size()]));
						for (String ticker : multipleStockQuery.getStockSymbols()) {
							List<HistoricalDividend> stockDividendHistoryFromToDate = stocks.get(ticker.toUpperCase())
									.getDividendHistory(StringToCalender.convert(fromDate),
											StringToCalender.convert(toDate));
							mapStockHistoricalDividendFromToDate.put(ticker.toUpperCase(),
									stockDividendHistoryFromToDate);
						}
						return mapStockHistoricalDividendFromToDate;
					} else {
						throw new RuntimeException(
								"To Date: " + toDate + " should be less than or equal to Current Date : " + new Date());
					}
				} else {
					throw new RuntimeException(
							"From Date: " + fromDate + " should be less than or equal to Current Date : " + new Date());
				}
			} else {
				throw new RuntimeException("From Date : " + fromDate + " should be less than To Date : " + toDate);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
