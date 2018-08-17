package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.StockStatsDao;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import yahoofinance.quotes.stock.StockStats;

public class StockStatsAdapterImpl implements StockStatsAdapter {

	private static final String TAG_STOCK_STATS = "STATISTICS_";
	private StockCacheStorage stockCacheStorage;

	@Inject
	public StockStatsAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<StockStats> getStockStatisticByTicker(String ticker) {

		return Single.create(new SingleOnSubscribe<StockStats>() {

			@Override
			public void subscribe(SingleEmitter<StockStats> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_STOCK_STATS + "_" + ticker))) {
					Object stockStatistics = map.get(String.valueOf(TAG_STOCK_STATS + "_" + ticker));
					subscriber.onSuccess((StockStats) stockStatistics);
				} else {
					try {

						StockStats stockStatistics = StockStatsDao.fetchStockStatistics(ticker);
						if (stockStatistics != null) {
							stockCacheStorage.add(String.valueOf(TAG_STOCK_STATS + "_" + ticker), stockStatistics);
							subscriber.onSuccess(stockStatistics);
						} else {
							subscriber
									.onError(new StockSymbolNotFoundException("Stock statistic for : " + ticker + " not found"));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

}
