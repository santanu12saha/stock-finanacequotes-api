package org.santanu.santanubrains.rxjava.service;

import io.reactivex.Single;
import yahoofinance.Stock;

public interface StockDetailService {

	Single<Stock> getStockDetailBySymbols(String symbol);

	Single<Stock> getStockDetailsWithIncludeHistoricalDataBySymbol(String symbol, boolean includeHistorical);
	
	Single<Stock> getStockByFromDate(String symbol, String fromDate);

	Single<Stock> getStockByInterval(String symbol, String interval);

	Single<Stock> getStockByFromToDate(String symbol, String fromDate, String toDate);

	Single<Stock> getStockByFromInterval(String symbol, String fromDate, String interval);

	Single<Stock> getStockByFromToDateInterval(String symbol, String fromDate, String toDate, String interval);
}
