package com.dzig.objectify;


import com.dzig.model.Coordinate;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {
	   static {
		   ObjectifyService.factory().register(Coordinate.class);
	    }
	   
	   public static Objectify ofy() {
	        return ObjectifyService.begin();
	    }

	    public static ObjectifyFactory factory() {
	        return ObjectifyService.factory();
	    }
}
