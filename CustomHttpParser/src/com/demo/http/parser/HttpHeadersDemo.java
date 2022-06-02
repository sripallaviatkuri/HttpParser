package com.demo.http.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

public class HttpHeadersDemo {

	HashMap<String, String> headerNameValues = new HashMap<>();
	int validHeadercount = 0;
	int invalidHeadercount = 0;

	static String HTTP_RESPONSE = "HTTP/1.0 200 OK\n" + "cache-control: public\n" + "content-length: 0\n"
			+ "content-type: image/svg+xml\n" + "date: Tue, 22 Jun 2021 22:24:42 GMT";


	public static void main(String args[]) throws IOException {
		HttpHeadersDemo httpHeadersDemo = new HttpHeadersDemo();
		String result = httpHeadersDemo.ParseHttpResponse(HTTP_RESPONSE);
		System.out.println(result);

	}

	/**
	 * Parse HTTP response headers
	 * 
	 * @param httpResponse - well formatted HTTP response
	 * @throws IOException
	 */
	public String ParseHttpResponse(String httpResponse) throws IOException {

		BufferedReader bufferedReader = new BufferedReader(new StringReader(httpResponse));

		String eachLine = bufferedReader.readLine();

		if (eachLine == null || !validateStatusLine(eachLine))
			return "Invalid status line";

		// Read header line
		eachLine = bufferedReader.readLine();

		while (eachLine != null) {

			if (validateHeaders(eachLine))
				validHeadercount++;
			else
				invalidHeadercount++;

			eachLine = bufferedReader.readLine();
		}

		return constructResult(httpResponse);

	}

	/**
	 * Construct the result in required format
	 * 
	 * @param httpResponse
	 * @return formatted result string
	 */
	private String constructResult(String httpResponse) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("HTTP version: "
				+ httpResponse.substring(httpResponse.indexOf("/") + 1, httpResponse.indexOf("/") + 4));
		stringBuilder.append(System.getProperty("line.separator"));

		stringBuilder.append(
				"Status: " + httpResponse.substring(httpResponse.indexOf("/") + 5, httpResponse.indexOf("/") + 8));
		stringBuilder.append(System.getProperty("line.separator"));

		stringBuilder.append("Number of valid headers: " + validHeadercount);
		stringBuilder.append(System.getProperty("line.separator"));

		stringBuilder.append("Number of invalid headers: " + invalidHeadercount);

		return stringBuilder.toString();
	}

	/**
	 * Validate headers to check for valid header name and value
	 * 
	 * @param headerString
	 * @return isHeaderValid
	 */
	private boolean validateHeaders(String headerString) {

		// Check for invalid headers with no value
		if (headerString == null || headerString.indexOf(":") == -1)
			return false;

		String headerName = headerString.substring(0, headerString.indexOf(":"));

		boolean validHeader = headerName.matches("[a-zA-Z0-9-]+");
		String headerValue = headerString.substring(headerString.indexOf(":") + 1);

		for (char headerValueChar : headerValue.toCharArray())
			if ((int) headerValueChar > 127) {
				validHeader = false;
				break;
			}

		if (validHeader) {
			headerNameValues.put(headerName, headerValue);
		}

		return validHeader;
	}

	/**
	 * Validate if HTTP status line is well formed
	 * 
	 * @param sourceString
	 * @return
	 */
	private boolean validateStatusLine(String sourceString) {
		return sourceString.matches("^(HTTP)/(1|2)\\.\\d \\d{3}(.|\\s)+$");
	}

}
