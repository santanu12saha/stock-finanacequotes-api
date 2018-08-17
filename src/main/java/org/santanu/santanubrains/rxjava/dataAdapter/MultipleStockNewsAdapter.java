package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;
import org.santanu.santanubrains.rxjava.domain.news.News;

import io.reactivex.Single;

public interface MultipleStockNewsAdapter {

	Single<Map<String, News[]>> getMultipleStockNewsByTicker(MultipleStockQuery multipleStockQuery);
}
