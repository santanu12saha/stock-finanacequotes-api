package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.MultipleStockStatsDao;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import yahoofinance.quotes.stock.StockStats;

public class MultipleStockStatsAdapterImpl implements MultipleStockStatsAdapter {

	private static Boolean foundMap = false;
	private static final String TAG_STOCK_STATS = "STATISTICS_";
	private StockCacheStorage stockCacheStorage;

	@Inject
	public MultipleStockStatsAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<Map<String, StockStats>> getMultipleStockStatisticByTicker(MultipleStockQuery multipleStockQuery) {

		return Single.create(new SingleOnSubscribe<Map<String, StockStats>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, StockStats>> subscriber) throws Exception {

				Map<String, StockStats> mapStockStatistic = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					if (map.containsKey(String.valueOf(TAG_STOCK_STATS + "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockStatistic = map.get(String.valueOf(TAG_STOCK_STATS + "_" + ticker.toUpperCase()));
						mapStockStatistic.put(ticker.toUpperCase(), (StockStats) stockStatistic);
					}else {
						foundMap = false;
						break;
					}
				}
				if(foundMap) {
					subscriber.onSuccess(mapStockStatistic);
				}else {
					try {
						Map<String, StockStats> mapstockStats = MultipleStockStatsDao.fetchMultipleStockStatistics(multipleStockQuery);
						if(mapstockStats != null) {
							for(Map.Entry<String, StockStats> entry : mapstockStats.entrySet()) {
								if(!map.containsKey(TAG_STOCK_STATS + "_"+entry.getKey())) {
									stockCacheStorage.add(TAG_STOCK_STATS + "_"+entry.getKey(), entry.getValue());
								}
							}
							subscriber.onSuccess(mapstockStats);
						}else {
							subscriber.onError(new StockSymbolNotFoundException("Stock statistic symbols not found"));
						}
						
					}catch(Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

}
