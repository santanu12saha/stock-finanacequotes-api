package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.List;

import io.reactivex.Single;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

public interface StockQuotesAdapter {
	Single<StockQuote> getStockQuoteByTicker(String ticker);

	Single<List<HistoricalQuote>> getStockHistoricalQuoteByTicker(String ticker);

	Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromDate(String ticker, String fromDate);

	Single<List<HistoricalQuote>> getStockHistoricalQuoteByInterval(String ticker, String interval);

	Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromToDate(String ticker, String fromDate, String toDate);

	Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromInterval(String ticker, String fromDate,
			String interval);

	Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromToInterval(String ticker, String fromDate, String toDate,
			String interval);
}
