package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.news.News;

import io.reactivex.Single;

public interface StockNewsAdapter {

	Single<Map<String, News[]>> getStockNewsByTicker(String ticker);
}
