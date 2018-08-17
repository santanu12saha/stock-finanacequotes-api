package org.santanu.santanubrains.rxjava.service;

import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.news.News;

import io.reactivex.Single;

public interface StockNewService {

	Single<Map<String, News[]>> getStockNewsBySymbol(String symbol);
}
