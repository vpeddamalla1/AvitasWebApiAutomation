package com.avitas.qa.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;

public class JsonParsers {

	public Map<String, String> getMapFromJsonString(String jsonDataSource, String jsonPathKey) {
		setDefaultsForJsonPath();
		Map<String, String> aMap = new HashMap<>();
		DocumentContext jsonContext = JsonPath.parse(readJsonStringFromFile(jsonDataSource));
		aMap = jsonContext.read(jsonPathKey, new TypeRef<Map<String, String>>() {
		});

		return aMap;
	}
	
	public List<String> getListFromJsonString(String jsonDataSource, String jsonPathKey) {
		setDefaultsForJsonPath();
		List<String> aList = new ArrayList<>();
		DocumentContext jsonContext = JsonPath.parse(readJsonStringFromFile(jsonDataSource));
		aList = jsonContext.read(jsonPathKey, new TypeRef<List<String>>() {});

		return aList;
	}

	public String getStringFromJsonString(String jsonDataSource, String jsonPathKey) {
		setDefaultsForJsonPath();
		DocumentContext jsonContext = JsonPath.parse(readJsonStringFromFile(jsonDataSource));
		String aJsonString = jsonContext.read(jsonPathKey, new TypeRef<String>() {});

		return aJsonString;
	}
	
	public List<String> getListFromJsonStringOnly(String jsonStr, String jsonPathKey) {
		setDefaultsForJsonPath();
		List<String> aList = new ArrayList<>();
		DocumentContext jsonContext = JsonPath.parse(jsonStr);
		aList = jsonContext.read(jsonPathKey, new TypeRef<List<String>>() {});

		return aList;
	}

	public List<Map<String, String>> getListOfMapFromJsonString(String jsonDataSource, String jsonPathKey) {
		setDefaultsForJsonPath();
		DocumentContext jsonContext = JsonPath.parse(readJsonStringFromFile(jsonDataSource));
		List<Map<String, String>> aListOfMap = jsonContext.read(jsonPathKey, new TypeRef<List<Map<String, String>>>() {
		});

		return aListOfMap;
	}

	public List<Map<String, Object>> getListOfMapWithStringNObject(String jsonStr, String jsonPathKey) {
		setDefaultsForJsonPath();
		DocumentContext jsonContext = JsonPath.parse(jsonStr);
		List<Map<String, Object>> aListOfMap = jsonContext.read(jsonPathKey, new TypeRef<List<Map<String, Object>>>() {
		});

		return aListOfMap;
	}

	public Map<String, Object> getMapWithStringNObject(String jsonStr, String jsonPathKey) {
		setDefaultsForJsonPath();
		DocumentContext jsonContext = JsonPath.parse(jsonStr);
		Map<String, Object> aMapOfStrNObj = jsonContext.read(jsonPathKey, new TypeRef<Map<String, Object>>() {
		});

		return aMapOfStrNObj;
	}
	
	public String readStringValueFromJson(String jsonStr, String jsonPathKey) {
		setDefaultsForJsonPath();
		DocumentContext jsonContext = JsonPath.parse(jsonStr);
		String aJsonString = jsonContext.read(jsonPathKey, new TypeRef<String>() {});
		return aJsonString;
	}

	private void setDefaultsForJsonPath() {
		Configuration.setDefaults(new Configuration.Defaults() {
			private final JsonProvider jsonProvider = new JacksonJsonProvider();
			private final MappingProvider mappingProvider = new JacksonMappingProvider();

			@Override
			public JsonProvider jsonProvider() {
				return jsonProvider;
			}

			@Override
			public MappingProvider mappingProvider() {
				return mappingProvider;
			}

			@Override
			public Set<Option> options() {
				return EnumSet.noneOf(Option.class);
			}
		});
	}

	public static String readJsonStringFromFile(String fileName) {

		String result = "";
		ClassLoader classLoader = JsonParsers.class.getClassLoader();
		try {
			result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

}
