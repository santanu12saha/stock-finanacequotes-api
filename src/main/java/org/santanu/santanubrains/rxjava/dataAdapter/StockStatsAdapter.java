package org.santanu.santanubrains.rxjava.dataAdapter;

import io.reactivex.Single;
import yahoofinance.quotes.stock.StockStats;

public interface StockStatsAdapter {
	
	Single<StockStats> getStockStatisticByTicker(String ticker);
}
