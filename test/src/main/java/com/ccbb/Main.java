package com.ccbb;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * Created by CCA3441 on 11/05/2016.
 */
public class Main {


    public static void main(String[] args) throws IOException, InterruptedException, XmlRpcException {
        System.out.println("Salut");
        InetAddress adrLocale = InetAddress.getLocalHost();
        System.out.println(" "+adrLocale.getHostAddress());
        WebServer webServer = new WebServer(2000,adrLocale);
        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

        PropertyHandlerMapping phm = new PropertyHandlerMapping();
        phm.load(Thread.currentThread().getContextClassLoader(),
                "XmlRpcServlet.properties");
        xmlRpcServer.setHandlerMapping(phm);

        XmlRpcServerConfigImpl serverConfig =
                (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
        serverConfig.setEnabledForExtensions(true);
        serverConfig.setContentLengthOptional(false);
        System.out.println("fin init server ");
        webServer.start();
    }
}
