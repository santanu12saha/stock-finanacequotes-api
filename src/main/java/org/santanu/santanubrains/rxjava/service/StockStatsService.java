package org.santanu.santanubrains.rxjava.service;

import io.reactivex.Single;
import yahoofinance.quotes.stock.StockStats;

public interface StockStatsService {

	Single<StockStats> getStockStatisticBySymbol(String symbol);
}
