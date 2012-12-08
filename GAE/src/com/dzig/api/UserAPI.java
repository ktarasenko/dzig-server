package com.dzig.api;

import java.util.logging.Logger;

import org.json.JSONException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.dzig.model.UserInfo;
import com.dzig.utils.RestUtils;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class UserAPI extends ServerResource {

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger("API");
	
	@Get
	public Representation handleGet() throws JSONException{
		User user = UserServiceFactory.getUserService().getCurrentUser();
		return RestUtils.createResponse(UserInfo.create(user));
	}
	
}
