package com.barbearia.service.utils;

public class RegexUtils {
	static String cpfRegex = "([0-9]{2}[\\\\.]?[0-9]{3}[\\\\.]?[0-9]{3}[\\\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\\\.]?[0-9]{3}[\\\\.]?[0-9]{3}[-]?[0-9]{2})";
	static String brPostalRegex = "^[0-9]{8}$";
	static String caPostalRegex = "^[A-Za-z]\\d[A-Za-z][ -]?\\d[A-Za-z]\\d$";
}
