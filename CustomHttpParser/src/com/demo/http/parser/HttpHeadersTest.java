package com.demo.http.parser;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HttpHeadersTest {

	@Test
	void testParseHttpResponse() {
		HttpHeadersDemo httpHeadersDemoTest = new HttpHeadersDemo();
		String httpResponse = "";

		String parsedResult = null;

		try {
			parsedResult = httpHeadersDemoTest.ParseHttpResponse(httpResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Assertions.assertNotNull(parsedResult);
	}

	@Test
	void testValidHttpResponse200() {
		HttpHeadersDemo httpHeadersDemoTest = new HttpHeadersDemo();

		String httpResponse = "HTTP/1.0 200 OK\n" + "cache-control: public\n" + "content-length: 0\n"
				+ "content-type: image/svg+xml\n" + "date: Tue, 22 Jun 2021 22:24:42 GMT";

		String parsedResult = null;

		try {
			parsedResult = httpHeadersDemoTest.ParseHttpResponse(httpResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String expectedResult = "HTTP version: 1.0\n" + "Status: 200\n" + "Number of valid headers: 4\n"
				+ "Number of invalid headers: 0";

		Assertions.assertNotNull(parsedResult);
		Assertions.assertEquals(expectedResult, parsedResult);

	}

	@Test
	void testValidHttpResponse302() {
		HttpHeadersDemo httpHeadersDemoTest = new HttpHeadersDemo();
		String httpResponse = "HTTP/1.1 302 Found\n" + "cache-control: public\n" + "Transfer-encoding: chunked\n"
				+ "invalid_header\n" + "date: Tue, 22 Jun 2021 22:24:42 GMT";

		String parsedResult = null;

		try {
			parsedResult = httpHeadersDemoTest.ParseHttpResponse(httpResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String expectedResult = "HTTP version: 1.1\n" + "Status: 302\n" + "Number of valid headers: 3\n"
				+ "Number of invalid headers: 1";

		Assertions.assertNotNull(parsedResult);
		Assertions.assertEquals(expectedResult, parsedResult);
	}

	@Test
	void testInvalidHttpResponse() {
		HttpHeadersDemo httpHeadersDemoTest = new HttpHeadersDemo();
		String httpResponse = "Header1: value1\n" + "date: Tue, 22 Jun 2021 22:24:42 GMT\n" + "content-length: 1337";

		String parsedResult = null;

		try {
			parsedResult = httpHeadersDemoTest.ParseHttpResponse(httpResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String expectedResult = "Invalid status line";

		Assertions.assertNotNull(parsedResult);
		Assertions.assertEquals(expectedResult, parsedResult);
	}

}
