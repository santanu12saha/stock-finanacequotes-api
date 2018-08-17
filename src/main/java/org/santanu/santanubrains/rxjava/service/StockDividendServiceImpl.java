package org.santanu.santanubrains.rxjava.service;

import java.util.List;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.dataAdapter.StockDividendAdapter;

import io.reactivex.Single;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockDividend;

public class StockDividendServiceImpl implements StockDividendService {

	private StockDividendAdapter stockDividendAdapter;

	@Inject
	public StockDividendServiceImpl(StockDividendAdapter stockDividendAdapter) {
		super();
		this.stockDividendAdapter = stockDividendAdapter;
	}

	@Override
	public Single<StockDividend> getStockDividendBySymbol(String symbol) {

		return stockDividendAdapter.getStockDividendByTicker(symbol);
	}

	@Override
	public Single<List<HistoricalDividend>> getStockDividendHistoryBySymbol(String symbol) {

		return stockDividendAdapter.getStockDividendHistoryByTicker(symbol);
	}

	@Override
	public Single<List<HistoricalDividend>> getStockDivHisByFromDate(String symbol, String fromDate) {
		
		return stockDividendAdapter.getStockHisDivByFromDate(symbol, fromDate);
	}

	@Override
	public Single<List<HistoricalDividend>> getStockDivHisByFromToDate(String symbol, String fromDate, String toDate) {
		
		return stockDividendAdapter.getStockHisDivByFromToDate(symbol, fromDate, toDate);
	}

}
