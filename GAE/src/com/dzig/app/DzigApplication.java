package com.dzig.app;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.dzig.api.AuthFilter;
import com.dzig.api.CoordinateAPI;

public class DzigApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {

        Router router = new Router(getContext());
        
        AuthFilter filter = new AuthFilter();
        filter.setNext(router);
        
        router.attach("/coordinate",CoordinateAPI.class);

        return filter;
    }
}
