package org.santanu.santanubrains.rxjava.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringToCalender {

	private static final String FORMAT = "yyyy-MM-dd";
	private static Date date;

	public static Calendar convert(String stringDate) {

		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		try {
			date = sdf.parse(stringDate);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal;

	}
}
