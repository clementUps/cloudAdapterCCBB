package com.ccbb.Reception;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by clement on 04/05/2016.
 */
public class EnvoieServer {

    public EnvoieServer(String ip, int port) throws MalformedURLException {
       // addServer(ip, port);
    }

    private static Server makeServer(String ip, int port)throws MalformedURLException{
        // create configuration
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://"+ip+":"+port+"/xmlrpc"));
        config.setEnabledForExtensions(true);
        config.setConnectionTimeout(60 * 1000);
        config.setReplyTimeout(60 * 1000);

        XmlRpcClient client = new XmlRpcClient();

        // use Commons HttpClient as transport
        client.setTransportFactory(
                new XmlRpcCommonsTransportFactory(client));
        // set configuration
        client.setConfig(config);
        Server server = new Server();
        server.setIp(ip);
        server.setClient(client);
        server.setPort(port);
        server.setPret(true);

        return server;
    }

    public static void addServer(String ip, int port) throws MalformedURLException{
        Server server = makeServer(ip,port);
        Algorithme.addServer(server);
    }

    public static void suppServer(String ip,int port) throws MalformedURLException {
        Server server = makeServer(ip,port);
        Algorithme.delServer(server);
    }


}
