package org.shanerx.buildtools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Date;

public class Application {

	public static final String URL = "https://github.com/SparklingComet/TradeShop";

	public static void main(String[] args) {
		long millis = System.currentTimeMillis();

		System.out.println("Starting Build: " + new Date().toString());
		Runtime rt = Runtime.getRuntime();
		try {
			output(rt.exec("git clone " + URL));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) { }

		String[] dirPath = URL.split("/");
		String dir = dirPath[dirPath.length - 1];

		try {
			output(rt.exec("mvn -f " + dir));

		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (InterruptedException e) { }

		System.out.println("Done: " + new Date().toString() + " (" + (System.currentTimeMillis() - millis) + "ms)!");
	}

	private static void output(Process p) throws IOException, InterruptedException {

		BufferedReader stdInput = new BufferedReader(new
				InputStreamReader(p.getInputStream()));

		BufferedReader stdError = new BufferedReader(new
				InputStreamReader(p.getErrorStream()));

		String s = null;
		while ((s = stdInput.readLine()) != null) {
			System.out.println(s);
		}

		while ((s = stdError.readLine()) != null) {
			System.out.println(s);
		}

		p.waitFor();
	}
}