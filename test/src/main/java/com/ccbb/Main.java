package com.ccbb;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;
import org.openstack4j.api.OSClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;

/**
 * Created by CCA3441 on 11/05/2016.
 */
public class Main {
    private static final String url = "http://";
   // private static final String adresseDestination = "127.0.0.1";
    private static final String portDestination = "2003";
    private static final String root = "/test";

    public static void sendRequest(String adresseDestination) throws IOException, InterruptedException, XmlRpcException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(url.concat(adresseDestination).concat(":").concat(String.valueOf(portDestination)).concat(root)));
        config.setEnabledForExtensions(true);
        config.setConnectionTimeout(60 * 1000);
        config.setReplyTimeout(60 * 1000);
        final XmlRpcClient client = new XmlRpcClient();

        // use Commons HttpClient as transport
        client.setTransportFactory(
                new XmlRpcCommonsTransportFactory(client));
        // set configuration
        client.setConfig(config);
        final Object[] params = new Object[]
                { new String(" nombre ")};
        System.out.println(client.execute("A.bonjour", params));

    }

    public static void main(String[] args) throws IOException, InterruptedException, XmlRpcException {
        if(args.length == 1) {
            sendRequest(args[0]);
        }

        System.out.println("Salut");
        InetAddress adrLocale = InetAddress.getLocalHost();
        System.out.println(" "+adrLocale.getHostAddress());
        WebServer webServer = new WebServer(2003);
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
