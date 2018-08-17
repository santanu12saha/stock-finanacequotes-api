package org.santanu.santanubrains.rxjava.service;

import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;
import org.santanu.santanubrains.rxjava.domain.news.News;

import io.reactivex.Single;

public interface MultipleStockNewService {

	Single<Map<String, News[]>> getMultipleStockNewsBySymbol(MultipleStockQuery multipleStockQuery);
}
