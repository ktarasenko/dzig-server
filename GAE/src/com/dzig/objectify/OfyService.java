package com.dzig.objectify;


import com.dzig.model.Coordinate;
import com.dzig.model.UserInfo;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {
	   static {
		   ObjectifyService.factory().register(Coordinate.class);
		   ObjectifyService.factory().register(UserInfo.class);
	    }
	   
	   public static Objectify ofy() {
	        return ObjectifyService.ofy();
	    }

	    public static ObjectifyFactory factory() {
	        return ObjectifyService.factory();
	    }
}
