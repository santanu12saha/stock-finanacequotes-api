package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.FxQuoteDao;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import yahoofinance.quotes.fx.FxQuote;

public class FxQuoteAdapterImpl implements FxQuoteAdapter {

	private StockCacheStorage stockCacheStorage;

	@Inject
	public FxQuoteAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<FxQuote> getFxQuoteBySysmbol(String fxSymbol) {

		return Single.create(new SingleOnSubscribe<FxQuote>() {

			@Override
			public void subscribe(SingleEmitter<FxQuote> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(fxSymbol)) {
					Object fxQuote = map.get(String.valueOf(fxSymbol));
					subscriber.onSuccess((FxQuote) fxQuote);
				} else {
					try {

						FxQuote fxQuoteDetail = FxQuoteDao.fetch(fxSymbol);
						if (fxQuoteDetail != null) {
							stockCacheStorage.add(String.valueOf(fxSymbol), fxQuoteDetail);
							subscriber.onSuccess(fxQuoteDetail);
						} else {
							subscriber
									.onError(new StockSymbolNotFoundException("Fx symbol " + fxSymbol + " not found"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

}
