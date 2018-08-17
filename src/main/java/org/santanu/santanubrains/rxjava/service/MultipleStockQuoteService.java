package org.santanu.santanubrains.rxjava.service;

import java.util.List;
import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuotesQuery;

import io.reactivex.Single;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

public interface MultipleStockQuoteService {
	
	Single<Map<String, StockQuote>> getMultipleStockQuoteBySymbol(MultipleStockQuotesQuery multipleStockQuotesQuery);
	
	Single<Map<String, List<HistoricalQuote>>> getStockHistoricalQuoteBySymbol(MultipleStockQuotesQuery multipleStockQuotesQuery);

	Single<Map<String, List<HistoricalQuote>>> getStockHistoricalQuoteByFromDate(MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate);

	Single<Map<String, List<HistoricalQuote>>> getStockHistoricalQuoteByInterval(MultipleStockQuotesQuery multipleStockQuotesQuery, String interval);

	Single<Map<String, List<HistoricalQuote>>> getStockHistoricalQuoteByFromToDate(MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate, String toDate);

	Single<Map<String, List<HistoricalQuote>>> getStockHistoricalQuoteByFromInterval(MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate,
			String interval);

	Single<Map<String, List<HistoricalQuote>>> getStockHistoricalQuoteByFromToInterval(MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate, String toDate,
			String interval);
}
