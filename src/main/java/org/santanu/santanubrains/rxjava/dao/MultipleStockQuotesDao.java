package org.santanu.santanubrains.rxjava.dao;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuotesQuery;
import org.santanu.santanubrains.rxjava.utility.DateComparison;
import org.santanu.santanubrains.rxjava.utility.SpecifiedInterval;
import org.santanu.santanubrains.rxjava.utility.StringToCalender;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

public class MultipleStockQuotesDao {

	public static Map<String, StockQuote> fetch(MultipleStockQuotesQuery multipleStockQuotesQuery) {
		try {
			Map<String, StockQuote> mapStockQuotes = new HashMap<>();
			Map<String, Stock> stocks = YahooFinance.get(multipleStockQuotesQuery.getStockQuoteSymbols()
					.toArray(new String[multipleStockQuotesQuery.getStockQuoteSymbols().size()]));
			for (String ticker : multipleStockQuotesQuery.getStockQuoteSymbols()) {
				StockQuote stockquote = stocks.get(ticker.toUpperCase()).getQuote();
				mapStockQuotes.put(ticker.toUpperCase(), stockquote);
			}
			return mapStockQuotes;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Map<String, List<HistoricalQuote>> fetchMultipleHistoricalQuote(
			MultipleStockQuotesQuery multipleStockQuotesQuery) {

		try {
			Map<String, List<HistoricalQuote>> mapHistoricalQuotes = new HashMap<>();
			Map<String, Stock> stocks = YahooFinance.get(multipleStockQuotesQuery.getStockQuoteSymbols()
					.toArray(new String[multipleStockQuotesQuery.getStockQuoteSymbols().size()]));
			for (String ticker : multipleStockQuotesQuery.getStockQuoteSymbols()) {
				List<HistoricalQuote> stockHistoricalQuote = stocks.get(ticker.toUpperCase()).getHistory();
				mapHistoricalQuotes.put(ticker.toUpperCase(), stockHistoricalQuote);
			}
			return mapHistoricalQuotes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Map<String, List<HistoricalQuote>> fetchMultipleHistoricalQuoteFromDate(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate) {

		try {
			if (DateComparison.fromLesserCurrent(fromDate)) {
				Map<String, List<HistoricalQuote>> mapHistoricalQuotesFromDate = new HashMap<>();
				Map<String, Stock> stocks = YahooFinance.get(multipleStockQuotesQuery.getStockQuoteSymbols()
						.toArray(new String[multipleStockQuotesQuery.getStockQuoteSymbols().size()]));
				for (String ticker : multipleStockQuotesQuery.getStockQuoteSymbols()) {
					List<HistoricalQuote> stockHistoricalQuoteFromDate = stocks.get(ticker.toUpperCase())
							.getHistory(StringToCalender.convert(fromDate));
					mapHistoricalQuotesFromDate.put(ticker.toUpperCase(), stockHistoricalQuoteFromDate);
				}
				return mapHistoricalQuotesFromDate;
			} else {
				throw new RuntimeException(
						"From Date: " + fromDate + " should be less than Current Date : " + new Date());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static Map<String, List<HistoricalQuote>> fetchMultipleHistoricalQuoteInterval(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String interval) {

		try {
			if (SpecifiedInterval.interval(interval) != null) {
				Map<String, List<HistoricalQuote>> mapHistoricalQuotesInterval = new HashMap<>();
				Map<String, Stock> stocks = YahooFinance.get(multipleStockQuotesQuery.getStockQuoteSymbols()
						.toArray(new String[multipleStockQuotesQuery.getStockQuoteSymbols().size()]));
				for (String ticker : multipleStockQuotesQuery.getStockQuoteSymbols()) {
					List<HistoricalQuote> stockHistoricalQuoteInterval = stocks.get(ticker.toUpperCase())
							.getHistory(SpecifiedInterval.interval(interval));
					mapHistoricalQuotesInterval.put(ticker.toUpperCase(), stockHistoricalQuoteInterval);
				}
				return mapHistoricalQuotesInterval;
			} else {
				throw new RuntimeException("InValid Interval. Options are 'Weekly','Daily','Monthly'");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Map<String, List<HistoricalQuote>> fetchMultipleHistoricalQuoteFromToDate(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate, String toDate) {

		try {
			if (DateComparison.fromDateLesserToDate(fromDate, toDate)) {

				if (DateComparison.fromLesserCurrent(fromDate)) {

					if (DateComparison.toDateLesserCurrent(toDate)) {

						Map<String, List<HistoricalQuote>> mapHistoricalQuotesFromToDate = new HashMap<>();
						Map<String, Stock> stocks = YahooFinance.get(multipleStockQuotesQuery.getStockQuoteSymbols()
								.toArray(new String[multipleStockQuotesQuery.getStockQuoteSymbols().size()]));
						for (String ticker : multipleStockQuotesQuery.getStockQuoteSymbols()) {
							List<HistoricalQuote> stockHistoricalQuoteFromToDate = stocks.get(ticker.toUpperCase())
									.getHistory(StringToCalender.convert(fromDate), StringToCalender.convert(toDate));
							mapHistoricalQuotesFromToDate.put(ticker.toUpperCase(), stockHistoricalQuoteFromToDate);
						}
						return mapHistoricalQuotesFromToDate;
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

	public static Map<String, List<HistoricalQuote>> fetchMultipleHistoricalQuoteFromInterval(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate, String interval) {

		try {
			if (DateComparison.fromLesserCurrent(fromDate)) {

				if (SpecifiedInterval.interval(interval) != null) {

					Map<String, List<HistoricalQuote>> mapHistoricalQuotesFromInterval = new HashMap<>();
					Map<String, Stock> stocks = YahooFinance.get(multipleStockQuotesQuery.getStockQuoteSymbols()
							.toArray(new String[multipleStockQuotesQuery.getStockQuoteSymbols().size()]));
					for (String ticker : multipleStockQuotesQuery.getStockQuoteSymbols()) {
						List<HistoricalQuote> stockHistoricalQuoteFromInterval = stocks.get(ticker.toUpperCase())
								.getHistory(StringToCalender.convert(fromDate), SpecifiedInterval.interval(interval));
						mapHistoricalQuotesFromInterval.put(ticker.toUpperCase(), stockHistoricalQuoteFromInterval);
					}
					return mapHistoricalQuotesFromInterval;
				} else {
					throw new RuntimeException("InValid Interval. Options are 'Weekly','Daily','Monthly'");
				}
			} else {
				throw new RuntimeException(
						"From Date: " + fromDate + " should be less than Current Date : " + new Date());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static Map<String, List<HistoricalQuote>> fetchMultipleHistoricalQuoteFromToInterval(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate, String toDate, String interval) {

		try {

			if (DateComparison.fromDateLesserToDate(fromDate, toDate)) {
				if (DateComparison.fromLesserCurrent(fromDate)) {
					if (DateComparison.toDateLesserCurrent(toDate)) {
						if (SpecifiedInterval.interval(interval) != null) {

							Map<String, List<HistoricalQuote>> mapHistoricalQuotesFromToInterval = new HashMap<>();
							Map<String, Stock> stocks = YahooFinance.get(multipleStockQuotesQuery.getStockQuoteSymbols()
									.toArray(new String[multipleStockQuotesQuery.getStockQuoteSymbols().size()]));
							for (String ticker : multipleStockQuotesQuery.getStockQuoteSymbols()) {
								List<HistoricalQuote> stockHistoricalQuoteFromToInterval = stocks
										.get(ticker.toUpperCase()).getHistory(StringToCalender.convert(fromDate),
												StringToCalender.convert(toDate), SpecifiedInterval.interval(interval));
								mapHistoricalQuotesFromToInterval.put(ticker.toUpperCase(),
										stockHistoricalQuoteFromToInterval);

							}
							return mapHistoricalQuotesFromToInterval;
						} else {
							throw new RuntimeException("InValid Interval. Options are 'Weekly','Daily','Monthly'");
						}

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
