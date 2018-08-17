package org.santanu.santanubrains.rxjava.main;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import yahoofinance.Stock;

public class MultipleStockService {

	private static final Logger logger = Log4jUtil.getLogger(MultipleStockService.class);
	
	public static Single<Map<String, Stock>> getMultipleStocksDetails(List<String> symbols) {
		logger.info("Created....");
		return Single.create(new SingleOnSubscribe<Map<String, Stock>>() {
			
			@Override
			public void subscribe(SingleEmitter<Map<String, Stock>> emitter) throws Exception {
				
				logger.info("emitting data....");
				
				try {
					Map<String, Stock> stocks = MultipleStockFetcher.fetch(symbols);
					if (stocks != null) {
						emitter.onSuccess(stocks);
					} else {
						emitter.onError(new RuntimeException("Stock not found"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

}
