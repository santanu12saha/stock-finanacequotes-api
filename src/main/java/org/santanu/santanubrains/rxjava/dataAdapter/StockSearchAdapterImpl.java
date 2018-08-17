package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.StockSearchDao;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;
import org.santanu.santanubrains.rxjava.utility.SearchRegexChecking;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class StockSearchAdapterImpl implements StockSearchAdapter {

	private static final String TAG_SEARCH_QUERY = "SEARCH_QUERY_";
	private StockCacheStorage stockCacheStorage;

	@Inject
	public StockSearchAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<String> getAllStockTickerNameBySearchParam(String searchQuery) {

		return Single.create(new SingleOnSubscribe<String>() {

			@Override
			public void subscribe(SingleEmitter<String> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_SEARCH_QUERY + searchQuery.toUpperCase()))) {
					Object searchResponse = map.get(String.valueOf(TAG_SEARCH_QUERY + searchQuery.toUpperCase()));
					subscriber.onSuccess((String) searchResponse);
				} else {
					if (SearchRegexChecking.check(searchQuery)) {
						String searchResponse = StockSearchDao.search(searchQuery);
						if (searchResponse != null) {
							JsonNode rootNode = new ObjectMapper().readTree(searchResponse).get("ResultSet");
							JsonNode arrNode = rootNode.path("Result");
							if (arrNode.size() > 0) {
								stockCacheStorage.add(String.valueOf(TAG_SEARCH_QUERY + searchQuery.toUpperCase()),
										searchResponse);
								subscriber.onSuccess(searchResponse);
							} else {
								subscriber.onError(new StockSymbolNotFoundException(
										"No Stock found for search query: " + searchQuery));
							}

						} else {
							subscriber.onError(new StockSymbolNotFoundException(
									"No Stock found for search query: " + searchQuery));
						}
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Invalid Search Query: " + searchQuery));
					}
				}

			}
		});
	}

}
