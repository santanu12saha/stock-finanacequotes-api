package org.santanu.santanubrains.rxjava.service;

import java.util.Map;
import java.util.Set;

import io.reactivex.Single;

public interface FxSymbolService {
	
	Single<Map<String, Set<String>>> getFxSymbols();
}
