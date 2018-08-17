package org.santanu.santanubrains.rxjava.service;

import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;
import io.reactivex.Single;
import yahoofinance.Stock;

public interface MultipleStockDetailService {

	Single<Map<String, Stock>> getMultipleStockDetailBySymbols(MultipleStockQuery multipleStockQuery);

	Single<Map<String, Stock>> getMultipleStockDetailsWithIncludeHistoricalDataBySymbol(
			MultipleStockQuery multipleStockQuery, boolean includeHistorical);
	
	Single<Map<String, Stock>> getMultipleStockByFromDate(MultipleStockQuery multipleStockQuery, String fromDate);

	Single<Map<String, Stock>> getMultipleStockByInterval(MultipleStockQuery multipleStockQuery, String interval);

	Single<Map<String, Stock>> getMultipleStockByFromToDate(MultipleStockQuery multipleStockQuery, String fromDate, String toDate);

	Single<Map<String, Stock>> getMultipleStockByFromInterval(MultipleStockQuery multipleStockQuery, String fromDate, String interval);

	Single<Map<String, Stock>> getMultipleStockByFromToDateInterval(MultipleStockQuery multipleStockQuery, String fromDate, String toDate, String interval);
}
