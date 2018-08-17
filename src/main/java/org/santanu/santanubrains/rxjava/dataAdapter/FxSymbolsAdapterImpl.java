package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.FxSymbolDao;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class FxSymbolsAdapterImpl implements FxSymbolsAdapter {

	private static final String FXSYMBOL_LIST = "fxSymbolList";
	private StockCacheStorage stockCacheStorage;

	@Inject
	public FxSymbolsAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<Map<String, Set<String>>> getFxSymbols() {

		return Single.create(new SingleOnSubscribe<Map<String, Set<String>>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<Map<String, Set<String>>> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(FXSYMBOL_LIST))) {
					Object fxSymbol = map.get(String.valueOf(FXSYMBOL_LIST));
					subscriber.onSuccess((Map<String, Set<String>>) fxSymbol);
				} else {
					try {

						Map<String, Set<String>> fxSymbolList = FxSymbolDao.fetchFxSymbol();
						if (fxSymbolList != null) {
							stockCacheStorage.add(String.valueOf(FXSYMBOL_LIST), fxSymbolList);
							subscriber.onSuccess(fxSymbolList);
						} else {
							subscriber.onError(new StockSymbolNotFoundException("No Fx Symbols found"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

}
