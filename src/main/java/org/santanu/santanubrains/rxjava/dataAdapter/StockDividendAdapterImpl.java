package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dao.StockDividendDao;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundException;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockDividend;

public class StockDividendAdapterImpl implements StockDividendAdapter {

	private static final String TAG_DIVIDEND = "DIVIDEND_";
	private static final String TAG_DIVIDEND_HISTORY = "HIS_DIVIDEND_";
	private static final String TAG_DIVIDEND_HISTORY_FROMDATE = "HIS_DIVIDEND_FROM_";
	private static final String TAG_DIVIDEND_HISTORY_FROMTODATE = "HIS_DIVIDEND_FROM_TO";
	private StockCacheStorage stockCacheStorage;

	@Inject
	public StockDividendAdapterImpl(StockCacheStorage stockCacheStorage) {
		super();
		this.stockCacheStorage = stockCacheStorage;
	}

	@Override
	public Single<StockDividend> getStockDividendByTicker(String ticker) {

		return Single.create(new SingleOnSubscribe<StockDividend>() {

			@Override
			public void subscribe(SingleEmitter<StockDividend> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_DIVIDEND + ticker))) {
					Object stockDividend = map.get(String.valueOf(TAG_DIVIDEND + ticker));
					subscriber.onSuccess((StockDividend) stockDividend);
				} else {
					try {

						StockDividend stockDividend = StockDividendDao.fetch(ticker);
						if (stockDividend != null) {
							stockCacheStorage.add(String.valueOf(TAG_DIVIDEND + ticker), stockDividend);
							subscriber.onSuccess(stockDividend);
						} else {
							subscriber.onError(
									new StockSymbolNotFoundException("Stock Dividend symbol " + ticker + " not found"));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	@Override
	public Single<List<HistoricalDividend>> getStockDividendHistoryByTicker(String ticker) {

		return Single.create(new SingleOnSubscribe<List<HistoricalDividend>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<List<HistoricalDividend>> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_DIVIDEND_HISTORY + ticker))) {
					Object stockDividendHistory = map.get(String.valueOf(TAG_DIVIDEND_HISTORY + ticker));
					subscriber.onSuccess((List<HistoricalDividend>) stockDividendHistory);
				} else {
					try {

						List<HistoricalDividend> listDividendHistory = StockDividendDao.fetchDividendHistory(ticker);
						if (listDividendHistory != null) {
							stockCacheStorage.add(String.valueOf(TAG_DIVIDEND_HISTORY + ticker), listDividendHistory);
							subscriber.onSuccess(listDividendHistory);
						} else {
							subscriber.onError(
									new StockSymbolNotFoundException("Stock Dividend symbol " + ticker + " not found"));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}
		});
	}

	@Override
	public Single<List<HistoricalDividend>> getStockHisDivByFromDate(String ticker, String fromDate) {

		return Single.create(new SingleOnSubscribe<List<HistoricalDividend>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<List<HistoricalDividend>> subscriber) throws Exception {
				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String.valueOf(TAG_DIVIDEND_HISTORY_FROMDATE + "_" + fromDate + "_" + ticker))) {
					Object stockDividendHistoryFromDate = map
							.get(String.valueOf(TAG_DIVIDEND_HISTORY_FROMDATE + "_" + fromDate + "_" + ticker));
					subscriber.onSuccess((List<HistoricalDividend>) stockDividendHistoryFromDate);
				} else {
					List<HistoricalDividend> listDividendHistoryFromDate = StockDividendDao
							.fetchDividendHistoryFromDate(ticker, fromDate);
					if (listDividendHistoryFromDate != null) {
						stockCacheStorage.add(
								String.valueOf(TAG_DIVIDEND_HISTORY_FROMDATE + "_" + fromDate + "_" + ticker),
								listDividendHistoryFromDate);
						subscriber.onSuccess(listDividendHistoryFromDate);
					} else {
						subscriber.onError(
								new StockSymbolNotFoundException("Stock Dividend symbol " + ticker + " not found"));
					}
				}

			}
		});
	}

	@Override
	public Single<List<HistoricalDividend>> getStockHisDivByFromToDate(String ticker, String fromDate, String toDate) {

		return Single.create(new SingleOnSubscribe<List<HistoricalDividend>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void subscribe(SingleEmitter<List<HistoricalDividend>> subscriber) throws Exception {

				ConcurrentMap<String, Object> map = stockCacheStorage.getAsMap();
				if (map.containsKey(String
						.valueOf(TAG_DIVIDEND_HISTORY_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + ticker))) {
					Object stockDividendHistoryFromToDate = map
							.get(TAG_DIVIDEND_HISTORY_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + ticker);
					subscriber.onSuccess((List<HistoricalDividend>) stockDividendHistoryFromToDate);
				} else {

					List<HistoricalDividend> stockDividendHistoryFromToDate = StockDividendDao
							.fetchDividendHistoryFromToDate(ticker, fromDate, toDate);
					if (stockDividendHistoryFromToDate != null) {
						stockCacheStorage.add(
								String.valueOf(
										TAG_DIVIDEND_HISTORY_FROMTODATE + "_" + fromDate + "_" + toDate + "_" + ticker),
								stockDividendHistoryFromToDate);
						subscriber.onSuccess(stockDividendHistoryFromToDate);
					} else {
						subscriber.onError(
								new StockSymbolNotFoundException("Stock Dividend symbol " + ticker + " not found"));
					}

				}

			}
		});
	}

}
