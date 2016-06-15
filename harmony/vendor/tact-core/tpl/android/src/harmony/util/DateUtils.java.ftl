<@header?interpret />
package ${project_namespace}.harmony.util;

import java.text.ParseException;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

import android.util.Log;

import ${project_namespace}.${project_name?cap_first}Application;

/** Utils for date manipulation. */
public class DateUtils extends android.text.format.DateUtils {
	/** TAG for debug purpose. */
	private static final String TAG = "DateUtils";

	/** Internal pattern for AM PM time. */
	private static final String TIME_AMPM_PATTERN = "hh:mm a";
	/** Internal pattern for H24 time. */
	private static final String TIME_24H_PATTERN = "HH:mm";

	/** Time Format type. */
	public enum TimeFormatType {
		/** 24h format. */
		H24,
		/** AM/PM format. */
		AMPM,
		/** Format defined in user's Android configuration. */
		ANDROID_CONF;
	}


	/**
	 * Convert date to Android string date format.
	 * @param date to convert
	 * @return string date with Android date format
	 */
	public static String formatDateToString(DateTime date) { 
		TimeZone tz = date.getZone().toTimeZone();
		java.text.DateFormat df = ${project_name?cap_first}Application.getDateFormat();
		
		df.setTimeZone(tz);

		return df.format(date.toDate());
	}

	/**
	 * Convert date to Android string time format.
	 * @param time Time to convert
	 * @return string time with Android time format
	 */
	public static String formatTimeToString(DateTime time) {
		String result = null;
		if (${project_name?cap_first}Application.is24Hour()) {
			result = time.toString(
					DateTimeFormat.forPattern(TIME_24H_PATTERN));
		} else {
			result = time.toString(
					DateTimeFormat.forPattern(TIME_AMPM_PATTERN));
		}

		return result;
	}

	/**
	 * Convert date to Android string time format.
	 * @param time to convert
	 * @param formatType The time format
	 * @return string time with Android time format
	 */
	public static String formatTimeToString(
			DateTime time,
			TimeFormatType formatType) {
		String result;
		if (formatType.equals(TimeFormatType.H24)) {
			result =
				time.toString(DateTimeFormat.forPattern(TIME_24H_PATTERN));
		} else if (formatType.equals(TimeFormatType.AMPM)) {
			result =
				time.toString(DateTimeFormat.forPattern(TIME_AMPM_PATTERN));
		} else if (formatType.equals(TimeFormatType.ANDROID_CONF)) {
			result = formatTimeToString(time);
		} else {
			result = null;
		}
		return result;
	}

	/**
	 * Convert datetime to Android string date + time format.
	 * @param date to convert
	 * @return string datetime with Android date + time format
	 */
	public static String formatDateTimeToString(DateTime date) {
		return String.format("%s %s",
				formatDateToString(date),
				formatTimeToString(date));
	}

	/**
	 * Convert Android String date format to datetime.
	 * @param date Android string date format to convert
	 * @return datetime
	 */
	public static DateTime formatStringToDate(String date) {
		return formatStringToDateTime(${project_name?cap_first}Application
							.getDateFormat(), date);
	}

	/**
	 * Convert Android String time format to datetime.
	 * @param time Android string time format to convert
	 * @return datetime
	 */
	public static DateTime formatStringToTime(String time) {
		return formatStringToDateTime(${project_name?cap_first}Application
							.getTimeFormat(), time);
	}

	/**
	 * Convert Android String time format to datetime.
	 * @param time Android string time format to convert
	 * @param formatType The time format type
	 * @return datetime The parsed datetime
	 */
	public static DateTime formatStringToTime(
			String time,
			TimeFormatType formatType) {
		DateTime result;
		if (formatType.equals(TimeFormatType.H24)) {
			result = DateTimeFormat.forPattern(TIME_24H_PATTERN).parseDateTime(
					time);
		} else if (formatType.equals(TimeFormatType.AMPM)) {
			result = DateTimeFormat.forPattern(TIME_AMPM_PATTERN).parseDateTime(
					time);
		} else if (formatType.equals(TimeFormatType.ANDROID_CONF)) {
			result = DateUtils.formatStringToTime(time);
		} else {
			result = null;
		}
		return result;
	}

