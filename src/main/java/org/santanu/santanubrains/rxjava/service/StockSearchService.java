package org.santanu.santanubrains.rxjava.service;

import io.reactivex.Single;

public interface StockSearchService {
	Single<String> getAllStockTickerNameBySearchParam(String searchQuery);
}
