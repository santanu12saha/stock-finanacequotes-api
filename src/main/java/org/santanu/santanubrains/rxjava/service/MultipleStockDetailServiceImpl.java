package org.santanu.santanubrains.rxjava.service;

import java.util.Map;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockDetailsAdapter;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;

import io.reactivex.Single;
import yahoofinance.Stock;

public class MultipleStockDetailServiceImpl implements MultipleStockDetailService {

	private MultipleStockDetailsAdapter multipleStockDetailsAdapter;

	@Inject
	public MultipleStockDetailServiceImpl(MultipleStockDetailsAdapter multipleStockDetailsAdapter) {
		super();
		this.multipleStockDetailsAdapter = multipleStockDetailsAdapter;
	}

	@Override
	public Single<Map<String, Stock>> getMultipleStockDetailBySymbols(MultipleStockQuery multipleStockQuery) {

		return multipleStockDetailsAdapter.getMultipleStockDetailsByTickers(multipleStockQuery);
	}

	@Override
	public Single<Map<String, Stock>> getMultipleStockDetailsWithIncludeHistoricalDataBySymbol(
			MultipleStockQuery multipleStockQuery, boolean includeHistorical) {
		
		return multipleStockDetailsAdapter.getMultipleStockDetailsWithIncludeHistoricalDataByTicker(multipleStockQuery, includeHistorical);
	}

	@Override
	public Single<Map<String, Stock>> getMultipleStockByFromDate(MultipleStockQuery multipleStockQuery,
			String fromDate) {
		
		return multipleStockDetailsAdapter.getMultipleStockByFromDate(multipleStockQuery, fromDate);
	}

	@Override
	public Single<Map<String, Stock>> getMultipleStockByInterval(MultipleStockQuery multipleStockQuery,
			String interval) {
		
		return multipleStockDetailsAdapter.getMultipleStockByInterval(multipleStockQuery, interval);
	}

	@Override
	public Single<Map<String, Stock>> getMultipleStockByFromToDate(MultipleStockQuery multipleStockQuery,
			String fromDate, String toDate) {
		
		return multipleStockDetailsAdapter.getMultipleStockByFromToDate(multipleStockQuery, fromDate, toDate);
	}

	@Override
	public Single<Map<String, Stock>> getMultipleStockByFromInterval(MultipleStockQuery multipleStockQuery,
			String fromDate, String interval) {
		
		return multipleStockDetailsAdapter.getMultipleStockByFromInterval(multipleStockQuery, fromDate, interval);
	}

	@Override
	public Single<Map<String, Stock>> getMultipleStockByFromToDateInterval(MultipleStockQuery multipleStockQuery,
			String fromDate, String toDate, String interval) {
		
		return multipleStockDetailsAdapter.getMultipleStockByFromToDateInterval(multipleStockQuery, fromDate, toDate, interval);
	}

	
}
