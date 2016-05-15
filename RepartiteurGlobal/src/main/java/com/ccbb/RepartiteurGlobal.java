package com.ccbb;

import java.util.Scanner;

/**
 * Created by CCA3441 on 09/05/2016.
 */
public class RepartiteurGlobal {


    public static void main(String[] args) throws Exception {
        if(args.length < 1){
            System.exit(1);
        }
        RepartiteurUpdate repartiteurUpdate = new RepartiteurUpdate();
        RepartiteurEcoute ecoute = new RepartiteurEcoute(repartiteurUpdate,args[0]);
       /* Timer timer = new Timer();
        timer.schedule(ecoute, 0, 5000);
        */
    }

}
