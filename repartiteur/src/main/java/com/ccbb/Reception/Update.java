package com.ccbb.Reception;

import java.net.MalformedURLException;

/**
 * Created by clement on 04/05/2016.
 */
public class Update {
    public int update(String a,int b)
    {
        try {
            EnvoieServer.addServer(a,b);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return 3;
    }
}
