package org.santanu.santanubrains.rxjava.service;

import java.util.List;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.dataAdapter.StockQuotesAdapter;

import io.reactivex.Single;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

public class StockQuoteServiceImpl implements StockQuoteService {

	private StockQuotesAdapter stockQuotesAdapter;

	@Inject
	public StockQuoteServiceImpl(StockQuotesAdapter stockQuotesAdapter) {
		super();
		this.stockQuotesAdapter = stockQuotesAdapter;
	}

	@Override
	public Single<StockQuote> getStockQuoteBySymbol(String symbol) {

		return stockQuotesAdapter.getStockQuoteByTicker(symbol);
	}

	@Override
	public Single<List<HistoricalQuote>> getStockHistoricalQuoteBySymbol(String symbol) {

		return stockQuotesAdapter.getStockHistoricalQuoteByTicker(symbol);
	}

	@Override
	public Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromDate(String symbol, String fromDate) {

		return stockQuotesAdapter.getStockHistoricalQuoteByFromDate(symbol, fromDate);
	}

	@Override
	public Single<List<HistoricalQuote>> getStockHistoricalQuoteByInterval(String symbol, String interval) {

		return stockQuotesAdapter.getStockHistoricalQuoteByInterval(symbol, interval);
	}

	@Override
	public Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromToDate(String symbol, String fromDate,
			String toDate) {

		return stockQuotesAdapter.getStockHistoricalQuoteByFromToDate(symbol, fromDate, toDate);
	}

	@Override
	public Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromInterval(String symbol, String fromDate,
			String interval) {

		return stockQuotesAdapter.getStockHistoricalQuoteByFromInterval(symbol, fromDate, interval);
	}

	@Override
	public Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromToInterval(String symbol, String fromDate,
			String toDate, String interval) {

		return stockQuotesAdapter.getStockHistoricalQuoteByFromToInterval(symbol, fromDate, toDate, interval);
	}

}
