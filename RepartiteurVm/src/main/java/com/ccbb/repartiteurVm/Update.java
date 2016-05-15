package com.ccbb.repartiteurVm;

import java.net.MalformedURLException;

/**
 * Created by clement on 04/05/2016.
 */
public class Update {
    public String update(String a,int port)
    {
        try {
            EnvoieServer.addServer(a,2003);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }
        return "0";
    }

    public String checkNbRequete(String check,int a){
        return ""+Algorithme.getNbRequete();
    }

    public String delete(String ip,int port){
        try {
            return EnvoieServer.deleteServer();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
