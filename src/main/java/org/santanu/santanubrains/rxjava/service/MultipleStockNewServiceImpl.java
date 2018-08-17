package org.santanu.santanubrains.rxjava.service;

import java.util.Map;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockNewsAdapter;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;
import org.santanu.santanubrains.rxjava.domain.news.News;

import io.reactivex.Single;

public class MultipleStockNewServiceImpl implements MultipleStockNewService {

	private MultipleStockNewsAdapter multipleStockNewsAdapter;

	@Inject
	public MultipleStockNewServiceImpl(MultipleStockNewsAdapter multipleStockNewsAdapter) {
		super();
		this.multipleStockNewsAdapter = multipleStockNewsAdapter;
	}

	@Override
	public Single<Map<String, News[]>> getMultipleStockNewsBySymbol(MultipleStockQuery multipleStockQuery) {

		return multipleStockNewsAdapter.getMultipleStockNewsByTicker(multipleStockQuery);
	}

}
