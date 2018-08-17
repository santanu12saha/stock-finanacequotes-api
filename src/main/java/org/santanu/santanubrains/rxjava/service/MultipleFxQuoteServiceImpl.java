package org.santanu.santanubrains.rxjava.service;

import java.util.Map;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.dataAdapter.MultipleFxQuoteAdapter;
import org.santanu.santanubrains.rxjava.domain.FxMultipleQuoteQuery;

import io.reactivex.Single;
import yahoofinance.quotes.fx.FxQuote;

public class MultipleFxQuoteServiceImpl implements MultipleFxQuoteService {

	private MultipleFxQuoteAdapter multipleFxQuoteAdapter;

	@Inject
	public MultipleFxQuoteServiceImpl(MultipleFxQuoteAdapter multipleFxQuoteAdapter) {
		super();
		this.multipleFxQuoteAdapter = multipleFxQuoteAdapter;
	}

	@Override
	public Single<Map<String, FxQuote>> getMultipleFxQuotesBySymbol(FxMultipleQuoteQuery fxMultipleQuoteQuery) {
	
		return multipleFxQuoteAdapter.getMultipleFxQuotesBySymbols(fxMultipleQuoteQuery);
	}

}
