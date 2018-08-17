package org.santanu.santanubrains.rxjava.dataAdapter;


import java.util.List;

import io.reactivex.Single;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockDividend;

public interface StockDividendAdapter {
	
	Single<StockDividend> getStockDividendByTicker(String ticker);
	Single<List<HistoricalDividend>> getStockDividendHistoryByTicker(String ticker);
	Single<List<HistoricalDividend>> getStockHisDivByFromDate(String ticker, String fromDate);
	Single<List<HistoricalDividend>> getStockHisDivByFromToDate(String ticker, String fromDate, String toDate);
}
