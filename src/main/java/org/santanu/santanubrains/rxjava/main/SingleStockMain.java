package org.santanu.santanubrains.rxjava.main;

import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;
import com.google.gson.Gson;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import yahoofinance.Stock;

public class SingleStockMain {

	private static final Logger logger = Log4jUtil.getLogger(SingleStockMain.class);

	public static void main(String[] args) {

		List<String> symbols = Arrays.asList("GOOG");

		Single<Stock> stock = StockService.getStockDetails(symbols);
		logger.info("Got Observable...");
		
		stock.subscribe(new SingleObserver<Stock>() {

			@Override
			public void onError(Throwable throwable) {
				logger.error("Error :" + throwable.getMessage());

			}

			@Override
			public void onSubscribe(Disposable d) {
				// d.dispose();

			}

			@Override
			public void onSuccess(Stock stock) {
				Gson gson = new Gson();
				logger.info(gson.toJson(stock));
				

			}
		});

	}

}
