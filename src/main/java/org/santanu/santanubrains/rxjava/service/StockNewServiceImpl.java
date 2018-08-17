package org.santanu.santanubrains.rxjava.service;

import java.util.Map;

import javax.inject.Inject;
import org.santanu.santanubrains.rxjava.dataAdapter.StockNewsAdapter;
import org.santanu.santanubrains.rxjava.domain.news.News;

import io.reactivex.Single;

public class StockNewServiceImpl implements StockNewService {

	private StockNewsAdapter stockNewsAdapter;

	@Inject
	public StockNewServiceImpl(StockNewsAdapter stockNewsAdapter) {
		super();
		this.stockNewsAdapter = stockNewsAdapter;
	}

	@Override
	public Single<Map<String, News[]>> getStockNewsBySymbol(String symbol) {

		return stockNewsAdapter.getStockNewsByTicker(symbol);
	}

}
