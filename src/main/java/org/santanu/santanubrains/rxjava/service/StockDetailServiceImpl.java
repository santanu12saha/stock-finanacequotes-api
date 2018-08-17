package org.santanu.santanubrains.rxjava.service;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.dataAdapter.StockDetailsAdapter;
import io.reactivex.Single;
import yahoofinance.Stock;

public class StockDetailServiceImpl implements StockDetailService {

	private StockDetailsAdapter stockDetailsAdapter;

	@Inject
	public StockDetailServiceImpl(StockDetailsAdapter stockDetailsAdapter) {
		this.stockDetailsAdapter = stockDetailsAdapter;
	}

	@Override
	public Single<Stock> getStockDetailBySymbols(String symbol) {

		return stockDetailsAdapter.getStockDetailsByTicker(symbol);

	}

	@Override
	public Single<Stock> getStockDetailsWithIncludeHistoricalDataBySymbol(String symbol, boolean includeHistorical) {

		return stockDetailsAdapter.getStockDetailsWithIncludeHistoricalDataByTicker(symbol, includeHistorical);
	}

	@Override
	public Single<Stock> getStockByFromDate(String symbol, String fromDate) {

		return stockDetailsAdapter.getStockByFromDate(symbol, fromDate);
	}

	@Override
	public Single<Stock> getStockByInterval(String symbol, String interval) {

		return stockDetailsAdapter.getStockByInterval(symbol, interval);
	}

	@Override
	public Single<Stock> getStockByFromToDate(String symbol, String fromDate, String toDate) {

		return stockDetailsAdapter.getStockByFromToDate(symbol, fromDate, toDate);
	}

	@Override
	public Single<Stock> getStockByFromInterval(String symbol, String fromDate, String interval) {

		return stockDetailsAdapter.getStockByFromInterval(symbol, fromDate, interval);
	}

	@Override
	public Single<Stock> getStockByFromToDateInterval(String symbol, String fromDate, String toDate, String interval) {

		return stockDetailsAdapter.getStockByFromToDateInterval(symbol, fromDate, toDate, interval);
	}

}
