package com.ccbb.repartiteur;

import com.ccbb.Reception.Algorithme;
import com.ccbb.Reception.EnvoieServer;
import com.ccbb.Reception.ReceptionRequete;
import com.ccbb.Reception.Repartition;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.apache.xmlrpc.webserver.ServletWebServer;

import java.net.URL;

import static java.lang.System.exit;
//  import org.apache.xmlrpc.demo.proxy.Adder;

public class Repartiteur {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Invalid argument : ./repartiteur PortRepartiteur\n");
            exit(-1);
        }
        try {
            int _PortRepartiteur = Integer.parseInt(args[0]);
            ReceptionRequete receptionRequete = new ReceptionRequete(_PortRepartiteur);
            receptionRequete.init();
        } catch (ServletWebServer.Exception e) {
            System.out.println("Error : "+e.getMessage());
        }
    }


}