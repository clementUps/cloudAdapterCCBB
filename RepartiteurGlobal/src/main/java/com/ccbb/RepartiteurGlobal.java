package com.ccbb;

import java.util.Scanner;
import java.util.Timer;

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
        System.out.println("fin init ");
        Timer timer = new Timer();
        timer.schedule(ecoute, 0, 5000);
    }

}
