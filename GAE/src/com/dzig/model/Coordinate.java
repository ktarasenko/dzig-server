package com.dzig.model;

import java.util.Date;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.dzig.api.AuthFilter;
import com.dzig.utils.RestException;
import com.dzig.utils.RestUtils;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Coordinate implements Convertable{
	@Id
	private Long id;
	private double lat;
	private double lon;
	private double accuracy;
	private Date date;
	private String creatorId;

	public Coordinate() {
	}
	
	

	public Coordinate(double lat, double lon, double accuracy, String creatorId) {
		this.lat = lat;
		this.lon = lon;
		this.accuracy = accuracy;
		this.creatorId = creatorId;
		this.date = new Date();
	}
	
	
	public static Coordinate produceFromAttributes(Map<String, String> map) throws RestException{
		return  new Coordinate(RestUtils.getDouble(map, "lat"),
		RestUtils.getDouble(map, "lon"),
		RestUtils.getDouble(map, "accuracy"),
		AuthFilter.DEBUG_TURN_OFF_AUTH ? "100500": 
				UserServiceFactory.getUserService().getCurrentUser().getUserId());
	}


	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("id", String.valueOf(id));
		json.put("lat", RestUtils.formatDouble(lat));
		json.put("lon", RestUtils.formatDouble(lon));
		json.put("creatorId", String.valueOf(creatorId));
		json.put("accuracy",RestUtils.formatDouble(accuracy));
		json.put("date", RestUtils.formatDate(date));
		return json;
	}

}
