package org.santanu.santanubrains.rxjava.service;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import org.santanu.santanubrains.rxjava.dataAdapter.FxSymbolsAdapter;
import io.reactivex.Single;

public class FxSymbolServiceImpl implements FxSymbolService {

	private FxSymbolsAdapter fxSymbolsAdapter;

	@Inject
	public FxSymbolServiceImpl(FxSymbolsAdapter fxSymbolsAdapter) {
		super();
		this.fxSymbolsAdapter = fxSymbolsAdapter;
	}

	@Override
	public Single<Map<String, Set<String>>> getFxSymbols() {

		return fxSymbolsAdapter.getFxSymbols();
	}

}
