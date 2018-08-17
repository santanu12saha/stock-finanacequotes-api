package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;

import io.reactivex.Single;
import yahoofinance.quotes.stock.StockStats;

public interface MultipleStockStatsAdapter {

	Single<Map<String, StockStats>> getMultipleStockStatisticByTicker(MultipleStockQuery multipleStockQuery);
}
