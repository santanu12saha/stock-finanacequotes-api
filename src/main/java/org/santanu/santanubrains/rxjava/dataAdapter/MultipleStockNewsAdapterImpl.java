package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.MultipleStockNewsDao;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;
import org.santanu.santanubrains.rxjava.domain.news.News;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class MultipleStockNewsAdapterImpl implements MultipleStockNewsAdapter {

	private static Boolean foundMap = false;
	private static final String TAG_NEWS_QUERY = "NEWS_QUERY_";
	private StockCacheStorage stockCacheStorage;

	@Inject
	public MultipleStockNewsAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<Map<String, News[]>> getMultipleStockNewsByTicker(MultipleStockQuery multipleStockQuery) {

		return Single.create(new SingleOnSubscribe<Map<String, News[]>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, News[]>> subscriber) throws Exception {
				Map<String, News[]> mapStockNews = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					if (map.containsKey(String.valueOf(TAG_NEWS_QUERY + "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockNews = map.get(String.valueOf(TAG_NEWS_QUERY + "_" + ticker.toUpperCase()));
						mapStockNews.put(ticker.toUpperCase(), (News[]) stockNews);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockNews);
				} else {

					try {
						Map<String, News[]> stockNews = MultipleStockNewsDao.multipleNewsRead(multipleStockQuery);
						if (stockNews != null) {
							for (Map.Entry<String, News[]> entry : stockNews.entrySet()) {
								if (!map.containsKey(String.valueOf(TAG_NEWS_QUERY + "_" + entry.getKey()))) {
									stockCacheStorage.add(String.valueOf(TAG_NEWS_QUERY + "_" + entry.getKey()),
											entry.getValue());
								}
							}
							subscriber.onSuccess(stockNews);
						} else {
							subscriber
									.onError(new StockSymbolNotFoundException("No Stock News found for search query"));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}
		});
	}

}
