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
        System.out.println("ssh fedora@"+adresseDestination+" '/home/fedora/test.sh "+requete+" "+params[0]+" "+params[1]+"'");
        String s = repartiteurUpdate.executeProcess("ssh fedora@"+adresseDestination+" '/home/fedora/test.sh "+requete+" "+params[0]+" "+params[1]+"'");
        System.out.println(s);
        return s;
    }

    public void run() {
        String[] params = new String[]
                {new String("localhost"), new String("2003")};
        int nbRequete = 0;
        String ip = "";
        try {
            String st = (String) send("check",params);
            if(!st.equals("")){
                nbRequete = Integer.parseInt(st);
            } else {
                nbRequete = -1;
            }
            if(nbRequete > 50){

                Vm vm=repartiteurUpdate.addVM();
                params = new String[]
                        {new String(vm.getIp()), new String("2003")};
                ip = (String)send("add",params);
                if(ip.equals("")){
                    repartiteurUpdate.findAndRemove(ip);
                }
            } else if (nbRequete < 5 && nbRequete > 0){
                ip = (String)send("del",params);
                if(!ip.equals("")){
                    repartiteurUpdate.removeVm(ip);
                }
            }
            number = 0;
        } catch (MalformedURLException e) {
            System.out.println("erreur "+e.getMessage());
            e.printStackTrace();
        } catch (XmlRpcException e) {
            System.out.println("erreur "+e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("erreur "+e.getMessage());
            e.printStackTrace();
            if(number > 200){
                System.exit(0);
            }
            number++;
        }
    }
}


