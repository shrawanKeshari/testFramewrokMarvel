package com.airtel.solution.assertions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Map;

import com.airtel.solution.util.Util;

public class AssertionValidation {
	
	public void getAssertion(Map<String, Object> responseObjectMap) {
		assertNotNull(String.valueOf(responseObjectMap.get("attributionHTML")));
		// uncomment below for asserting on specific string
		assertEquals(String.valueOf(responseObjectMap.get("attributionHTML")),
				"<a href=\"http://marvel.com\">Data provided by Marvel. © 2020 MARVEL</a>");

		assertNotNull(String.valueOf(responseObjectMap.get("attributionText")));
		// uncomment below for asserting on specific string
		assertEquals(String.valueOf(responseObjectMap.get("attributionText")), "Data provided by Marvel. © 2020 MARVEL");

		assertEquals(Util.getIntVal(responseObjectMap.get("code")), 200);

		assertNotNull(String.valueOf(responseObjectMap.get("copyright")));
		// uncomment below for asserting on specific string
		assertEquals(String.valueOf(responseObjectMap.get("copyright")), "© 2020 MARVEL");

		assertNotNull(String.valueOf(responseObjectMap.get("etag")));
		// uncomment below for asserting on specific string
		assertEquals(String.valueOf(responseObjectMap.get("etag")), "8b5362429cbdb09caa1415deb8b8e2c44a5958b5");

		assertNotNull(String.valueOf(responseObjectMap.get("status")));
		// uncomment below for asserting on specific string
		assertEquals(String.valueOf(responseObjectMap.get("status")), "Ok");

		assertNotNull(String.valueOf(responseObjectMap.get("data")));
		System.out.println(responseObjectMap);
	}
}
