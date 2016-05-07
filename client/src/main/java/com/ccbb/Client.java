package com.ccbb;

import com.ccbb.client.ClientEnvoi;
import com.ccbb.update.Update;
import org.apache.xmlrpc.webserver.ServletWebServer;

import java.util.Timer;

import static java.lang.System.exit;

/**
 * Created by clement on 02/05/2016.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println("Invalid argument : ./client NbRequetesParSecondes IPRepartiteur PortRepartiteur\n");
            exit(-1);
        }
        try {
            int _nbRequete = Integer.parseInt(args[0]);
            String _IpRepartiteur = args[1];
            int _PortRepartiteur = Integer.parseInt(args[2]);
            ClientEnvoi clientEnvoi = new ClientEnvoi(_nbRequete, _IpRepartiteur, _PortRepartiteur);
            Timer timer = new Timer();
            timer.schedule(clientEnvoi, 0, 1000);
            Update update = new Update();
            update.init();
        } catch (ServletWebServer.Exception e) {
            System.out.println("Error :"+e.getMessage());
        }
    }
}
