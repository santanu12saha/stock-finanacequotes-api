package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.Map;
import java.util.Set;

import io.reactivex.Single;

public interface FxSymbolsAdapter {

	Single<Map<String, Set<String>>> getFxSymbols();
}
