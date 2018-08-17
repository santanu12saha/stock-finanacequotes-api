package org.santanu.santanubrains.rxjava.service;

import java.util.List;

import io.reactivex.Single;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

public interface StockQuoteService {

	Single<StockQuote> getStockQuoteBySymbol(String symbol);

	Single<List<HistoricalQuote>> getStockHistoricalQuoteBySymbol(String symbol);

	Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromDate(String symbol, String fromDate);

	Single<List<HistoricalQuote>> getStockHistoricalQuoteByInterval(String symbol, String interval);

	Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromToDate(String symbol, String fromDate, String toDate);

	Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromInterval(String symbol, String fromDate,
			String interval);

	Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromToInterval(String symbol, String fromDate, String toDate,
			String interval);
}
