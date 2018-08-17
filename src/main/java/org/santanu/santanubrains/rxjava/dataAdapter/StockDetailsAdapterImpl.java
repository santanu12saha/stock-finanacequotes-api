package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.concurrent.ConcurrentMap;
import javax.inject.Inject;
import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.StockDao;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import yahoofinance.Stock;

public class StockDetailsAdapterImpl implements StockDetailsAdapter {

	private static final String TAG_HISTORICAL_INCLUDE = "INCLUDE_HIS_";
	private static final String TAG_STOCK_FROMDATE = "STOCK_FROMDATE_";
	private static final String TAG_STOCK_INTERVAL = "STOCK_INTERVAL_";
	private static final String TAG_STOCK_FROMTODATE = "STOCK_FROMTODATE_";
	private static final String TAG_STOCK_FROMINTERVAL = "STOCk_FROMINTERVAL_";
	private static final String TAG_STOCK_FROMTOINTERVAL = "STOCK_FROMTOINTERVAL_";
	private StockCacheStorage stockCacheStorage;

	@Inject
	public StockDetailsAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<Stock> getStockDetailsByTicker(String ticker) {

		return Single.create(new SingleOnSubscribe<Stock>() {

			@Override
			public void subscribe(SingleEmitter<Stock> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(ticker))) {
					Object stock = map.get(String.valueOf(ticker));
					subscriber.onSuccess((Stock) stock);

				} else {

					try {
						Stock stockDetail = StockDao.fetch(ticker);
						if (stockDetail != null) {
							stockCacheStorage.add(String.valueOf(ticker), stockDetail);
							subscriber.onSuccess(stockDetail);
						} else {
							subscriber
									.onError(new StockSymbolNotFoundException("Stock symbol " + ticker + " not found"));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}
		});
	}

	@Override
	public Single<Stock> getStockDetailsWithIncludeHistoricalDataByTicker(String ticker, boolean includeHistorical) {

		return Single.create(new SingleOnSubscribe<Stock>() {

			@Override
			public void subscribe(SingleEmitter<Stock> subscriber) throws Exception {
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_HISTORICAL_INCLUDE + "_" + includeHistorical + "_" + ticker))) {
					Object stock = map
							.get(String.valueOf(TAG_HISTORICAL_INCLUDE + "_" + includeHistorical + "_" + ticker));
					subscriber.onSuccess((Stock) stock);
				} else {
					try {
						Stock stockDetail = StockDao.fetchStockWithHistoricalData(ticker, includeHistorical);
						if (stockDetail != null) {
							stockCacheStorage.add(
									String.valueOf(TAG_HISTORICAL_INCLUDE + "_" + includeHistorical + "_" + ticker),
									stockDetail);
							subscriber.onSuccess(stockDetail);
						} else {
							subscriber
									.onError(new StockSymbolNotFoundException("Stock symbol " + ticker + " not found"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	@Override
	public Single<Stock> getStockByFromDate(String ticker, String fromDate) {

		return Single.create(new SingleOnSubscribe<Stock>() {

			@Override
			public void subscribe(SingleEmitter<Stock> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_STOCK_FROMDATE + "_" + fromDate + "_" + ticker))) {
					Object stockFromDate = map.get(String.valueOf(TAG_STOCK_FROMDATE + "_" + fromDate + "_" + ticker));
					subscriber.onSuccess((Stock) stockFromDate);
				} else {
					Stock stockFromDate = StockDao.fetchStockByFromDate(ticker, fromDate);
					if (stockFromDate != null) {
						stockCacheStorage.add(String.valueOf(TAG_STOCK_FROMDATE + "_" + fromDate + "_" + ticker),
								stockFromDate);
						subscriber.onSuccess(stockFromDate);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock symbol " + ticker + " not found"));
					}
				}

			}
		});
	}

	@Override
	public Single<Stock> getStockByInterval(String ticker, String interval) {

		return Single.create(new SingleOnSubscribe<Stock>() {

			@Override
			public void subscribe(SingleEmitter<Stock> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_STOCK_INTERVAL + "_" + interval + "_" + ticker))) {
					Object stockByInterval = map
							.get(String.valueOf(TAG_STOCK_INTERVAL + "_" + interval + "_" + ticker));
					subscriber.onSuccess((Stock) stockByInterval);
				} else {
					Stock stockByInterval = StockDao.fetchStockByInterval(ticker, interval);
					if (stockByInterval != null) {
						stockCacheStorage.add(String.valueOf(TAG_STOCK_INTERVAL + "_" + interval + "_" + ticker),
								stockByInterval);
						subscriber.onSuccess(stockByInterval);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock symbol " + ticker + " not found"));
					}
				}

			}
		});
	}

	@Override
	public Single<Stock> getStockByFromToDate(String ticker, String fromDate, String toDate) {

		return Single.create(new SingleOnSubscribe<Stock>() {

			@Override
			public void subscribe(SingleEmitter<Stock> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(
						String.valueOf(TAG_STOCK_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + ticker))) {
					Object stockByFromToDate = map
							.get(String.valueOf(TAG_STOCK_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + ticker));
					subscriber.onSuccess((Stock) stockByFromToDate);
				} else {
					Stock stockByFromToDate = StockDao.fetchStockByFromToDate(ticker, fromDate, toDate);
					if (stockByFromToDate != null) {
						stockCacheStorage.add(
								String.valueOf(TAG_STOCK_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + ticker),
								stockByFromToDate);
						subscriber.onSuccess(stockByFromToDate);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock symbol " + ticker + " not found"));
					}
				}

			}
		});
	}

