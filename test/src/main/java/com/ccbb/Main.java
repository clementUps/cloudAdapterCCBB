package com.ccbb;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by CCA3441 on 11/05/2016.
 */
public class Main {

     static  Timer timer = new Timer();
     static TimerTask exitApp = new TimerTask() {
        public void run() {
            System.exit(0);
        }
    };

    public static void timedExit() {
        timer.schedule(exitApp, new Date(System.currentTimeMillis()+5*1000));
    }


    public static void main(String[] args) throws IOException, InterruptedException, XmlRpcException {
        timedExit();
        System.out.println("Salut");
        InetAddress adrLocale = InetAddress.getLocalHost();
        System.out.println(" "+adrLocale.getHostAddress());
        WebServer webServer = new WebServer(11999);
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
