package com.szakdoga.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.szakdoga.exceptions.BadYearException;

public class Utils {

	public static int MAX_IMAGEFILE_SIZE=64000;
	
	public static byte[] getDefaultProfileImage() {
		File defaultFile = new File("src/main/resources/images/defaultProfileImage.jpg");
		InputStream targetStream = null;
		try {
			targetStream = new FileInputStream(defaultFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		byte[] targetArray = null;
		try {
			targetArray = new byte[targetStream.available()];
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			targetStream.read(targetArray);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return targetArray;
	}

	public static String randomString(int n) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < n; i++) {
			sb.append(randomChar());
		}

		return sb.toString();
	}

	public static char randomChar() {
		String from = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		Random rand = new Random();
		int n = rand.nextInt(from.length());

		return from.charAt(n);
	}

	// https://stackoverflow.com/a/1306751
	public static void checkTheNumberPositive4DigitLong(int n) {
		if (n < 0)
			throw new BadYearException("The year cannot be less than zero");
		int length = (int) (Math.log10(n) + 1);
		if (length != 4)
			throw new BadYearException("The given year is not 4 digit long!");
	}

}