	/**
	 * Convert Android String date and time format to datetime.
	 * @param date Android string date format to convert
	 * @param time Android string time format to convert
	 * @return datetime
	 */
	public static DateTime formatStringToDateTime(String date, String time) {
		DateTime dt = formatStringToDate(date);
		DateTime t = formatStringToTime(time);

		dt = new DateTime(dt.getYear(),
				dt.getMonthOfYear(),
				dt.getDayOfMonth(),
				t.getHourOfDay(),
				t.getMinuteOfHour());

		return dt;
	}

	/**
	 * Convert Android String date and time format to datetime.
	 * @param date Android string date format to convert
	 * @param time Android string time format to convert
	 * @param timeFormat The time format
	 * @return datetime
	 */
	public static DateTime formatStringToDateTime(String date,
			String time,
			TimeFormatType timeFormat) {

		DateTime dt = formatStringToDate(date);
		DateTime t = formatStringToTime(time, timeFormat);

		dt = new DateTime(dt.getYear(),
				dt.getMonthOfYear(),
				dt.getDayOfMonth(),
				t.getHourOfDay(),
				t.getMinuteOfHour());

		return dt;
	}

	/**
	 * Convert String date with dateformat to datetime.
	 * @param dateFormat date format of datetime
	 * @param dateTime string datetime to convert
	 * @return datetime
	 */
	public static DateTime formatStringToDateTime(
							java.text.DateFormat dateFormat, String dateTime) {
		DateTime dt = null;

		try {
			dt = new DateTime(dateFormat.parse(dateTime).getTime());
		} catch (ParseException e) {
			Log.e(TAG, e.getMessage());
		}

		return dt;
	}

	/**
	 * Convert ISO8601 string date to datetime.
	 * @param dateTime ISO8601 string date
	 * @return datetime
	 */
	public static DateTime formatISOStringToDateTime(String dateTime) {
		DateTime dt = null;

		try {
			dt = new DateTime(
						  ISODateTimeFormat.dateTime().parseDateTime(dateTime));
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
		}

		return dt;
	}

	/**
	 * Convert ISO8601 string localdate to datetime.
	 * @param dateTime ISO8601 string localdate
	 * @return datetime
	 */
	public static DateTime formatLocalISOStringToDateTime(String dateTime) {
		DateTime dt = null;

		try {
			dt = new DateTime(ISODateTimeFormat.localDateOptionalTimeParser()
													  .parseDateTime(dateTime));
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
		}

		return dt;
	}

	/**
	 * Convert a string to a datetime thanks to the given pattern.
	 * @param pattern The datetime pattern (ex. "dd-mm-yyyy" or "yyyy-MM-dd
	 * hh:mm")
	 * @param dateTime date string
	 * @return datetime
	 */
	public static DateTime formatPattern(String pattern, String dateTime) {
		DateTime dt = null;

		try {
			dt = new DateTime(DateTimeFormat.forPattern(pattern)
													  .parseDateTime(dateTime));
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
		}

		return dt;
	}

	/**
	 * Convert a string to a locale datetime thanks to the given pattern.
	 * @param pattern The datetime pattern (ex. "dd-mm-yyyy" or "yyyy-MM-dd
	 * hh:mm")
	 * @param dateTime date string
	 * @return datetime
	 */
	public static DateTime formatLocalPattern(String pattern, String dateTime) {
		DateTime dt = null;

		try {
			LocalDateTime tmp = DateTimeFormat.forPattern(pattern)
												  .parseLocalDateTime(dateTime);
			dt = DateUtils.formatLocalISOStringToDateTime(tmp.toString());
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
		}

		return dt;
	}
}
