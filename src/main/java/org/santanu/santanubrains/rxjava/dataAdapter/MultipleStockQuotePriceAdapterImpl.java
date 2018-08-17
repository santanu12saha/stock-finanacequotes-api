package org.santanu.santanubrains.rxjava.dataAdapter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.MultipleStockQuotePriceDao;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class MultipleStockQuotePriceAdapterImpl implements MultipleStockQuotePriceAdapter {

	private static Boolean foundMap = false;
	private static final String TAG_PRICE = "PRICE_";
	private StockCacheStorage stockCacheStorage;

	@Inject
	public MultipleStockQuotePriceAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<Map<String, BigDecimal>> getMultipleStockQuotePriceBySymbols(MultipleStockQuery multipleStockQuery) {

		return Single.create(new SingleOnSubscribe<Map<String, BigDecimal>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, BigDecimal>> subscriber) throws Exception {
				Map<String, BigDecimal> mapStockQuotePrice = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					if (map.containsKey(String.valueOf(TAG_PRICE + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockQprice = map.get(TAG_PRICE + ticker.toUpperCase());
						mapStockQuotePrice.put(ticker.toUpperCase(),
								(BigDecimal) stockQprice);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockQuotePrice);
				} else {
					try {

						Map<String, BigDecimal> stockQuotesPrice = MultipleStockQuotePriceDao.fetch(multipleStockQuery);
						if (stockQuotesPrice != null) {

							for (Map.Entry<String, BigDecimal> entry : stockQuotesPrice.entrySet()) {
								if (!map.containsKey(TAG_PRICE+entry.getKey())) {
									stockCacheStorage.add(TAG_PRICE+entry.getKey(), entry.getValue());
								}
							}
							subscriber.onSuccess(stockQuotesPrice);
						} else {
							subscriber.onError(new StockSymbolNotFoundException("Stock Quote price symbols not found"));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

}
