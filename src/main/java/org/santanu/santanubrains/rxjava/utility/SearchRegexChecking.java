package org.santanu.santanubrains.rxjava.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchRegexChecking {

	private static final String regex = "[a-zA-Z]+$";

	public static boolean check(String searchQuery) {

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(searchQuery);
		return matcher.matches();
	}

}
