package org.santanu.santanubrains.rxjava.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import yahoofinance.quotes.fx.FxSymbols;

public class FxSymbolDao {

	public static Map<String, Set<String>> fetchFxSymbol() {

		Map<String, Set<String>> fxSymbols = new HashMap<>();

		Set<String> fxSymbolList = new HashSet<>();

		fxSymbolList.add(FxSymbols.USDGBP);
		fxSymbolList.add(FxSymbols.USDEUR);
		fxSymbolList.add(FxSymbols.USDAUD);
		fxSymbolList.add(FxSymbols.USDCHF);
		fxSymbolList.add(FxSymbols.USDJPY);
		fxSymbolList.add(FxSymbols.USDCAD);
		fxSymbolList.add(FxSymbols.USDSGD);
		fxSymbolList.add(FxSymbols.USDNZD);
		fxSymbolList.add(FxSymbols.USDHKD);

		fxSymbolList.add(FxSymbols.GBPUSD);
		fxSymbolList.add(FxSymbols.GBPEUR);
		fxSymbolList.add(FxSymbols.GBPAUD);
		fxSymbolList.add(FxSymbols.GBPCHF);
		fxSymbolList.add(FxSymbols.GBPJPY);
		fxSymbolList.add(FxSymbols.GBPCAD);
		fxSymbolList.add(FxSymbols.GBPSGD);
		fxSymbolList.add(FxSymbols.GBPNZD);
		fxSymbolList.add(FxSymbols.GBPHKD);

		fxSymbolList.add(FxSymbols.AUDUSD);
		fxSymbolList.add(FxSymbols.AUDGBP);
		fxSymbolList.add(FxSymbols.AUDEUR);
		fxSymbolList.add(FxSymbols.AUDCHF);
		fxSymbolList.add(FxSymbols.AUDJPY);
		fxSymbolList.add(FxSymbols.AUDCAD);
		fxSymbolList.add(FxSymbols.AUDSGD);
		fxSymbolList.add(FxSymbols.AUDNZD);
		fxSymbolList.add(FxSymbols.AUDHKD);

		fxSymbolList.add(FxSymbols.CHFGBP);
		fxSymbolList.add(FxSymbols.CHFEUR);
		fxSymbolList.add(FxSymbols.CHFAUD);
		fxSymbolList.add(FxSymbols.CHFJPY);
		fxSymbolList.add(FxSymbols.CHFCAD);
		fxSymbolList.add(FxSymbols.CHFSGD);
		fxSymbolList.add(FxSymbols.CHFNZD);
		fxSymbolList.add(FxSymbols.CHFHKD);

		fxSymbolList.add(FxSymbols.JPYUSD);
		fxSymbolList.add(FxSymbols.JPYGBP);
		fxSymbolList.add(FxSymbols.JPYEUR);
		fxSymbolList.add(FxSymbols.JPYAUD);
		fxSymbolList.add(FxSymbols.JPYCHF);
		fxSymbolList.add(FxSymbols.JPYCAD);
		fxSymbolList.add(FxSymbols.JPYSGD);
		fxSymbolList.add(FxSymbols.JPYNZD);
		fxSymbolList.add(FxSymbols.JPYHKD);

		fxSymbolList.add(FxSymbols.CADUSD);
		fxSymbolList.add(FxSymbols.CADGBP);
		fxSymbolList.add(FxSymbols.CADEUR);
		fxSymbolList.add(FxSymbols.CADAUD);
		fxSymbolList.add(FxSymbols.CADCHF);
		fxSymbolList.add(FxSymbols.CADJPY);
		fxSymbolList.add(FxSymbols.CADSGD);
		fxSymbolList.add(FxSymbols.CADNZD);
		fxSymbolList.add(FxSymbols.CADHKD);

		fxSymbolList.add(FxSymbols.SGDUSD);
		fxSymbolList.add(FxSymbols.SGDGBP);
		fxSymbolList.add(FxSymbols.SGDEUR);
		fxSymbolList.add(FxSymbols.SGDAUD);
		fxSymbolList.add(FxSymbols.SGDCHF);
		fxSymbolList.add(FxSymbols.SGDJPY);
		fxSymbolList.add(FxSymbols.SGDCAD);
		fxSymbolList.add(FxSymbols.SGDNZD);
		fxSymbolList.add(FxSymbols.SGDHKD);

		fxSymbolList.add(FxSymbols.NZDUSD);
		fxSymbolList.add(FxSymbols.NZDGBP);
		fxSymbolList.add(FxSymbols.NZDEUR);
		fxSymbolList.add(FxSymbols.NZDAUD);
		fxSymbolList.add(FxSymbols.NZDCHF);
		fxSymbolList.add(FxSymbols.NZDJPY);
		fxSymbolList.add(FxSymbols.NZDCAD);
		fxSymbolList.add(FxSymbols.NZDSGD);
		fxSymbolList.add(FxSymbols.NZDHKD);

		fxSymbolList.add(FxSymbols.HKDUSD);
		fxSymbolList.add(FxSymbols.HKDGBP);
		fxSymbolList.add(FxSymbols.HKDEUR);
		fxSymbolList.add(FxSymbols.HKDAUD);
		fxSymbolList.add(FxSymbols.HKDCHF);
		fxSymbolList.add(FxSymbols.HKDJPY);
		fxSymbolList.add(FxSymbols.HKDCAD);
		fxSymbolList.add(FxSymbols.HKDSGD);
		fxSymbolList.add(FxSymbols.HKDNZD);

		fxSymbols.put("fxSymbols", fxSymbolList);
		
		return fxSymbols;

	}
}
