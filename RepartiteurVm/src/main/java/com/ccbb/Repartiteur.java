package com.ccbb;

import com.ccbb.repartiteurVm.ReceptionRequete;
import org.apache.xmlrpc.webserver.ServletWebServer;

import static java.lang.System.exit;
//  import org.apache.xmlrpc.demo.proxy.Adder;

public class Repartiteur {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Invalid argument : ./repartiteurVm PortRepartiteur\n");
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