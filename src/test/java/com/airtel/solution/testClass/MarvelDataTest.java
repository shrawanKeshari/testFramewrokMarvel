package com.airtel.solution.testClass;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.airtel.solution.assertions.AssertionValidation;
import com.airtel.solution.core.ApiExecutor;
import com.airtel.solution.extentReporting.ExtentTestManager;
import com.airtel.solution.util.DateTimeUtil;
import com.airtel.solution.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class MarvelDataTest {

	AssertionValidation assertionValidation = new AssertionValidation();
	ApiExecutor apiExecutor = new ApiExecutor();
	Response response;
	Map<String, List<String>> characterSeriesMap;

	// Method used for executing the API call
	@BeforeMethod
	public void executeApi() {
		HashMap<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put("Content-Type", ContentType.JSON);

		HashMap<String, Object> queryParamsMap = new HashMap<String, Object>();

		String baseUri = "https://us-central1-airtelb2cfunction.cloudfunctions.net";
		String basePath = "/api";

		response = apiExecutor.get(headerMap, baseUri, basePath, queryParamsMap);
	}

	@SuppressWarnings("unchecked")
	@Test(description = "Get the list of all the characters which have description and get the list of series too")
	public void TC001_GetAllCharactersWithDiscription() {
		ExtentTestManager.startTest("TC001_GetAllCharactersWithDiscription",
				"Get the list of all the characters which have description and get the list of series too");

		assertEquals(response.getStatusCode(), 200);

		Map<String, Object> responseMap = new Gson().fromJson(response.prettyPrint(),
				new TypeToken<HashMap<String, Object>>() {
				}.getType());

		assertionValidation.getAssertion(responseMap);

		characterSeriesMap = new HashMap<String, List<String>>();

		Map<String, Object> dataMap = (Map<String, Object>) responseMap.get("data");
		Map<String, Object> resultMap = (Map<String, Object>) dataMap.get("results");
		for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
			Map<String, Object> currResult = (Map<String, Object>) entry.getValue();
			if (currResult.get("description") != null) {
				String currDescription = String.valueOf(currResult.get("description"));
				if (!currDescription.equals("")) {
					Map<String, Object> seriesMap = (Map<String, Object>) currResult.get("series");
					Map<String, Object> itemsMap = (Map<String, Object>) seriesMap.get("items");
					List<String> seriesList = new ArrayList<String>();
					for (Map.Entry<String, Object> itemsEntry : itemsMap.entrySet()) {
						String seriesName = ((Map<String, String>) itemsEntry.getValue()).get("name");
						seriesList.add(seriesName);
					}
					characterSeriesMap.put(String.valueOf(currResult.get("name")), seriesList);
					ExtentTestManager.getTest().log(LogStatus.INFO,
							"Characters Name : " + String.valueOf(currResult.get("name")),
							"Series List: " + seriesList.toString());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Test(description = "Get the list of stories which does not involve a character with description")
	public void TC002_GetAllStoriesWithNoDiscription() {
		ExtentTestManager.startTest("TC002_GetAllStoriesWithNoDiscription",
				"Get the list of stories which does not involve a character with description");

		assertEquals(response.getStatusCode(), 200);

		Map<String, Object> responseMap = new Gson().fromJson(response.prettyPrint(),
				new TypeToken<HashMap<String, Object>>() {
				}.getType());

		assertionValidation.getAssertion(responseMap);

		characterSeriesMap = new HashMap<String, List<String>>();

		Map<String, Object> dataMap = (Map<String, Object>) responseMap.get("data");
		Map<String, Object> resultMap = (Map<String, Object>) dataMap.get("results");
		for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
			Map<String, Object> currResult = (Map<String, Object>) entry.getValue();
			if (currResult.get("description") != null) {
				String currDescription = String.valueOf(currResult.get("description"));
				if (currDescription.equals("")) {
					Map<String, Object> seriesMap = (Map<String, Object>) currResult.get("series");
					Map<String, Object> itemsMap = (Map<String, Object>) seriesMap.get("items");
					if (itemsMap != null && itemsMap.size() > 0) {
						System.out.println(itemsMap);
						List<String> seriesList = new ArrayList<String>();
						for (Map.Entry<String, Object> itemsEntry : itemsMap.entrySet()) {
							String seriesName = ((Map<String, String>) itemsEntry.getValue()).get("name");
							seriesList.add(seriesName);
						}
						characterSeriesMap.put(String.valueOf(currResult.get("name")), seriesList);
						ExtentTestManager.getTest().log(LogStatus.INFO,
								"Characters Name : " + String.valueOf(currResult.get("name")),
								"Series List: " + seriesList.toString());
					} else {
						characterSeriesMap.put(String.valueOf(currResult.get("name")), null);
						ExtentTestManager.getTest().log(LogStatus.INFO,
								"Characters Name : " + String.valueOf(currResult.get("name")), "Series List: " + null);
					}
				}
			}
		}
	}

	// Method is used to write the character name and series related to it in file
	// after each method
	@AfterMethod
	public void writeToFile() {
		StringBuilder fileOutput = new StringBuilder();
		fileOutput.append("Character Name: ").append("\t").append("Series List: ")
				.append(System.getProperty("line.separator"));
		if (characterSeriesMap != null) {
			for (Map.Entry<String, List<String>> entry : characterSeriesMap.entrySet()) {
				List<String> seriesList = entry.getValue();
				if (seriesList != null) {
					for (String string : seriesList) {
						fileOutput.append(entry.getKey()).append("\t").append(string)
								.append(System.getProperty("line.separator"));
					}
				} else {
					fileOutput.append(entry.getKey()).append("\t").append("No series correspondign to this")
							.append(System.getProperty("line.separator"));
				}
			}
		}

		String formattedDate = DateTimeUtil.getFormattedDateTime("dd-MMM-yyyy_HH-mm-ss.SSS");

		assertNotNull(Util.writeByte(fileOutput.toString().getBytes(),
				".//test-output/logs//CharacterSeriesData" + formattedDate + ".txt"));

		ExtentTestManager.getTest().log(LogStatus.INFO, "File Path", "<a href='file://" + System.getProperty("user.dir")
				+ "/test-output/logs/CharacterSeriesData" + formattedDate + ".txt'>Click Here to open list</a>");
	}
}
