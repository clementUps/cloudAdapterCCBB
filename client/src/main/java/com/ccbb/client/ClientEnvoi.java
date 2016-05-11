package com.ccbb.client;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import java.net.URL;
import java.util.TimerTask;
import java.util.concurrent.RunnableFuture;

public class ClientEnvoi extends TimerTask {
    private String adresseDestination = "127.0.0.1";
    private int portDestination = 2000;
    private final String url = "http://";
    private final String root = "/repartiteurVm";
    private static int nbRequete = 18;

    public ClientEnvoi(int _nbRequete, String _IpRepartiteur, int _PortRepartiteur) {
        nbRequete = _nbRequete;
        adresseDestination = _IpRepartiteur;
        portDestination = _PortRepartiteur;
    }

    public void client() throws Exception {
        // create configuration
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


        for(int i = 0; i < nbRequete; i++) {
            final int nb = i;
            Runnable runnable = new Runnable() {
                public void run() {
                    // make the a regular call
                    final Object[] params = new Object[]
                            { new String(" nombre "+nb), new Integer(3) };
                    String result = null;
                    try {
                        System.out.println("Envoie de requete");
                        result = (String) client.execute("Repartition.repartir", params);
                        System.out.println("2 + 3 = " + result);
                    } catch (XmlRpcException e) {
                        e.printStackTrace();
                    }
                }
            };
          new Thread(runnable).start();
        }
    }

    public static void setNbRequete(int nb){
        nbRequete = nb;
    }

    public void run() {
        try {
            client();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}