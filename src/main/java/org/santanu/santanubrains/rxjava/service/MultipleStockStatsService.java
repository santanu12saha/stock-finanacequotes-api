package org.santanu.santanubrains.rxjava.service;

import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;

import io.reactivex.Single;
import yahoofinance.quotes.stock.StockStats;

public interface MultipleStockStatsService {

	Single<Map<String, StockStats>> getMultipleStockStatisticBySymbol(MultipleStockQuery multipleStockQuery);
}
