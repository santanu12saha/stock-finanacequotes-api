package org.santanu.santanubrains.rxjava.service;

import java.util.Map;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockStatsAdapter;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;

import io.reactivex.Single;
import yahoofinance.quotes.stock.StockStats;

public class MultipleStockStatsServiceImpl implements MultipleStockStatsService{

	
	private MultipleStockStatsAdapter multipleStockStatsAdapter;
	
	@Inject
	public MultipleStockStatsServiceImpl(MultipleStockStatsAdapter multipleStockStatsAdapter) {
		super();
		this.multipleStockStatsAdapter = multipleStockStatsAdapter;
	}

	@Override
	public Single<Map<String, StockStats>> getMultipleStockStatisticBySymbol(MultipleStockQuery multipleStockQuery) {
		
		return multipleStockStatsAdapter.getMultipleStockStatisticByTicker(multipleStockQuery);
	}

}
