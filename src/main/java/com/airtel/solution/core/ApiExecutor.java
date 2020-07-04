package com.airtel.solution.core;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;

public class ApiExecutor {

	// Function for performing get call request on server
	public Response get(HashMap<String, Object> headerMap, String baseUri, String basePath,
			HashMap<String, Object> queryParamsMap) {
		RestAssured.baseURI = baseUri;
		
		Response response = given()
				.log().all().filter(new RequestLoggingFilter())
				.params(queryParamsMap)
				.headers(headerMap)
				.when().get(basePath)
				.then().log().everything()
				.extract().response();
		
		return response;
	}
}
