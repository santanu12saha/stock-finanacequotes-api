package org.santanu.santanubrains.rxjava.service;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.dataAdapter.StockStatsAdapter;

import io.reactivex.Single;
import yahoofinance.quotes.stock.StockStats;

public class StockStatsServiceImpl implements StockStatsService{

	private StockStatsAdapter stockStatsAdapter;
	
	@Inject
	public StockStatsServiceImpl(StockStatsAdapter stockStatsAdapter) {
		super();
		this.stockStatsAdapter = stockStatsAdapter;
	}

	@Override
	public Single<StockStats> getStockStatisticBySymbol(String symbol) {
		
		return stockStatsAdapter.getStockStatisticByTicker(symbol);
	}

}
