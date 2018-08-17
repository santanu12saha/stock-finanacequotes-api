package org.santanu.santanubrains.rxjava.main;

import java.util.Arrays;
import java.util.List;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;

import com.google.gson.Gson;

public class SampleMain {

	public static void main(String[] args) {
		
		List<String> symbols = Arrays.asList("GOOG", "ITCkkjkzv", "BABA", "TSLA", "AIR.PA", "YHOO");
		MultipleStockQuery multipleStockQuery = new MultipleStockQuery();
		multipleStockQuery.getStockSymbols().addAll(symbols);
		Gson gson = new Gson();
		String jsonString = gson.toJson(multipleStockQuery);
		System.out.println(jsonString);

	}

}
