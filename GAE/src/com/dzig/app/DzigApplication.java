package com.dzig.app;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.engine.http.ServerCall;
import org.restlet.routing.Router;

import com.dzig.api.CoordinateServerResource;

public class DzigApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {

        Router router = new Router(getContext());
        // Defines only one route
        router.attachDefault(CoordinateServerResource.class);

        return router;
    }
}
