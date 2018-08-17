package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.MultipleStockQuotesDao;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuotesQuery;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

public class MultipleStockQuotesAdapterImpl implements MultipleStockQuotesAdapter {

	private static Boolean foundMap = false;
	private static final String TAG_QUOTES = "QUOTES_";
	private static final String TAG_HISTORICAL_QUOTES = "HIS_QUOTES_";
	private static final String TAG_HISTORICAL_QUOTES_FROMDATE = "HIS_QUOTES_FROMDATE_";
	private static final String TAG_HIS_QUOTES_INTERVAL = "HIS_QUOTES_INTERVAL_";
	private static final String TAG_HIS_QUOTES_FROMTODATE = "HIS_QUOTES_FROMTODATE_";
	private static final String TAG_HIS_QUOTES_FROMINTERVAL = "HIS_QUOTES_FROMINTERVAL_";
	private static final String TAG_HIS_QUOTES_FROMTOINTERVAL = "HIS_QUOTES_FROMTOINTERVAL_";
	private StockCacheStorage stockCacheStorage;

	@Inject
	public MultipleStockQuotesAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<Map<String, StockQuote>> getMultipleStockQuoteByTicker(
			MultipleStockQuotesQuery multipleStockQuotesQuery) {

		return Single.create(new SingleOnSubscribe<Map<String, StockQuote>>() {

			@Override
			public void subscribe(SingleEmitter<Map<String, StockQuote>> subscriber) throws Exception {
				Map<String, StockQuote> mapStockQuote = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuotesQuery.getStockQuoteSymbols()) {
					if (map.containsKey(String.valueOf(TAG_QUOTES + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockQuotes = map.get(String.valueOf(TAG_QUOTES + ticker.toUpperCase()));
						mapStockQuote.put(ticker.toUpperCase(), (StockQuote) stockQuotes);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockQuote);
				} else {
					try {

						Map<String, StockQuote> stockQuotes = MultipleStockQuotesDao.fetch(multipleStockQuotesQuery);
						if (stockQuotes != null) {
							for (Map.Entry<String, StockQuote> entry : stockQuotes.entrySet()) {
								if (!map.containsKey(TAG_QUOTES + entry.getKey())) {
									stockCacheStorage.add(TAG_QUOTES + entry.getKey(), entry.getValue());
								}
							}
							subscriber.onSuccess(stockQuotes);
						} else {
							subscriber.onError(new StockSymbolNotFoundException("Stock Quote symbols not found"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public Single<Map<String, List<HistoricalQuote>>> getMultipleStockHistoricalQuoteByTicker(
			MultipleStockQuotesQuery multipleStockQuotesQuery) {

		return Single.create(new SingleOnSubscribe<Map<String, List<HistoricalQuote>>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<Map<String, List<HistoricalQuote>>> subscriber) throws Exception {

				Map<String, List<HistoricalQuote>> mapStockHistoricalQuote = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuotesQuery.getStockQuoteSymbols()) {
					if (map.containsKey(String.valueOf(TAG_HISTORICAL_QUOTES + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockHistoricalQuotes = map
								.get(String.valueOf(TAG_HISTORICAL_QUOTES + ticker.toUpperCase()));
						mapStockHistoricalQuote.put(ticker.toUpperCase(),
								(List<HistoricalQuote>) stockHistoricalQuotes);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockHistoricalQuote);
				} else {
					try {

						Map<String, List<HistoricalQuote>> stockHistoricalQuotes = MultipleStockQuotesDao
								.fetchMultipleHistoricalQuote(multipleStockQuotesQuery);
						if (stockHistoricalQuotes != null) {
							for (Map.Entry<String, List<HistoricalQuote>> entry : stockHistoricalQuotes.entrySet()) {
								if (!map.containsKey(TAG_HISTORICAL_QUOTES + entry.getKey())) {
									stockCacheStorage.add(TAG_HISTORICAL_QUOTES + entry.getKey(), entry.getValue());
								}
							}
							subscriber.onSuccess(stockHistoricalQuotes);
						} else {
							subscriber.onError(new StockSymbolNotFoundException("Stock Quote symbols not found"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	@Override
	public Single<Map<String, List<HistoricalQuote>>> getMultipleStockHistoricalQuoteByFromDate(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate) {

		return Single.create(new SingleOnSubscribe<Map<String, List<HistoricalQuote>>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<Map<String, List<HistoricalQuote>>> subscriber) throws Exception {

				Map<String, List<HistoricalQuote>> mapStockHistoricalQuoteFromDate = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuotesQuery.getStockQuoteSymbols()) {
					if (map.containsKey(String
							.valueOf(TAG_HISTORICAL_QUOTES_FROMDATE + "_" + fromDate + "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockHistoricalQuotesFromDate = map.get(String
								.valueOf(TAG_HISTORICAL_QUOTES_FROMDATE + "_" + fromDate + "_" + ticker.toUpperCase()));
						mapStockHistoricalQuoteFromDate.put(ticker.toUpperCase(),
								(List<HistoricalQuote>) stockHistoricalQuotesFromDate);

					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockHistoricalQuoteFromDate);
				} else {
					Map<String, List<HistoricalQuote>> stockHistoricalQuotesFromDate = MultipleStockQuotesDao
							.fetchMultipleHistoricalQuoteFromDate(multipleStockQuotesQuery, fromDate);
					if (stockHistoricalQuotesFromDate != null) {
						for (Map.Entry<String, List<HistoricalQuote>> entry : stockHistoricalQuotesFromDate
								.entrySet()) {
							if (!map.containsKey(
									TAG_HISTORICAL_QUOTES_FROMDATE + "_" + fromDate + "_" + entry.getKey())) {
								stockCacheStorage.add(
										TAG_HISTORICAL_QUOTES_FROMDATE + "_" + fromDate + "_" + entry.getKey(),
										entry.getValue());
							}
						}
						subscriber.onSuccess(stockHistoricalQuotesFromDate);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock Quote symbols not found"));
					}
				}

			}
		});
	}

	@Override
	public Single<Map<String, List<HistoricalQuote>>> getMultipleStockHistoricalQuoteByInterval(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String interval) {

		return Single.create(new SingleOnSubscribe<Map<String, List<HistoricalQuote>>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<Map<String, List<HistoricalQuote>>> subscriber) throws Exception {

				Map<String, List<HistoricalQuote>> mapStockHistoricalQuoteInterval = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuotesQuery.getStockQuoteSymbols()) {
					if (map.containsKey(
							String.valueOf(TAG_HIS_QUOTES_INTERVAL + "_" + interval + "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockHistoricalQuoteInterval = map.get(
								String.valueOf(TAG_HIS_QUOTES_INTERVAL + "_" + interval + "_" + ticker.toUpperCase()));
						mapStockHistoricalQuoteInterval.put(ticker.toUpperCase(),
								(List<HistoricalQuote>) stockHistoricalQuoteInterval);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockHistoricalQuoteInterval);
				} else {
					Map<String, List<HistoricalQuote>> stockHistoricalQuotesInterval = MultipleStockQuotesDao
							.fetchMultipleHistoricalQuoteInterval(multipleStockQuotesQuery, interval);
					if (stockHistoricalQuotesInterval != null) {
						for (Map.Entry<String, List<HistoricalQuote>> entry : stockHistoricalQuotesInterval
								.entrySet()) {
							if (!map.containsKey(TAG_HIS_QUOTES_INTERVAL + "_" + interval + "_" + entry.getKey())) {
								stockCacheStorage.add(TAG_HIS_QUOTES_INTERVAL + "_" + interval + "_" + entry.getKey(),
										entry.getValue());
							}
						}
						subscriber.onSuccess(stockHistoricalQuotesInterval);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock Quote symbols not found"));
					}
				}
			}
		});
	}

	@Override
	public Single<Map<String, List<HistoricalQuote>>> getMultipleStockHistoricalQuoteByFromToDate(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate, String toDate) {

		return Single.create(new SingleOnSubscribe<Map<String, List<HistoricalQuote>>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<Map<String, List<HistoricalQuote>>> subscriber) throws Exception {

				Map<String, List<HistoricalQuote>> mapStockHistoricalQuoteFromToDate = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuotesQuery.getStockQuoteSymbols()) {
					if (map.containsKey(String.valueOf(
							TAG_HIS_QUOTES_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockHistoricalQuoteFromToDate = map.get(String.valueOf(TAG_HIS_QUOTES_FROMTODATE + "_"
								+ fromDate + "_" + toDate + "_" + ticker.toUpperCase()));
						mapStockHistoricalQuoteFromToDate.put(ticker.toUpperCase(),
								(List<HistoricalQuote>) stockHistoricalQuoteFromToDate);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockHistoricalQuoteFromToDate);
				} else {
					Map<String, List<HistoricalQuote>> stockHistoricalQuotesFromToDate = MultipleStockQuotesDao
							.fetchMultipleHistoricalQuoteFromToDate(multipleStockQuotesQuery, fromDate, toDate);
					if (stockHistoricalQuotesFromToDate != null) {
						for (Map.Entry<String, List<HistoricalQuote>> entry : stockHistoricalQuotesFromToDate
								.entrySet()) {
							if (!map.containsKey(
									TAG_HIS_QUOTES_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + entry.getKey())) {
								stockCacheStorage.add(TAG_HIS_QUOTES_FROMTODATE + "_" + fromDate + "_" + toDate + "_"
										+ entry.getKey(), entry.getValue());
							}
						}
						subscriber.onSuccess(stockHistoricalQuotesFromToDate);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock Quote symbols not found"));
					}
				}
			}
		});
	}

	@Override
	public Single<Map<String, List<HistoricalQuote>>> getMultipleStockHistoricalQuoteByFromInterval(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate, String interval) {

		return Single.create(new SingleOnSubscribe<Map<String, List<HistoricalQuote>>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<Map<String, List<HistoricalQuote>>> subscriber) throws Exception {

				Map<String, List<HistoricalQuote>> mapStockHistoricalQuoteFromInterval = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuotesQuery.getStockQuoteSymbols()) {
					if (map.containsKey(String.valueOf(TAG_HIS_QUOTES_FROMINTERVAL + "_" + fromDate + "_" + interval
							+ "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockHistoricalQuoteFromInterval = map.get(String.valueOf(TAG_HIS_QUOTES_FROMINTERVAL
								+ "_" + fromDate + "_" + interval + "_" + ticker.toUpperCase()));
						mapStockHistoricalQuoteFromInterval.put(ticker.toUpperCase(),
								(List<HistoricalQuote>) stockHistoricalQuoteFromInterval);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockHistoricalQuoteFromInterval);
				} else {
					Map<String, List<HistoricalQuote>> stockHistoricalQuoteFromInterval = MultipleStockQuotesDao
							.fetchMultipleHistoricalQuoteFromInterval(multipleStockQuotesQuery, fromDate, interval);
					if (stockHistoricalQuoteFromInterval != null) {
						for (Map.Entry<String, List<HistoricalQuote>> entry : stockHistoricalQuoteFromInterval
								.entrySet()) {
							if (!map.containsKey(TAG_HIS_QUOTES_FROMINTERVAL + "_" + fromDate + "_" + interval + "_"
									+ entry.getKey())) {
								stockCacheStorage.add(TAG_HIS_QUOTES_FROMINTERVAL + "_" + fromDate + "_" + interval
										+ "_" + entry.getKey(), entry.getValue());
							}
						}
						subscriber.onSuccess(stockHistoricalQuoteFromInterval);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock Quote symbols not found"));
					}
				}

			}
		});
	}

	@Override
	public Single<Map<String, List<HistoricalQuote>>> getMultipleStockHistoricalQuoteByFromToInterval(
			MultipleStockQuotesQuery multipleStockQuotesQuery, String fromDate, String toDate, String interval) {

		return Single.create(new SingleOnSubscribe<Map<String, List<HistoricalQuote>>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<Map<String, List<HistoricalQuote>>> subscriber) throws Exception {

				Map<String, List<HistoricalQuote>> mapStockHistoricalQuoteFromToInterval = new HashMap<>();
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				for (String ticker : multipleStockQuotesQuery.getStockQuoteSymbols()) {
					if (map.containsKey(String.valueOf(TAG_HIS_QUOTES_FROMTOINTERVAL + "_" + fromDate + "_" + toDate
							+ "_" + interval + "_" + ticker.toUpperCase()))) {
						foundMap = true;
						Object stockHistoricalQuoteFromToInterval = map.get(String.valueOf(TAG_HIS_QUOTES_FROMTOINTERVAL
								+ "_" + fromDate + "_" + toDate + "_" + interval + "_" + ticker.toUpperCase()));
						mapStockHistoricalQuoteFromToInterval.put(ticker.toUpperCase(),
								(List<HistoricalQuote>) stockHistoricalQuoteFromToInterval);
					} else {
						foundMap = false;
						break;
					}
				}
				if (foundMap) {
					subscriber.onSuccess(mapStockHistoricalQuoteFromToInterval);
				} else {
					Map<String, List<HistoricalQuote>> stockHistoricalQuoteFromToInterval = MultipleStockQuotesDao
							.fetchMultipleHistoricalQuoteFromToInterval(multipleStockQuotesQuery, fromDate, toDate,
									interval);
					if (stockHistoricalQuoteFromToInterval != null) {
						for (Map.Entry<String, List<HistoricalQuote>> entry : stockHistoricalQuoteFromToInterval
								.entrySet()) {
							if (!map.containsKey(TAG_HIS_QUOTES_FROMTOINTERVAL + "_" + fromDate + "_" + toDate + "_"
									+ interval + "_" + entry.getKey())) {
								stockCacheStorage.add(TAG_HIS_QUOTES_FROMTOINTERVAL + "_" + fromDate + "_" + toDate
										+ "_" + interval + "_" + entry.getKey(), entry.getValue());
							}
						}
						subscriber.onSuccess(stockHistoricalQuoteFromToInterval);
					} else {
						subscriber.onError(new StockSymbolNotFoundException("Stock Quote symbols not found"));
					}
				}
			}
		});
	}

}
