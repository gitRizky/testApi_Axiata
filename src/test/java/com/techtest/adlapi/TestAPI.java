package com.techtest.adlapi;

import static org.testng.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestAPI {
	
	String bearerToken = "98fa87eea24a340e3cbedef8cc6ae7cf14db9fc95c0f6cb05cc6e0462ee4d0d9";
	String id = "";
	String link = "https://gorest.co.in/public/v1/users";
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Test(priority = 1)
	public void testPost() {
		
		JSONObject object = new JSONObject();

		object.put("id", "");
		object.put("name", "Test Name");
		object.put("email", "mytestmail@testmail.com");
		object.put("gender", "Male");
		object.put("status", "Inactive");

		RestAssured.baseURI = this.link;
		RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Bearer " + this.bearerToken).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(object.toJSONString());
		Response response = httpRequest.post();
		int statusCode = response.getStatusCode();
		
		assertEquals(201, statusCode); // Assertion when data success created
		
		JsonPath path = new JsonPath(response.asString());
		setId(path.getString("data.id"));
		
	}
	
	@Test(priority = 2)
	public void testGet() {
		RestAssured.baseURI = this.link + "/" + getId();
		RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Bearer " + this.bearerToken);
		Response response = httpRequest.get();
		int statusCode = response.getStatusCode();
		
		assertEquals(200, statusCode); // Assertion when data success viewed
	}
	
	@Test(priority = 3)
	public void testPatch() {
		
		JSONObject object = new JSONObject();

		object.put("gender", "Female");
		object.put("status", "Active");

		RestAssured.baseURI = this.link + "/" + getId();
		RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Bearer " + this.bearerToken).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(object.toJSONString());
		Response response = httpRequest.patch();
		int statusCode = response.getStatusCode();
		
		assertEquals(200, statusCode); // Assertion when data success edited
		
	}
	
	@Test(priority = 4)
	public void testDelete() {
		RestAssured.baseURI = this.link + "/" + getId();
		RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Bearer " + this.bearerToken);
		Response response = httpRequest.delete();
		int statusCode = response.getStatusCode();
		
		assertEquals(204, statusCode); // Assertion when data success deleted
		
	}
	
}
