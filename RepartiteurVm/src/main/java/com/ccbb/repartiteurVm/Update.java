package com.ccbb.repartiteurVm;

import java.net.MalformedURLException;

/**
 * Created by clement on 04/05/2016.
 */
public class Update {
    public int update(String a,int port)
    {
        try {
            EnvoieServer.addServer(a,2003);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public int checkNbRequete(String check){
        return Algorithme.getNbRequete();
    }

    public int delete(String ip,int port){
        try {
            EnvoieServer.deleteServer(ip,2003);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }
}
