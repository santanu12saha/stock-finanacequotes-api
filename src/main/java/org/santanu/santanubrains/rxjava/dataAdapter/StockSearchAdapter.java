package org.santanu.santanubrains.rxjava.dataAdapter;

import io.reactivex.Single;

public interface StockSearchAdapter {
	Single<String> getAllStockTickerNameBySearchParam(String searchQuery);
}
