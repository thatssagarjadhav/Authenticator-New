package com.authenticator.authenticate.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * This Validations class is utility class and is used to do server side Validations in all controller classes.
 * 
 * @author Suman Kumar
 * @version 1.0
 */
@Component
@Scope("prototype")
public class Validations {
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PSRD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
	private static final String ALPHABETS_PATTERN = "/^[A-z]+$/";
	private static final String ALPHABETS_OCCURENCE_PATTERN = "[A-Za-z]";
	private static final String DIGITS_OCCURENCE_PATTERN = "[0-9]";
	private static Pattern pattern;
	private static Matcher matcher;

	public static final String EMPTY_DATA = "-";

	/**
	 * This method is used to check whether the given string is empty or not
	 * 
	 * @param string string is the String to check emptiness of a String
	 * @return boolean representing whether given string is empty or not
	 */
	public static boolean isEmpty(String string) {
		boolean flag = false;
		if (string == null || string.trim().length() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * This method is used to check whether the given emailId is valid or not
	 * 
	 * @param emailId is the String that represents a emailId
	 * @return boolean representing whether given email is valid or not
	 */
	public static boolean isValidEmailId(String emailId) {
		boolean flag = false;
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(emailId);
		flag = matcher.matches();
		return flag;
	}

	/**
	 * This method is used to check whether given passWord is as per client requirement or not
	 * 
	 * @param passWord is the String that represents a Passowrd
	 * @return boolean representing whether given passWord is as per client requirement or not
	 */
	public static boolean isValidPassWord(String passWord) {
		boolean flag = false;
		pattern = Pattern.compile(PSRD_PATTERN);
		matcher = pattern.matcher(passWord);
		flag = matcher.matches();
		return flag;
	}

	/**
	 * This method is used to check whether the given String data has only alphabets or not
	 * 
	 * @param data is the String data that needs to check
	 * @return boolean representing whether given data has only alphabets
	 */
	public static boolean isAlphabetsOnly(String data) {
		boolean flag = false;
		pattern = Pattern.compile(ALPHABETS_PATTERN);
		matcher = pattern.matcher(data);
		flag = matcher.matches();
		return flag;
	}

	public static boolean haveNumberOfAlphabets(int number, String data) {
		boolean flag = false;
		pattern = Pattern.compile(ALPHABETS_OCCURENCE_PATTERN + "{" + number + "}");
		matcher = pattern.matcher(data);
		flag = matcher.matches();
		return flag;
	}

	public static boolean haveNumberOfDigits(int number, String data) {
		boolean flag = false;
		pattern = Pattern.compile(DIGITS_OCCURENCE_PATTERN + "{" + number + "}");
		matcher = pattern.matcher(data);
		flag = matcher.matches();
		return flag;
	}

	/**
	 * This method would trim the input string
	 * 
	 * @param input The input string to trim
	 * @return trim content of the input if not null, else null
	 */
	public static String trimInput(String input) {
		if (input != null) {
			return input.trim();
		}
		return null;
	}

	public boolean compareList(List<Long> list1, List<Long> list2) {
		if (list1 == null && list2 == null) {
			return true;
		}
		if (list1 == null || list2 == null) {
			return false;
		}
		if (list1.size() != list2.size()) {
			return false;
		}
		int count = 0;
		for (Long l1 : list1) {
			if (list2.contains(l1)) {
				count++;
			}
		}
		if (list1.size() == count) {
			return true;
		} else {
			return false;
		}
	}

	public static String convertArrayToString(Long[] eAlertArr) {
		if (eAlertArr != null && eAlertArr.length > 0) {
			StringBuilder eAlert = new StringBuilder();
			for (int i = 0; i < eAlertArr.length; i++) {
				eAlert = eAlert.append(eAlertArr[i]);
				if (i < eAlertArr.length - 1) {
					eAlert = eAlert.append(",");
				}
			}
			return eAlert.toString();
		} else {
			return null;
		}
	}

	public static String convertReturnArrayToString(String[] eAlertArr) {
		if (eAlertArr != null && eAlertArr.length > 0) {
			StringBuilder eAlert = new StringBuilder();
			for (int i = 0; i < eAlertArr.length; i++) {
				eAlert = eAlert.append(eAlertArr[i]);
				if (i < eAlertArr.length - 1) {
					eAlert = eAlert.append(",");
				}
			}
			return eAlert.toString();
		} else {
			return null;
		}
	}

	public static boolean isEmpty(Collection<?> collection) {
		boolean flag = false;
		if (collection == null || collection.isEmpty()) {
			flag = true;
		}
		return flag;
	}

	public static boolean isEmpty(Map<?, ?> map) {
		boolean flag = false;
		if (map == null || map.isEmpty()) {
			flag = true;
		}
		return flag;
	}
}