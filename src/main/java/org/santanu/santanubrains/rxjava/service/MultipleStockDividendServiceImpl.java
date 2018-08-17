package org.santanu.santanubrains.rxjava.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockDividendAdapter;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;

import io.reactivex.Single;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockDividend;

public class MultipleStockDividendServiceImpl implements MultipleStockDividendService {

	private MultipleStockDividendAdapter multiStockDividendAdapter;

	@Inject
	public MultipleStockDividendServiceImpl(MultipleStockDividendAdapter multiStockDividendAdapter) {
		super();
		this.multiStockDividendAdapter = multiStockDividendAdapter;
	}

	@Override
	public Single<Map<String, StockDividend>> getMultipleStockDividendBySymbol(MultipleStockQuery multipleStockQuery) {

		return multiStockDividendAdapter.getMultipleStockDividendByTicker(multipleStockQuery);
	}

	@Override
	public Single<Map<String, List<HistoricalDividend>>> getMultipleStockHistoricalDividendBySymbol(
			MultipleStockQuery multipleStockQuery) {

		return multiStockDividendAdapter.getMultipleStockHistoricalDividendByTicker(multipleStockQuery);
	}

	@Override
	public Single<Map<String, List<HistoricalDividend>>> getMultipleStockHisDivByFromDate(
			MultipleStockQuery multipleStockQuery, String fromDate) {
		
		return multiStockDividendAdapter.getMultipleStockHisDivByFromDate(multipleStockQuery, fromDate);
	}

	@Override
	public Single<Map<String, List<HistoricalDividend>>> getMultipleStockHisDivByFromToDate(
			MultipleStockQuery multipleStockQuery, String fromDate, String toDate) {
		
		return multiStockDividendAdapter.getMultipleStockHisDivByFromToDate(multipleStockQuery, fromDate, toDate);
	}

}
