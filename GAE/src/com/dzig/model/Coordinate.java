package com.dzig.model;

import java.util.Date;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.dzig.api.AuthFilter;
import com.dzig.utils.RestException;
import com.dzig.utils.RestUtils;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

@Entity
public class Coordinate implements Convertable{
	@Id
	private Long id;
	private double lat;
	private double lon;
	private double accuracy;
	private Date date;
	@Load private Ref<UserInfo> creator;

	public Coordinate() {
	}
	
	

	public Coordinate(double lat, double lon, double accuracy, UserInfo creator) {
		this.lat = lat;
		this.lon = lon;
		this.accuracy = accuracy;
		this.creator = Ref.create(creator);
		this.date = new Date();
	}
	
	
	public static Coordinate produceFromAttributes(Map<String, String> map) throws RestException{
		return  new Coordinate(RestUtils.getDouble(map, "lat"),
		RestUtils.getDouble(map, "lon"),
		RestUtils.getDouble(map, "accuracy"),
		UserInfo.create(UserServiceFactory.getUserService().getCurrentUser()));
	}


	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("id", String.valueOf(id));
		json.put("lat", RestUtils.formatDouble(lat));
		json.put("lon", RestUtils.formatDouble(lon));
		json.put("creator", creator.get().toJson());
		json.put("accuracy",RestUtils.formatDouble(accuracy));
		json.put("date", RestUtils.formatDate(date));
		return json;
	}

}
