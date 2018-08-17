package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import javax.inject.Inject;
import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.StockQuoteDao;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

public class StockQuotesAdapterImpl implements StockQuotesAdapter {

	private static final String TAG_QUOTES = "QUOTES_";
	private static final String TAG_HISTORICAL_QUOTES = "HIS_QUOTES_";
	private static final String TAG_HISTORICAL_QUOTES_FROMDATE = "HIS_QUOTES_FROMDATE_";
	private static final String TAG_HIS_QUOTES_INTERVAL = "HIS_QUOTES_INTERVAL_";
	private static final String TAG_HIS_QUOTES_FROMTODATE = "HIS_QUOTES_FROMTODATE_";
	private static final String TAG_HIS_QUOTES_FROMINTERVAL = "HIS_QUOTES_FROMINTERVAL_";
	private static final String TAG_HIS_QUOTES_FROMTOINTERVAL = "HIS_QUOTES_FROMTOINTERVAL_";
	private StockCacheStorage stockCacheStorage;

	@Inject
	public StockQuotesAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<StockQuote> getStockQuoteByTicker(String ticker) {

		return Single.create(new SingleOnSubscribe<StockQuote>() {

			@Override
			public void subscribe(SingleEmitter<StockQuote> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_QUOTES + ticker))) {
					Object stockQuote = map.get(String.valueOf(TAG_QUOTES + ticker));
					subscriber.onSuccess((StockQuote) stockQuote);
				} else {
					try {
						StockQuote stockQuote = StockQuoteDao.fetch(ticker);
						if (stockQuote != null) {
							stockCacheStorage.add(String.valueOf(TAG_QUOTES + ticker), stockQuote);
							subscriber.onSuccess(stockQuote);
						} else {
							subscriber.onError(
									new StockSymbolNotFoundException("Stock Quote symbol " + ticker + " not found"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	@Override
	public Single<List<HistoricalQuote>> getStockHistoricalQuoteByTicker(String ticker) {

		return Single.create(new SingleOnSubscribe<List<HistoricalQuote>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<List<HistoricalQuote>> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_HISTORICAL_QUOTES + ticker))) {
					Object stockHistoricalQuote = map.get(String.valueOf(TAG_HISTORICAL_QUOTES + ticker));
					subscriber.onSuccess((List<HistoricalQuote>) stockHistoricalQuote);
				} else {
					try {
						List<HistoricalQuote> stockHistoricalQuote = StockQuoteDao.fetchHistoricalQuote(ticker);
						if (stockHistoricalQuote != null) {
							stockCacheStorage.add(TAG_HISTORICAL_QUOTES + ticker, stockHistoricalQuote);
							subscriber.onSuccess(stockHistoricalQuote);
						} else {
							subscriber.onError(
									new StockSymbolNotFoundException("Stock Quote symbol " + ticker + " not found"));
						}

					} catch (Exception e) {

					}
				}

			}
		});
	}

	@Override
	public Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromDate(String ticker, String fromDate) {

		return Single.create(new SingleOnSubscribe<List<HistoricalQuote>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<List<HistoricalQuote>> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_HISTORICAL_QUOTES_FROMDATE + "_" + fromDate + "_" + ticker))) {
					Object stockHistoricalQuoteFromDate = map
							.get(TAG_HISTORICAL_QUOTES_FROMDATE + "_" + fromDate + "_" + ticker);
					subscriber.onSuccess((List<HistoricalQuote>) stockHistoricalQuoteFromDate);
				} else {
					List<HistoricalQuote> stockHistoricalQuoteFromDate = StockQuoteDao
							.fetchHistoricalQuoteFromDate(ticker, fromDate);
					if (stockHistoricalQuoteFromDate != null) {
						stockCacheStorage.add(TAG_HISTORICAL_QUOTES_FROMDATE + "_" + fromDate + "_" + ticker,
								stockHistoricalQuoteFromDate);
						subscriber.onSuccess(stockHistoricalQuoteFromDate);
					} else {
						subscriber.onError(
								new StockSymbolNotFoundException("Stock Quote symbol " + ticker + " not found"));
					}
				}
			}
		});
	}

	@Override
	public Single<List<HistoricalQuote>> getStockHistoricalQuoteByInterval(String ticker, String interval) {

		return Single.create(new SingleOnSubscribe<List<HistoricalQuote>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<List<HistoricalQuote>> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_HIS_QUOTES_INTERVAL + "_" + interval + "_" + ticker))) {
					Object stockHistoricalQuoteInterval = map
							.get(String.valueOf(TAG_HIS_QUOTES_INTERVAL + "_" + interval + "_" + ticker));
					subscriber.onSuccess((List<HistoricalQuote>) stockHistoricalQuoteInterval);
				} else {
					List<HistoricalQuote> stockHistoricalQuoteInterval = StockQuoteDao
							.fetchHistoricalQuoteInterval(ticker, interval);
					if (stockHistoricalQuoteInterval != null) {
						stockCacheStorage.add(TAG_HIS_QUOTES_INTERVAL + "_" + interval + "_" + ticker,
								stockHistoricalQuoteInterval);
						subscriber.onSuccess(stockHistoricalQuoteInterval);
					} else {
						subscriber.onError(
								new StockSymbolNotFoundException("Stock Quote symbol " + ticker + " not found"));
					}
				}
			}
		});
	}

	@Override
	public Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromToDate(String ticker, String fromDate,
			String toDate) {

		return Single.create(new SingleOnSubscribe<List<HistoricalQuote>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<List<HistoricalQuote>> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(
						String.valueOf(TAG_HIS_QUOTES_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + ticker))) {
					Object stockHistoricalQuoteFromToDate = map.get(
							String.valueOf(TAG_HIS_QUOTES_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + ticker));
					subscriber.onSuccess((List<HistoricalQuote>) stockHistoricalQuoteFromToDate);

				} else {
					List<HistoricalQuote> stockHistoricalQuoteFromToDate = StockQuoteDao
							.fetchHistoricalQuoteFromToDate(ticker, fromDate, toDate);
					if (stockHistoricalQuoteFromToDate != null) {
						stockCacheStorage.add(TAG_HIS_QUOTES_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + ticker,
								stockHistoricalQuoteFromToDate);
						subscriber.onSuccess(stockHistoricalQuoteFromToDate);
					} else {
						subscriber.onError(
								new StockSymbolNotFoundException("Stock Quote symbol " + ticker + " not found"));
					}
				}

			}
		});
	}

	@Override
	public Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromInterval(String ticker, String fromDate,
			String interval) {

		return Single.create(new SingleOnSubscribe<List<HistoricalQuote>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<List<HistoricalQuote>> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(
						String.valueOf(TAG_HIS_QUOTES_FROMINTERVAL + "_" + fromDate + "_" + interval + "_" + ticker))) {
					Object stockHistoricalQuoteFromInterval = map.get(String
							.valueOf(TAG_HIS_QUOTES_FROMINTERVAL + "_" + fromDate + "_" + interval + "_" + ticker));
					subscriber.onSuccess((List<HistoricalQuote>) stockHistoricalQuoteFromInterval);
				} else {
					List<HistoricalQuote> stockHistoricalQuoteFromInterval = StockQuoteDao
							.fetchHistoricalQuoteFromInterval(ticker, fromDate, interval);
					if (stockHistoricalQuoteFromInterval != null) {
						stockCacheStorage.add(
								TAG_HIS_QUOTES_FROMINTERVAL + "_" + fromDate + "_" + interval + "_" + ticker,
								stockHistoricalQuoteFromInterval);
						subscriber.onSuccess(stockHistoricalQuoteFromInterval);
					} else {
						subscriber.onError(
								new StockSymbolNotFoundException("Stock Quote symbol " + ticker + " not found"));
					}
				}

			}
		});
	}

	@Override
	public Single<List<HistoricalQuote>> getStockHistoricalQuoteByFromToInterval(String ticker, String fromDate,
			String toDate, String interval) {

		return Single.create(new SingleOnSubscribe<List<HistoricalQuote>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<List<HistoricalQuote>> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_HIS_QUOTES_FROMTOINTERVAL + "_" + fromDate + "_" + toDate + "_"
						+ interval + "_" + ticker))) {
					Object stockHistoricalQuoteFromToInterval = map.get(String.valueOf(TAG_HIS_QUOTES_FROMTOINTERVAL
							+ "_" + fromDate + "_" + toDate + "_" + interval + "_" + ticker));
					subscriber.onSuccess((List<HistoricalQuote>) stockHistoricalQuoteFromToInterval);
				} else {
					List<HistoricalQuote> stockHistoricalQuoteFromToInterval = StockQuoteDao
							.fetchHistoricalQuoteFromToInterval(ticker, fromDate, toDate, interval);
					if (stockHistoricalQuoteFromToInterval != null) {
						stockCacheStorage.add(TAG_HIS_QUOTES_FROMTOINTERVAL + "_" + fromDate + "_" + toDate + "_"
								+ interval + "_" + ticker, stockHistoricalQuoteFromToInterval);
						subscriber.onSuccess(stockHistoricalQuoteFromToInterval);
					} else {
						subscriber.onError(
								new StockSymbolNotFoundException("Stock Quote symbol " + ticker + " not found"));
					}
				}
			}
		});
	}

}
