package org.mallen.test.cling.dms.httpserver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyServer {
    private int port;

    public JettyServer(int port) throws Exception {
        this.port = port;
        Server server = new Server(port);
        ContentHandler contentHandler = new ContentHandler();

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(".");

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{contentHandler, resourceHandler, new DefaultHandler()});
        server.setHandler(handlerList);

        server.start();
        server.join();
    }
}
