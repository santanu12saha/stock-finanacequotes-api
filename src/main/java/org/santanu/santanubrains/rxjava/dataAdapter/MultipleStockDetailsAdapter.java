package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;

import io.reactivex.Single;
import yahoofinance.Stock;

public interface MultipleStockDetailsAdapter {

	Single<Map<String, Stock>> getMultipleStockDetailsByTickers(MultipleStockQuery multipleStockQuery);

	Single<Map<String, Stock>> getMultipleStockDetailsWithIncludeHistoricalDataByTicker(
			MultipleStockQuery multipleStockQuery, boolean includeHistorical);

	Single<Map<String, Stock>> getMultipleStockByFromDate(MultipleStockQuery multipleStockQuery, String fromDate);

	Single<Map<String, Stock>> getMultipleStockByInterval(MultipleStockQuery multipleStockQuery, String interval);

	Single<Map<String, Stock>> getMultipleStockByFromToDate(MultipleStockQuery multipleStockQuery, String fromDate,
			String toDate);

	Single<Map<String, Stock>> getMultipleStockByFromInterval(MultipleStockQuery multipleStockQuery, String fromDate,
			String interval);

	Single<Map<String, Stock>> getMultipleStockByFromToDateInterval(MultipleStockQuery multipleStockQuery,
			String fromDate, String toDate, String interval);
}
