package org.santanu.santanubrains.rxjava.service;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.dataAdapter.FxQuoteAdapter;

import io.reactivex.Single;
import yahoofinance.quotes.fx.FxQuote;

public class FxQuoteServiceImpl implements FxQuoteService {

	private FxQuoteAdapter fxQuoteAdapter;

	@Inject
	public FxQuoteServiceImpl(FxQuoteAdapter fxQuoteAdapter) {
		super();
		this.fxQuoteAdapter = fxQuoteAdapter;
	}

	@Override
	public Single<FxQuote> getFxQuoteBySymbol(String fxSymbol) {

		return fxQuoteAdapter.getFxQuoteBySysmbol(fxSymbol);
	}

}
