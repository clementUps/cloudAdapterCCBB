package com.ccbb;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;

/**
 * Created by CCA3441 on 09/05/2016.
 */
public class RepartiteurGlobal {
    private static Process process;

    public static void main(String[] args) throws Exception {
        init();
        RepartiteurUpdate repartiteurUpdate = new RepartiteurUpdate();
        repartiteurUpdate.addVM();
    }

    private static void init() throws IOException, XmlRpcException {
        WebServer webServer = new WebServer(2000);

        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

        PropertyHandlerMapping phm = new PropertyHandlerMapping();
        phm.load(Thread.currentThread().getContextClassLoader(),
                "XmlRpcServlet.properties");
        xmlRpcServer.setHandlerMapping(phm);

        XmlRpcServerConfigImpl serverConfig =
                (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
        serverConfig.setEnabledForExtensions(true);
        serverConfig.setContentLengthOptional(false);
        System.out.println("fin init server");
        webServer.start();
    }

}
