package com.ccbb;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.*;

/**
 * Created by CCA3441 on 11/05/2016.
 */
public class Main {

    public static void writeLog(String str){
        try {
            PrintWriter f = new PrintWriter("/home/fedora/projet/cloudAdapterCCBB/Test2/log.txt","UTF-8");
            f.write(str);
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException, XmlRpcException {
        System.out.println("Salut");
        writeLog("Debut du server");
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
        writeLog("Log port "+2000+" server start");
        webServer.start();
    }
}
