package com.dzig.api;

import java.util.logging.Logger;

import org.json.JSONException;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.users.UserServiceFactory;

public class AuthAPI extends ServerResource {

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger("API");
	
	@Get
	@Post
	public void handlePost(Representation rep) throws JSONException{
		Form form = new Form(rep);
		String url;
		String token = form.getFirstValue("token");
		String continueUrl = form.getFirstValue("continueUrl", getRootRef() + "/user");
		if ("logout".equals(form.getFirstValue("method"))){
			url = UserServiceFactory.getUserService().createLogoutURL(continueUrl);
		} else {
			if (token != null){
				url = String.format("/_ah/login?auth=%s&continue=%s", token, continueUrl);
			} else {
				url = UserServiceFactory.getUserService().createLoginURL(continueUrl);
			}
		}
		redirectPermanent(url);
	}

}
