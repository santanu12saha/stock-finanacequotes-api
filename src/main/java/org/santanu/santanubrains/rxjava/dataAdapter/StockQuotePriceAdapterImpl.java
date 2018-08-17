package org.santanu.santanubrains.rxjava.dataAdapter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.StockQuotePriceDao;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class StockQuotePriceAdapterImpl implements StockQuotePriceAdapter {

	private static final String TAG_PRICE = "PRICE_";
	private StockCacheStorage stockCacheStorage;

	@Inject
	public StockQuotePriceAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<Map<String, BigDecimal>> getStockQuotePriceByTicker(String ticker) {

		return Single.create(new SingleOnSubscribe<Map<String, BigDecimal>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, BigDecimal>> subscriber) throws Exception {

				Map<String, BigDecimal> mapStockQuotePrice = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_PRICE + ticker))) {
					Object stockQuotePrice = map.get(String.valueOf(TAG_PRICE + ticker));
					mapStockQuotePrice.put(ticker, (BigDecimal) stockQuotePrice);
					subscriber.onSuccess(mapStockQuotePrice);
				} else {
					try {
						Map<String, BigDecimal> quotePrice = StockQuotePriceDao.fetch(ticker);
						if (quotePrice != null) {
							for (Map.Entry<String, BigDecimal> entry : quotePrice.entrySet()) {
								if (!map.containsKey(TAG_PRICE+entry.getKey()))
									stockCacheStorage.add(TAG_PRICE+entry.getKey(), entry.getValue());
							}
							subscriber.onSuccess(quotePrice);
						} else {
							subscriber.onError(new StockSymbolNotFoundException("Stock Quote price not found"));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

}
