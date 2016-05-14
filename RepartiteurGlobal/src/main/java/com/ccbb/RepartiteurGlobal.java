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
        Scanner input = new Scanner(System.in); //opens a scanner, keyboard
        String cm = "";
        System.out.println("Rentrer une commande");
        while(!cm.contains("quit")){
            if(cm.contains("add")) {
                repartiteurUpdate.addVM();
            } else if(cm.contains("del")){
                repartiteurUpdate.removeVm(repartiteurUpdate.getList().get(0).getIp());
            } else if(cm.contains("list")){
                repartiteurUpdate.executeProcess("nova list");
            } else if(cm.contains("check")){
                Object[] params = new Object[]
                        {new String("check")};
                System.out.println(ecoute.send(RepartiteurEcoute.Requete.CHECK,params));
            } else if(cm.contains("create")){

            } else if(cm.contains("remove")){

            }
            cm = input.next();
        }


    }

}
