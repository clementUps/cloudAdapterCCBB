package com.ccbb;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;
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
       /* Timer timer = new Timer();
        timer.schedule(ecoute, 0, 5000);
        */
        Scanner input = new Scanner(System.in); //opens a scanner, keyboard
        String cm = "";
        while((cm =input.next()) != "quit"){
            if(cm.equals("add")) {
                repartiteurUpdate.addVM();
            } else if(cm.equals("del")){
                repartiteurUpdate.removeVm(repartiteurUpdate.getList().get(0).getIp());
            } else if(cm.equals("list")){
                repartiteurUpdate.executeProcess("nova list");
            } else if(cm.equals("check")){
                Object[] params = new Object[]
                        {new String("check")};
                System.out.println(ecoute.send(RepartiteurEcoute.Requete.CHECK,params));
            } else if(cm.equals("create")){

            } else if(cm.equals("remove")){

            }
        }


    }

}
