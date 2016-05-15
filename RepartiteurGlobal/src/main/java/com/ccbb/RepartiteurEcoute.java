package com.ccbb;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimerTask;

/**
 * Created by CCA3441 on 11/05/2016.
 */
public class RepartiteurEcoute extends TimerTask {
    public static RepartiteurUpdate repartiteurUpdate;
    public static String adresseDestination = "";
    public static int number;
    public enum Requete {
        //Objets directement construits
        ADD ("Updater.update"),
        CHECK ("Updater.checkNbRequete"),
        DEL ("Updater.delete");

        private String name = "";

        //Constructeur
        Requete(String name){
            this.name = name;
        }

        public String toString(){
            return name;
        }
    }

    public RepartiteurEcoute(final RepartiteurUpdate ru,final String ip){
        repartiteurUpdate = ru;
        adresseDestination = ip;
    }

    public Object send(String requete,String[] params) throws MalformedURLException, XmlRpcException {
        String s = repartiteurUpdate.executeProcess("ssh fedora@"+adresseDestination+" '/home/fedora/test.sh "+requete+" "+params[0]+" "+params[1]+"'");
        System.out.println(s);
        return s;
    }

    public void run() {
        String[] params = new String[]
                {new String("check"), new String("e")};
        int nbRequete = 0;
        String ip = "";
        try {
            nbRequete = (Integer)send("check",params);
            if(nbRequete > 50){

                Vm vm=repartiteurUpdate.addVM();
                params = new String[]
                        {new String(vm.getIp()), new String("a")};
                ip = (String)send("add",params);
                if(ip.equals("")){
                    repartiteurUpdate.findAndRemove(ip);
                }
            } else if (nbRequete < 5){
                ip = (String)send("del",params);
                if(!ip.equals("")){
                    repartiteurUpdate.removeVm(ip);
                }
            }
            number = 0;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlRpcException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            if(number > 200){
                System.exit(0);
            }
            number++;
        }
    }
}


