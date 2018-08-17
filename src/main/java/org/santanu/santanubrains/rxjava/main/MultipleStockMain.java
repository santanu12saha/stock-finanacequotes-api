package org.santanu.santanubrains.rxjava.main;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;

import com.google.gson.Gson;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import yahoofinance.Stock;

public class MultipleStockMain {

	private static final Logger logger = Log4jUtil.getLogger(MultipleStockMain.class);
	public static void main(String[] args) {
		
		List<String> symbols = Arrays.asList("GOOG", "ITCkkjkzv", "BABA", "TSLA", "AIR.PA", "YHOO", "AAPL");
		
		Single<Map<String, Stock>> stocks = MultipleStockService.getMultipleStocksDetails(symbols);
		logger.info("Got Observable...");
		
		stocks.subscribe(new SingleObserver<Map<String, Stock>>() {

			@Override
			public void onSubscribe(Disposable d) {
				
			}

			@Override
			public void onSuccess(Map<String, Stock> stocks) {
				Gson gson = new Gson();
				logger.info(gson.toJson(stocks));
				
			}

			@Override
			public void onError(Throwable throwable) {
				logger.error("Error :" + throwable.getMessage());
				
			}
		});
		
		

	}

}
