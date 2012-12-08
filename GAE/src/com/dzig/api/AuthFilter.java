package com.dzig.api;

import java.util.logging.Logger;

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

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger("API");
	
	private String authRef; 
	
	
	@Override
	protected int beforeHandle(Request request, Response response) {
		if (authRef == null) authRef = request.getRootRef() + "/auth";
		
	    if (authRef.equals(request.getResourceRef().toString())
	    		|| UserServiceFactory.getUserService().isUserLoggedIn()){
	    	return CONTINUE;
	    } else {
			try {
				response.setEntity(RestUtils.createErrorResponse(new RestException(RestUtils.STATUS_FORBIDDEN, "Forbidden")));
			} catch (JSONException e) {
				response.setStatus(Status.CLIENT_ERROR_FORBIDDEN);
				response.setEntity(new StringRepresentation("Forbidden"));
			}
			return STOP;
	    }
	}

}
