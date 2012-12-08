package com.dzig.api;

import static com.dzig.objectify.OfyService.ofy;

import java.util.logging.Logger;

import org.json.JSONException;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.dzig.model.Coordinate;
import com.dzig.utils.RestException;
import com.dzig.utils.RestUtils;
import com.googlecode.objectify.cmd.Query;

public class CoordinateAPI extends ServerResource {

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger("API");
	
	
	
	@Get
	public Representation handleGet() throws JSONException {
		String creatorFilter = getQueryValue("creatorId");
		Query<Coordinate> ref = ofy().load().type(Coordinate.class);
		if (creatorFilter != null) {
			ref = ref.filter("creatorId", creatorFilter);
		}
		
		return RestUtils.createResponse(ref.list());
	}


	@Post
	public Representation handlePost(Representation rep) throws JSONException{
		Form form = new Form(rep);
		try {
			Coordinate coord = Coordinate
					.produceFromAttributes(form.getValuesMap());
			ofy().save().entities(coord).now();
			setStatus(Status.SUCCESS_CREATED);
			return RestUtils.createResponse(coord);
		} catch (RestException rex) {
			setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED);
			return RestUtils.createErrorResponse(rex);
		}
	}
}