	@Override
	public Single<Stock> getStockByFromInterval(String ticker, String fromDate, String interval) {

		return Single.create(new SingleOnSubscribe<Stock>() {

			@Override
			public void subscribe(SingleEmitter<Stock> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(
						String.valueOf(TAG_STOCK_FROMINTERVAL + "_" + fromDate + "_" + interval + "_" + ticker))) {
					Object stockByFromInterval = map.get(
							String.valueOf(TAG_STOCK_FROMINTERVAL + "_" + fromDate + "_" + interval + "_" + ticker));
					subscriber.onSuccess((Stock) stockByFromInterval);
				} else {
					Stock stockByFromInterval = StockDao.fetchStockByFromInterval(ticker, fromDate, interval);
					if (stockByFromInterval != null) {
						stockCacheStorage.add(
								String.valueOf(TAG_STOCK_FROMINTERVAL + "_" + fromDate + "_" + interval + "_" + ticker),
								stockByFromInterval);
						subscriber.onSuccess(stockByFromInterval);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock symbol " + ticker + " not found"));
					}
				}

			}
		});
	}

	@Override
	public Single<Stock> getStockByFromToDateInterval(String ticker, String fromDate, String toDate, String interval) {

		return Single.create(new SingleOnSubscribe<Stock>() {

			@Override
			public void subscribe(SingleEmitter<Stock> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(
						TAG_STOCK_FROMTOINTERVAL + "_" + fromDate + "_" + toDate + "_" + interval + "_" + ticker))) {
					Object stockByFromToDateInterval = map.get(String.valueOf(
							TAG_STOCK_FROMTOINTERVAL + "_" + fromDate + "_" + toDate + "_" + interval + "_" + ticker));
					subscriber.onSuccess((Stock) stockByFromToDateInterval);

				} else {
					Stock stockByFromToDateInterval = StockDao.fetchStockByFromToDateInterval(ticker, fromDate, toDate,
							interval);
					if (stockByFromToDateInterval != null) {
						stockCacheStorage.add(String.valueOf(TAG_STOCK_FROMTOINTERVAL + "_" + fromDate + "_" + toDate
								+ "_" + interval + "_" + ticker), stockByFromToDateInterval);
						subscriber.onSuccess(stockByFromToDateInterval);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock symbol " + ticker + " not found"));
					}
				}

			}
		});
	}

}
