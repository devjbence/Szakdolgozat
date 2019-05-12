package com.szakdoga.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Random;

import javax.imageio.ImageIO;

import com.szakdoga.exceptions.BadYearException;

public class Utils {

	public static int MAX_IMAGEFILE_SIZE = 64000;

	public static byte[] getDefaultImage() {
		URL resource = Utils.class.getResource("/images/defaultImage.jpg");
		File defaultFile = null;
		try {
			defaultFile = Paths.get(resource.toURI()).toFile();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
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

	public static int random(int from, int to) {
		Random rand = new Random();
		int n = from + rand.nextInt(to - from);

		return n;
	}

	public static byte[] getImageWithText(String text) {
		// create String object to be converted to image
		String sampleText = text;

		// create the font you wish to use
		Font font = new Font("Tahoma", Font.PLAIN, 11);

		// create the FontRenderContext object which helps us to measure the text
		FontRenderContext frc = new FontRenderContext(null, true, true);

		// get the height and width of the text
		Rectangle2D bounds = font.getStringBounds(sampleText, frc);
		int w = (int) bounds.getWidth();
		int h = (int) bounds.getHeight();

		// create a BufferedImage object
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		// calling createGraphics() to get the Graphics2D
		Graphics2D g = image.createGraphics();

		// set color and other parameters
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, h);
		g.setColor(Color.BLACK);
		g.setFont(font);

		g.drawString(sampleText, (float) bounds.getX(), (float) -bounds.getY());

		// releasing resources
		g.dispose();

		return ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	}
}
