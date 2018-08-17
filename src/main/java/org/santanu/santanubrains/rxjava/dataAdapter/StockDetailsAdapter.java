package org.santanu.santanubrains.rxjava.dataAdapter;

import io.reactivex.Single;
import yahoofinance.Stock;

public interface StockDetailsAdapter {
	Single<Stock> getStockDetailsByTicker(String ticker);

	Single<Stock> getStockDetailsWithIncludeHistoricalDataByTicker(String ticker, boolean includeHistorical);

	Single<Stock> getStockByFromDate(String ticker, String fromDate);

	Single<Stock> getStockByInterval(String ticker, String interval);

	Single<Stock> getStockByFromToDate(String ticker, String fromDate, String toDate);

	Single<Stock> getStockByFromInterval(String ticker, String fromDate, String interval);

	Single<Stock> getStockByFromToDateInterval(String ticker, String fromDate, String toDate, String interval);
}
