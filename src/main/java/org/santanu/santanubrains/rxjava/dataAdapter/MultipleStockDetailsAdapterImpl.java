package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import javax.inject.Inject;
import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.MultipleStockDao;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import yahoofinance.Stock;

public class MultipleStockDetailsAdapterImpl implements MultipleStockDetailsAdapter {

	private static Boolean foundMap = false;
	private static final String TAG_HISTORICAL_INCLUDE = "INCLUDE_HIS_";
	private static final String TAG_STOCK_FROMDATE = "STOCK_FROMDATE_";
	private static final String TAG_STOCK_INTERVAL = "STOCK_INTERVAL_";
	private static final String TAG_STOCK_FROMTODATE = "STOCK_FROMTODATE_";
	private static final String TAG_STOCK_FROMINTERVAL = "STOCk_FROMINTERVAL_";
	private static final String TAG_STOCK_FROMTOINTERVAL = "STOCK_FROMTOINTERVAL_";
	private StockCacheStorage stockCacheStorage;

	@Inject
	public MultipleStockDetailsAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<Map<String, Stock>> getMultipleStockDetailsByTickers(MultipleStockQuery multipleStockQuery) {

		return Single.create(new SingleOnSubscribe<Map<String, Stock>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, Stock>> subscriber) throws Exception {
				Map<String, Stock> mapStock = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					if (map.containsKey(String.valueOf(ticker.toUpperCase()))) {
						foundMap = true;
						Object stock = map.get(String.valueOf(ticker.toUpperCase()));
						mapStock.put(ticker.toUpperCase(), (Stock) stock);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStock);
				} else {
					try {
						Map<String, Stock> stocks = MultipleStockDao.fetch(multipleStockQuery);
						if (stocks != null) {

							for (Map.Entry<String, Stock> entry : stocks.entrySet()) {
								if (!map.containsKey(entry.getKey().toUpperCase()))
									stockCacheStorage.add(entry.getKey().toUpperCase(), entry.getValue());
							}
							subscriber.onSuccess(stocks);
						} else {
							subscriber.onError(new StockSymbolNotFoundException("Stock symbols not found"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	@Override
	public Single<Map<String, Stock>> getMultipleStockDetailsWithIncludeHistoricalDataByTicker(
			MultipleStockQuery multipleStockQuery, boolean includeHistorical) {

		return Single.create(new SingleOnSubscribe<Map<String, Stock>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, Stock>> subscriber) throws Exception {

				Map<String, Stock> mapStockHistoricalData = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					if (map.containsKey(String
							.valueOf(TAG_HISTORICAL_INCLUDE + "_" + includeHistorical + "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object mapStockHistorical = map.get(String.valueOf(
								TAG_HISTORICAL_INCLUDE + "_" + includeHistorical + "_" + ticker.toUpperCase()));
						mapStockHistoricalData.put(ticker.toUpperCase(), (Stock) mapStockHistorical);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockHistoricalData);
				} else {
					try {

						Map<String, Stock> mapStockHistorical = MultipleStockDao
								.fetchMultipleStockWithHistoricalData(multipleStockQuery, includeHistorical);
						if (mapStockHistorical != null) {
							mapStockHistorical.forEach((k, v) -> {
								if (!map.containsKey(String.valueOf(
										TAG_HISTORICAL_INCLUDE + "_" + includeHistorical + "_" + k.toUpperCase()))) {
									stockCacheStorage.add(String.valueOf(
											TAG_HISTORICAL_INCLUDE + "_" + includeHistorical + "_" + k.toUpperCase()),
											v);
								}
							});
							subscriber.onSuccess(mapStockHistorical);
						} else {
							subscriber.onError(new StockSymbolNotFoundException("Stock symbols not found"));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	@Override
	public Single<Map<String, Stock>> getMultipleStockByFromDate(MultipleStockQuery multipleStockQuery,
			String fromDate) {

		return Single.create(new SingleOnSubscribe<Map<String, Stock>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, Stock>> subscriber) throws Exception {

				Map<String, Stock> mapStockFromDate = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					if (map.containsKey(
							String.valueOf(TAG_STOCK_FROMDATE + "_" + fromDate + "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockFromDate = map
								.get(String.valueOf(TAG_STOCK_FROMDATE + "_" + fromDate + "_" + ticker.toUpperCase()));
						mapStockFromDate.put(ticker.toUpperCase(), (Stock) stockFromDate);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockFromDate);
				} else {
					Map<String, Stock> stockFromDate = MultipleStockDao.fetchMultipleStockByFromDate(multipleStockQuery,
							fromDate);
					if (stockFromDate != null) {
						stockFromDate.forEach((k, v) -> {
							if (!map.containsKey(
									String.valueOf(TAG_STOCK_FROMDATE + "_" + fromDate + "_" + k.toUpperCase()))) {
								stockCacheStorage.add(
										String.valueOf(TAG_STOCK_FROMDATE + "_" + fromDate + "_" + k.toUpperCase()), v);
							}
						});
						subscriber.onSuccess(stockFromDate);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock symbols not found"));
					}
				}

			}
		});
	}

	@Override
	public Single<Map<String, Stock>> getMultipleStockByInterval(MultipleStockQuery multipleStockQuery,
			String interval) {

		return Single.create(new SingleOnSubscribe<Map<String, Stock>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, Stock>> subscriber) throws Exception {

				Map<String, Stock> mapStockInterval = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					if (map.containsKey(
							String.valueOf(TAG_STOCK_INTERVAL + "_" + interval + "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockInterval = map
								.get(String.valueOf(TAG_STOCK_INTERVAL + "_" + interval + "_" + ticker.toUpperCase()));
						mapStockInterval.put(ticker.toUpperCase(), (Stock) stockInterval);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockInterval);
				} else {
					Map<String, Stock> stockInterval = MultipleStockDao.fetchMultipleStockByInterval(multipleStockQuery,
							interval);
					if (stockInterval != null) {
						stockInterval.forEach((k, v) -> {
							if (!map.containsKey(
									String.valueOf(TAG_STOCK_INTERVAL + "_" + interval + "_" + k.toUpperCase()))) {
								stockCacheStorage.add(
										String.valueOf(TAG_STOCK_INTERVAL + "_" + interval + "_" + k.toUpperCase()), v);
							}
						});
						subscriber.onSuccess(stockInterval);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock symbols not found"));
					}
				}
			}
		});
	}

	@Override
	public Single<Map<String, Stock>> getMultipleStockByFromToDate(MultipleStockQuery multipleStockQuery,
			String fromDate, String toDate) {

		return Single.create(new SingleOnSubscribe<Map<String, Stock>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, Stock>> subscriber) throws Exception {

				Map<String, Stock> mapStockFromToDate = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					if (map.containsKey(String.valueOf(
							TAG_STOCK_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockFromToDate = map.get(String.valueOf(
								TAG_STOCK_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + ticker.toUpperCase()));
						mapStockFromToDate.put(ticker.toUpperCase(), (Stock) stockFromToDate);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockFromToDate);
				} else {
					Map<String, Stock> stockFromToDate = MultipleStockDao
							.fetchMultipleStockByFromToDate(multipleStockQuery, fromDate, toDate);
					if (stockFromToDate != null) {
						stockFromToDate.forEach((k, v) -> {
							if (!map.containsKey(String.valueOf(
									TAG_STOCK_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + k.toUpperCase()))) {
								stockCacheStorage.add(String.valueOf(
										TAG_STOCK_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + k.toUpperCase()),
										v);
							}
						});
						subscriber.onSuccess(stockFromToDate);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock symbols not found"));
					}
				}

			}
		});
	}

	@Override
	public Single<Map<String, Stock>> getMultipleStockByFromInterval(MultipleStockQuery multipleStockQuery,
			String fromDate, String interval) {

		return Single.create(new SingleOnSubscribe<Map<String, Stock>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, Stock>> subscriber) throws Exception {

				Map<String, Stock> mapStockFromInterval = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					if (map.containsKey(String.valueOf(
							TAG_STOCK_FROMINTERVAL + "_" + fromDate + "_" + interval + "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockFromInterval = map.get(String.valueOf(
								TAG_STOCK_FROMINTERVAL + "_" + fromDate + "_" + interval + "_" + ticker.toUpperCase()));
						mapStockFromInterval.put(ticker.toUpperCase(), (Stock) stockFromInterval);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockFromInterval);
				} else {
					Map<String, Stock> stockFromInterval = MultipleStockDao
							.fetchMultipleStockByFromInterval(multipleStockQuery, fromDate, interval);
					if (stockFromInterval != null) {
						stockFromInterval.forEach((k, v) -> {
							if (!map.containsKey(String.valueOf(TAG_STOCK_FROMINTERVAL + "_" + fromDate + "_" + interval
									+ "_" + k.toUpperCase()))) {
								stockCacheStorage.add(String.valueOf(TAG_STOCK_FROMINTERVAL + "_" + fromDate + "_"
										+ interval + "_" + k.toUpperCase()), v);
							}
						});
						subscriber.onSuccess(stockFromInterval);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock symbols not found"));
					}
				}
			}
		});
	}

	@Override
	public Single<Map<String, Stock>> getMultipleStockByFromToDateInterval(MultipleStockQuery multipleStockQuery,
			String fromDate, String toDate, String interval) {

		return Single.create(new SingleOnSubscribe<Map<String, Stock>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, Stock>> subscriber) throws Exception {

				Map<String, Stock> mapStockFromToInterval = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					if (map.containsKey(String.valueOf(TAG_STOCK_FROMTOINTERVAL + "_" + fromDate + "_" + toDate + "_"
							+ interval + "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockFromToInterval = map.get(String.valueOf(TAG_STOCK_FROMTOINTERVAL + "_" + fromDate
								+ "_" + toDate + "_" + interval + "_" + ticker.toUpperCase()));
						mapStockFromToInterval.put(ticker.toUpperCase(), (Stock) stockFromToInterval);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockFromToInterval);
				} else {
					Map<String, Stock> stockFromToInterval = MultipleStockDao
							.fetchMultipleStockByFromToDateInterval(multipleStockQuery, fromDate, toDate, interval);
					if (stockFromToInterval != null) {
						stockFromToInterval.forEach((k, v) -> {
							if (!map.containsKey(String.valueOf(TAG_STOCK_FROMTOINTERVAL + "_" + fromDate + "_" + toDate
									+ "_" + interval + "_" + k.toUpperCase()))) {
								stockCacheStorage.add(String.valueOf(TAG_STOCK_FROMTOINTERVAL + "_" + fromDate + "_"
										+ toDate + "_" + interval + "_" + k.toUpperCase()), v);
							}
						});
						subscriber.onSuccess(stockFromToInterval);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock symbols not found"));
					}
				}

			}
		});
	}

}
