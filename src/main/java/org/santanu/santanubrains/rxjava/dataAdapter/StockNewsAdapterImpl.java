package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.StockNewsDao;
import org.santanu.santanubrains.rxjava.domain.news.News;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;
import org.santanu.santanubrains.rxjava.utility.SearchRegexChecking;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class StockNewsAdapterImpl implements StockNewsAdapter {

	private static final String TAG_NEWS_QUERY = "NEWS_QUERY_";
	private StockCacheStorage stockCacheStorage;

	@Inject
	public StockNewsAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<Map<String, News[]>> getStockNewsByTicker(String ticker) {

		return Single.create(new SingleOnSubscribe<Map<String, News[]>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, News[]>> subscriber) throws Exception {

				Map<String, News[]> mapNewsStock = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_NEWS_QUERY + "_" + ticker))) {
					Object stockNews = map.get((String.valueOf(TAG_NEWS_QUERY + "_" + ticker)));
					mapNewsStock.put(ticker, (News[]) stockNews);
					subscriber.onSuccess(mapNewsStock);
				} else {
					if (SearchRegexChecking.check(ticker)) {
						Map<String, News[]> mapNewsResponse = StockNewsDao.newsRead(ticker);
						if (mapNewsResponse != null) {
							for (Map.Entry<String, News[]> entry : mapNewsResponse.entrySet()) {
								if (!map.containsKey(String.valueOf(TAG_NEWS_QUERY + "_" + entry.getKey()))) {
									stockCacheStorage.add(String.valueOf(TAG_NEWS_QUERY + "_" + entry.getKey()),
											entry.getValue());
								}
							}

							subscriber.onSuccess(mapNewsResponse);
						} else {
							subscriber.onError(new StockSymbolNotFoundException(
									"No Stock News found for search query: " + ticker));
						}
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Invalid Search Query: " + ticker));
					}
				}

			}
		});
	}

}
