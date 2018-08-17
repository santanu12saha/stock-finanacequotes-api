package org.santanu.santanubrains.rxjava.service;

import javax.inject.Inject;
import org.santanu.santanubrains.rxjava.dataAdapter.StockSearchAdapter;
import io.reactivex.Single;

public class StockSearchServiceImpl implements StockSearchService {

	private StockSearchAdapter stockSearchAdapter;

	@Inject
	public StockSearchServiceImpl(StockSearchAdapter stockSearchAdapter) {
		super();
		this.stockSearchAdapter = stockSearchAdapter;
	}

	@Override
	public Single<String> getAllStockTickerNameBySearchParam(String searchQuery) {

		return stockSearchAdapter.getAllStockTickerNameBySearchParam(searchQuery);
	}

}
