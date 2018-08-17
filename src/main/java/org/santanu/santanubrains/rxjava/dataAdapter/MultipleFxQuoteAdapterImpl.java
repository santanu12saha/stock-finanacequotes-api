package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.MultipleFxQuotesDao;
import org.santanu.santanubrains.rxjava.domain.FxMultipleQuoteQuery;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import yahoofinance.quotes.fx.FxQuote;

public class MultipleFxQuoteAdapterImpl implements MultipleFxQuoteAdapter {

	private static Boolean foundMap = false;
	private StockCacheStorage stockCacheStorage;

	@Inject
	public MultipleFxQuoteAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<Map<String, FxQuote>> getMultipleFxQuotesBySymbols(FxMultipleQuoteQuery fxMultipleQuoteQuery) {
		
		return Single.create(new SingleOnSubscribe<Map<String,FxQuote>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, FxQuote>> subscriber) throws Exception {
				Map<String, FxQuote> mapFxQuote = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for(String fxSymbol : fxMultipleQuoteQuery.getFxSymbols()) {
					if(map.containsKey(String.valueOf(fxSymbol))) {
						foundMap = true;
						Object fxQuote = map.get(String.valueOf(fxSymbol));
						mapFxQuote.put(fxSymbol, (FxQuote) fxQuote);
					}else {
						foundMap = false;
						break;
					}
				}
				if(foundMap) {
					subscriber.onSuccess(mapFxQuote);
				}else {
					try {
						
						Map<String, FxQuote> fxQuotes = MultipleFxQuotesDao.fetch(fxMultipleQuoteQuery);
						if(fxQuotes != null) {
							
							for(Map.Entry<String, FxQuote> entry : fxQuotes.entrySet()) {
								if(!map.containsKey(entry.getKey()))
									stockCacheStorage.add(entry.getKey(), entry.getValue());
									
							}
							subscriber.onSuccess(fxQuotes);
						}else {
							subscriber.onError(new StockSymbolNotFoundException("Fx Quote symbols not found"));
						}
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		});
	}

}
