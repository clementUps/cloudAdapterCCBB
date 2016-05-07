package com.ccbb.update;

import com.ccbb.client.ClientEnvoi;

/**
 * Created by clement on 02/05/2016.
 */
public class Updater {

    public int update(int nb ){
        ClientEnvoi.setNbRequete(nb);
        return 1;
    }
}
