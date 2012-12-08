package com.dzig.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

import com.dzig.model.Convertable;

public class RestUtils {

	public static final int STATUS_OK = 200;
	public static final int STATUS_CREATED = 201;
	public static final int  STATUS_UNPROCESSABLE = 422;
	public static final int STATUS_FORBIDDEN = 403;
	
	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	private static NumberFormat numberFormat = new DecimalFormat("#.#####");

	
	public static String formatDate(Date date){
		if (date != null){
			return format.format(date);
		} else return "null";
	}
	
	public static Date parseDate(String date) throws ParseException{
		return format.parse(date);
	}
	
	
	public static String formatDouble(double dbl){
		return numberFormat.format(dbl);
	}
	
	public static Representation produceResponse(Iterable<? extends Convertable> data) throws JSONException{
		JSONObject json = new JSONObject();
		for (Convertable item: data){
			json.accumulate("data", item.toJson());
		}
		return produceResponse(json, generateMeta(STATUS_OK));
	}
	
	public static Representation produceResponse(Convertable data) throws JSONException{
		JSONObject json = new JSONObject();
		json.put("data", data.toJson());
		return produceResponse(json, generateMeta(STATUS_OK));
	}
	
	private static Representation produceResponse(JSONObject json, JSONObject meta) throws JSONException{
		json.put("meta", meta);
		JsonRepresentation jsonRep = new JsonRepresentation(json);
		jsonRep.setIndenting(true);
		return jsonRep;
	}
	
	private static JSONObject generateMeta(int status) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("status", status);
		json.put("asOf", formatDate(new Date()));
		return json;
	}

	public static Representation produceErrorResponce(RestException rex) throws JSONException{
		JSONObject meta = generateMeta(rex.getStatusCode());
		meta.put("error", rex.getMessage());
		return produceResponse(new JSONObject(), meta);
	}

	public static double getDouble(Map<String, String> map, String key) throws RestException {	
		try{
			return Double.parseDouble(getString(map, key));
			} catch (RuntimeException rex){
				//do nothing 
			}
		throw new RestException(RestUtils.STATUS_UNPROCESSABLE,"Unable to parse parameter :" + key);
	}

	public static Date getDate(Map<String, String> map, String key) throws RestException {
		try {
			return parseDate(getString(map, key));
		} catch (ParseException e) {
			//do nothing
		}
		throw new RestException(RestUtils.STATUS_UNPROCESSABLE, "Unable to parse parameter :" + key);
	}

	public static String getString(Map<String, String> map, String key) throws RestException {
		if (map.containsKey(key)){
			return (String) map.get(key);
		}
		throw new RestException(RestUtils.STATUS_UNPROCESSABLE, "Unable to parse parameter :" + key);
	}
	
}
