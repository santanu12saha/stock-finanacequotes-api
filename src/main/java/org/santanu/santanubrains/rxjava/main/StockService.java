package org.santanu.santanubrains.rxjava.main;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import yahoofinance.Stock;

public class StockService {

	public static Single<Stock> getStockDetails(List<String> symbols) {
		System.out.println("Created....");
		return Single.create(emitter -> emitStockDetails(emitter, symbols));
	}

	private static void emitStockDetails(SingleEmitter<Stock> emitter, List<String> symbols) {
		System.out.println("Ready to emit...");
		while(!emitter.isDisposed()) {
			symbols.stream()
				   .map(StockFetcher::fetch)
				   .filter(data -> !emitter.isDisposed())
				   .forEach(emitter::onSuccess);
			
		}
		
		
		
	}

}
