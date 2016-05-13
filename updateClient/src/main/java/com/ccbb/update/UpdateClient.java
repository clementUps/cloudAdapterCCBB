package com.ccbb.update;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import java.net.URL;

import static java.lang.System.exit;

/**
 * Created by clement on 02/05/2016.
 */
public class UpdateClient {

    private static String url = "http://";

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println("Invalid argument : ./update_client DebitClient IPClient PortClient\n");
            exit(-1);
        }
        int _DebitClient = Integer.parseInt(args[0]);
        String _IPClient = args[1];
        int _PortClient = Integer.parseInt(args[2]);

        // create configuration
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(url + _IPClient +":"+ _PortClient +"/updateClient"));
        //config.setServerURL(new URL("http://127.0.0.1:19000/updateClient"));
        config.setEnabledForExtensions(true);
        config.setConnectionTimeout(60 * 1000);
        config.setReplyTimeout(60 * 1000);

        XmlRpcClient client = new XmlRpcClient();

        // use Commons HttpClient as transport
        client.setTransportFactory(
                new XmlRpcCommonsTransportFactory(client));
        // set configuration
        client.setConfig(config);

        // make the a regular call
        Object[] params = new Object[]
                { new Integer(_DebitClient) };
        client.execute("Updater.update", params);
        System.out.println("Fin de l'update");

        // make a call using dynamic proxy
    /*          ClientFactory factory = new ClientFactory(client);
        Adder adder = (Adder) factory.newInstance(Adder.class);
        int sum = adder.add(2, 4);
        System.out.println("2 + 4 = " + sum);
    */
    }
}
