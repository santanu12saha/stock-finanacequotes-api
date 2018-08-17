package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.MultipleStockDividendDao;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockDividend;

public class MultipleStockDividendAdapterImpl implements MultipleStockDividendAdapter {

	private static Boolean foundMap = false;
	private static final String TAG_DIVIDEND = "DIVIDEND_";
	private static final String TAG_DIVIDEND_HISTORY = "HIS_DIVIDEND_";
	private static final String TAG_DIVIDEND_HISTORY_FROMDATE = "HIS_DIVIDEND_FROM_";
	private static final String TAG_DIVIDEND_HISTORY_FROMTODATE = "HIS_DIVIDEND_FROM_TO";
	private StockCacheStorage stockCacheStorage;

	@Inject
	public MultipleStockDividendAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<Map<String, StockDividend>> getMultipleStockDividendByTicker(MultipleStockQuery multipleStockQuery) {

		return Single.create(new SingleOnSubscribe<Map<String, StockDividend>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, StockDividend>> subscriber) throws Exception {

				Map<String, StockDividend> mapStockDividend = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					if (map.containsKey(String.valueOf(TAG_DIVIDEND + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockDividend = map.get(String.valueOf(TAG_DIVIDEND + ticker.toUpperCase()));
						mapStockDividend.put(ticker.toUpperCase(), (StockDividend) stockDividend);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockDividend);
				} else {
					try {

						Map<String, StockDividend> stockDividends = MultipleStockDividendDao.fetch(multipleStockQuery);
						if (stockDividends != null) {
							for (Map.Entry<String, StockDividend> entry : stockDividends.entrySet()) {
								if (!map.containsKey(TAG_DIVIDEND + entry.getKey())) {
									stockCacheStorage.add(TAG_DIVIDEND + entry.getKey(), entry.getValue());
								}
							}
							subscriber.onSuccess(stockDividends);
						} else {
							subscriber.onError(new StockSymbolNotFoundException("Stock Dividend symbols not found"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	@Override
	public Single<Map<String, List<HistoricalDividend>>> getMultipleStockHistoricalDividendByTicker(
			MultipleStockQuery multipleStockQuery) {

		return Single.create(new SingleOnSubscribe<Map<String, List<HistoricalDividend>>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<Map<String, List<HistoricalDividend>>> subscriber) throws Exception {

				Map<String, List<HistoricalDividend>> mapStockHistoricalDividend = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					if (map.containsKey(String.valueOf(TAG_DIVIDEND_HISTORY + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockHistoricalDividendlist = map
								.get(String.valueOf(TAG_DIVIDEND_HISTORY + ticker.toUpperCase()));
						mapStockHistoricalDividend.put(ticker.toUpperCase(),
								(List<HistoricalDividend>) stockHistoricalDividendlist);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockHistoricalDividend);
				} else {
					try {

						Map<String, List<HistoricalDividend>> stockHistoricalDividend = MultipleStockDividendDao
								.fetchMultipleDividendHistory(multipleStockQuery);
						if (stockHistoricalDividend != null) {
							for (Map.Entry<String, List<HistoricalDividend>> entry : stockHistoricalDividend
									.entrySet()) {
								if (!map.containsKey(TAG_DIVIDEND_HISTORY + entry.getKey())) {
									stockCacheStorage.add(TAG_DIVIDEND_HISTORY + entry.getKey(), entry.getValue());
								}
							}
							subscriber.onSuccess(stockHistoricalDividend);
						} else {
							subscriber.onError(new StockSymbolNotFoundException("Stock Dividend symbols not found"));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	@Override
	public Single<Map<String, List<HistoricalDividend>>> getMultipleStockHisDivByFromDate(
			MultipleStockQuery multipleStockQuery, String fromDate) {

		return Single.create(new SingleOnSubscribe<Map<String, List<HistoricalDividend>>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<Map<String, List<HistoricalDividend>>> subscriber) throws Exception {

				Map<String, List<HistoricalDividend>> mapStockHistoricalDividendFromDate = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					if (map.containsKey(String.valueOf(TAG_DIVIDEND_HISTORY_FROMDATE + "_" + fromDate + "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockHistoricalDividendFromDatelist = map
								.get(String.valueOf(TAG_DIVIDEND_HISTORY_FROMDATE + "_" + fromDate + "_" + ticker.toUpperCase()));
						mapStockHistoricalDividendFromDate.put(ticker.toUpperCase(),
								(List<HistoricalDividend>) stockHistoricalDividendFromDatelist);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockHistoricalDividendFromDate);
				} else {
					Map<String, List<HistoricalDividend>> stockHistoricalDividendFromDatelist = MultipleStockDividendDao
							.fetchMultipleDividendHistoryFromDate(multipleStockQuery, fromDate);
					if (stockHistoricalDividendFromDatelist != null) {
						for (Map.Entry<String, List<HistoricalDividend>> entry : stockHistoricalDividendFromDatelist
								.entrySet()) {
							if (!map.containsKey(TAG_DIVIDEND_HISTORY_FROMDATE + "_" + fromDate + "_" + entry.getKey())) {
								stockCacheStorage.add(TAG_DIVIDEND_HISTORY_FROMDATE + "_" + fromDate + "_" +entry.getKey(), entry.getValue());
							}
						}
						subscriber.onSuccess(stockHistoricalDividendFromDatelist);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock Dividend symbols not found"));
					}
				}

			}
		});
	}

	@Override
	public Single<Map<String, List<HistoricalDividend>>> getMultipleStockHisDivByFromToDate(
			MultipleStockQuery multipleStockQuery, String fromDate, String toDate) {

		return Single.create(new SingleOnSubscribe<Map<String, List<HistoricalDividend>>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<Map<String, List<HistoricalDividend>>> subscriber) throws Exception {

				Map<String, List<HistoricalDividend>> mapStockHistoricalDividendFromToDate = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuery.getStockSymbols()) {
					if (map.containsKey(String.valueOf(TAG_DIVIDEND_HISTORY_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockHistoricalDividendFromToDatelist = map
								.get(String.valueOf(TAG_DIVIDEND_HISTORY_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + ticker.toUpperCase()));
						mapStockHistoricalDividendFromToDate.put(ticker.toUpperCase(),
								(List<HistoricalDividend>) stockHistoricalDividendFromToDatelist);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockHistoricalDividendFromToDate);
				} else {

					Map<String, List<HistoricalDividend>> stockHistoricalDividendFromToDatelist = MultipleStockDividendDao
							.fetchMultipleDividendHistoryFromToDate(multipleStockQuery, fromDate, toDate);
					if (stockHistoricalDividendFromToDatelist != null) {
						for (Map.Entry<String, List<HistoricalDividend>> entry : stockHistoricalDividendFromToDatelist
								.entrySet()) {
							if (!map.containsKey(TAG_DIVIDEND_HISTORY_FROMTODATE+ "_" + fromDate + "_" + toDate + "_" + entry.getKey())) {
								stockCacheStorage.add(TAG_DIVIDEND_HISTORY_FROMTODATE+ "_" + fromDate + "_" + toDate + "_" + entry.getKey(),
										entry.getValue());
							}
						}
						subscriber.onSuccess(stockHistoricalDividendFromToDatelist);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock Dividend symbols not found"));
					}
				}

			}
		});
	}

}
