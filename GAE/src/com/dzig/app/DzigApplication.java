package com.dzig.app;

import static com.dzig.objectify.OfyService.ofy;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.dzig.api.AuthAPI;
import com.dzig.api.AuthFilter;
import com.dzig.api.CoordinateAPI;
import com.dzig.api.UserAPI;

public class DzigApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {

        Router router = new Router(getContext());
        ofy();
        AuthFilter filter = new AuthFilter();
        filter.setNext(router);
        
        router.attach("/coordinate",CoordinateAPI.class);
        router.attach("/user",UserAPI.class);
        router.attach("/auth",AuthAPI.class);

        return filter;
    }
}
