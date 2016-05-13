package com.ccbb.updateR;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import java.net.URL;

import static java.lang.System.exit;

/**
 * Created by clement on 02/05/2016.
 */
public class UpdateRepartiteur {

    static String url = "http://";

    public static void main(String[] args) throws Exception {
        if (args.length != 5) {
            System.out.println("Invalid argument : ./update_repartiteur IPRepartiteur PortRepartiteur add|del Argument1 Argument2\n");
            exit(-1);
        }
        String _IPRepartiteur = args[0];
        int _PortRepartiteur = Integer.parseInt(args[1]);
        String addOrDel = args[2];
        String _Argument1 = args[3];
        int _Argument2 = Integer.parseInt(args[4]);

        // create configuration
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(url + _IPRepartiteur +":"+ _PortRepartiteur +"/repartiteur"));
        //config.setServerURL(new URL("http://127.0.0.1:2000/repartiteur"));
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
                {new String(_Argument1), new Integer(_Argument2)};
        if (addOrDel.equals("add")) {
            Integer result = (Integer) client.execute("Updater.update", params);
        } else if (addOrDel.equals("del")) {
            Integer result = (Integer) client.execute("Updater.remove", params);
        }
        // make a call using dynamic proxy
        /*          ClientFactory factory = new ClientFactory(client);
            Adder adder = (Adder) factory.newInstance(Adder.class);
            int sum = adder.add(2, 4);
            System.out.println("2 + 4 = " + sum);
        */
    }
}
