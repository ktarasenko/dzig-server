package com.dzig.api;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.dzig.model.Coordinate;
import com.dzig.objectify.OfyService;


public class CoordinateServerResource extends ServerResource {

	@Get("json")
	public String handleGet() {
		try {
			JSONObject json = new JSONObject();
			json.put("name", "value");
			OfyService.ofy().save().entities(new Coordinate()).now();
			OfyService.ofy().load().type(Coordinate.class).first();
			JsonRepresentation jsonRep = new JsonRepresentation(json);

			return jsonRep.getText();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}