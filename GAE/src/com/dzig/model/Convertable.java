package com.dzig.model;

import org.json.JSONException;
import org.json.JSONObject;


public interface Convertable {

	public JSONObject toJson() throws JSONException;
	
}
