package org.santanu.santanubrains.rxjava.service;

import java.util.List;

import io.reactivex.Single;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockDividend;

public interface StockDividendService {

	Single<StockDividend> getStockDividendBySymbol(String symbol);
	Single<List<HistoricalDividend>> getStockDividendHistoryBySymbol(String symbol);
	Single<List<HistoricalDividend>> getStockDivHisByFromDate(String symbol, String fromDate);
	Single<List<HistoricalDividend>> getStockDivHisByFromToDate(String symbol, String fromDate, String toDate);
}
