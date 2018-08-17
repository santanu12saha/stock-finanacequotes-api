package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.List;
import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;

import io.reactivex.Single;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockDividend;

public interface MultipleStockDividendAdapter {

	Single<Map<String, StockDividend>> getMultipleStockDividendByTicker(MultipleStockQuery multipleStockQuery);

	Single<Map<String, List<HistoricalDividend>>> getMultipleStockHistoricalDividendByTicker(
			MultipleStockQuery multipleStockQuery);

	Single<Map<String, List<HistoricalDividend>>> getMultipleStockHisDivByFromDate(
			MultipleStockQuery multipleStockQuery, String fromDate);

	Single<Map<String, List<HistoricalDividend>>> getMultipleStockHisDivByFromToDate(
			MultipleStockQuery multipleStockQuery, String fromDate, String toDate);
}
