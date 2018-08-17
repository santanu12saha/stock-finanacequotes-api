package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.List;
import java.util.Map;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuotesQuery;

import io.reactivex.Single;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

public interface MultipleStockQuotesAdapter {

	Single<Map<String, StockQuote>> getMultipleStockQuoteByTicker(MultipleStockQuotesQuery multipleStockQuotesQuery);

	Single<Map<String, List<HistoricalQuote>>> getMultipleStockHistoricalQuoteByTicker(
			MultipleStockQuotesQuery multipleStockQuotesQuery);

	Single<Map<String, List<HistoricalQuote>>> getMultipleStockHistoricalQuoteByFromDate(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate);

	Single<Map<String, List<HistoricalQuote>>> getMultipleStockHistoricalQuoteByInterval(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String interval);

	Single<Map<String, List<HistoricalQuote>>> getMultipleStockHistoricalQuoteByFromToDate(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate, String toDate);

	Single<Map<String, List<HistoricalQuote>>> getMultipleStockHistoricalQuoteByFromInterval(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate, String interval);

	Single<Map<String, List<HistoricalQuote>>> getMultipleStockHistoricalQuoteByFromToInterval(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate, String toDate, String interval);
}
