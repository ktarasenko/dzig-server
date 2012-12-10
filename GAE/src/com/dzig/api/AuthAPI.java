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

	@Post
	public void handlePost(Representation rep) throws JSONException{
		Form form = new Form(rep);
		doAuth(form.getFirstValue("token"), form.getFirstValue("continueUrl"), form.getFirstValue("method"));
	}
	
	@Get
	public void handleGet(Representation rep){
		doAuth(getQueryValue("token"),getQueryValue("continueUrl"), getQueryValue("method"));
	}
	
	
	public void doAuth(String token, String continueUrl, String method){
		String url;
		if (continueUrl == null){
			continueUrl = getRootRef() + "/user";
		}
		System.out.println(token + " " + continueUrl + " " + method);
		if ("logout".equals(method)){
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
