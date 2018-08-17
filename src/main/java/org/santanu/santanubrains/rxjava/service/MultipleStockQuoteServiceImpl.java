package org.santanu.santanubrains.rxjava.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockQuotesAdapter;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuotesQuery;

import io.reactivex.Single;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

public class MultipleStockQuoteServiceImpl implements MultipleStockQuoteService {

	private MultipleStockQuotesAdapter mutipleStockQuotesAdapter;

	@Inject
	public MultipleStockQuoteServiceImpl(MultipleStockQuotesAdapter mutipleStockQuotesAdapter) {
		super();
		this.mutipleStockQuotesAdapter = mutipleStockQuotesAdapter;
	}

	@Override
	public Single<Map<String, StockQuote>> getMultipleStockQuoteBySymbol(
			MultipleStockQuotesQuery multipleStockQuotesQuery) {

		return mutipleStockQuotesAdapter.getMultipleStockQuoteByTicker(multipleStockQuotesQuery);
	}

	@Override
	public Single<Map<String, List<HistoricalQuote>>> getStockHistoricalQuoteBySymbol(
			MultipleStockQuotesQuery multipleStockQuotesQuery) {

		return mutipleStockQuotesAdapter.getMultipleStockHistoricalQuoteByTicker(multipleStockQuotesQuery);
	}

	@Override
	public Single<Map<String, List<HistoricalQuote>>> getStockHistoricalQuoteByFromDate(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate) {

		return mutipleStockQuotesAdapter.getMultipleStockHistoricalQuoteByFromDate(multipleStockQuotesQuery, fromDate);
	}

	@Override
	public Single<Map<String, List<HistoricalQuote>>> getStockHistoricalQuoteByInterval(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String interval) {

		return mutipleStockQuotesAdapter.getMultipleStockHistoricalQuoteByInterval(multipleStockQuotesQuery, interval);
	}

	@Override
	public Single<Map<String, List<HistoricalQuote>>> getStockHistoricalQuoteByFromToDate(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate, String toDate) {

		return mutipleStockQuotesAdapter.getMultipleStockHistoricalQuoteByFromToDate(multipleStockQuotesQuery, fromDate,
				toDate);
	}

	@Override
	public Single<Map<String, List<HistoricalQuote>>> getStockHistoricalQuoteByFromInterval(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate, String interval) {

		return mutipleStockQuotesAdapter.getMultipleStockHistoricalQuoteByFromInterval(multipleStockQuotesQuery,
				fromDate, interval);
	}

	@Override
	public Single<Map<String, List<HistoricalQuote>>> getStockHistoricalQuoteByFromToInterval(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate, String toDate, String interval) {

		return mutipleStockQuotesAdapter.getMultipleStockHistoricalQuoteByFromToInterval(multipleStockQuotesQuery,
				fromDate, toDate, interval);
	}

}
