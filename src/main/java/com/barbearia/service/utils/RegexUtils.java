package com.barbearia.service.utils;

public class RegexUtils {
	protected static String cpfRegex = "([0-9]{2}[\\\\.]?[0-9]{3}[\\\\.]?[0-9]{3}[\\\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\\\.]?[0-9]{3}[\\\\.]?[0-9]{3}[-]?[0-9]{2})";
	protected static String brPostalRegex = "^[0-9]{8}$";
	protected static String caPostalRegex = "^[A-Za-z]\\d[A-Za-z][ -]?\\d[A-Za-z]\\d$";
	protected static String digitoNumericoRegex = ".*\\d.*";
	
}
