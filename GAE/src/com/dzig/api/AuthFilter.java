package com.dzig.api;

import org.json.JSONException;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.representation.StringRepresentation;
import org.restlet.routing.Filter;

import com.dzig.utils.RestException;
import com.dzig.utils.RestUtils;
import com.google.appengine.api.users.UserServiceFactory;

public class AuthFilter extends Filter {

	@Override
	protected int beforeHandle(Request request, Response response) {
	    if (UserServiceFactory.getUserService().isUserLoggedIn()){
	    	return CONTINUE;
	    } else {
			try {
				response.setEntity(RestUtils.produceErrorResponce(new RestException(RestUtils.STATUS_FORBIDDEN, "Forbidden")));
			} catch (JSONException e) {
				response.setStatus(Status.CLIENT_ERROR_FORBIDDEN);
				response.setEntity(new StringRepresentation("Forbidden"));
			}
			return STOP;
	    }
	}

}
