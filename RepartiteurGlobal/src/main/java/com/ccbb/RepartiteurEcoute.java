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
    private static final String url = "http://";
    private static String adresseDestination = "127.0.0.1";
    private static final String portDestination = "2004";
    private static final String root = "/repartiteur";

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

    public Object send(Requete requete,Object[] params) throws MalformedURLException, XmlRpcException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(("http://").concat(adresseDestination).concat(":").concat(String.valueOf(portDestination)).concat(root)));
        System.out.println("url : "+config.getServerURL().getPath()+ "\nparams "+requete.toString());
        config.setEnabledForExtensions(true);
        config.setConnectionTimeout(60 * 1000);
        config.setReplyTimeout(60 * 1000);
        final XmlRpcClient client = new XmlRpcClient();

        // use Commons HttpClient as transport
        client.setTransportFactory(
                new XmlRpcCommonsTransportFactory(client));
        // set configuration
        client.setConfig(config);
        return client.execute(requete.toString(), params);
    }

    public void run() {
        Object[] params = new Object[]
                {new String("check")};
        int nbRequete = 0;
        String ip = "";
        try {
            nbRequete = (Integer)send(Requete.CHECK,params);
            if(nbRequete > 50){

                Vm vm=repartiteurUpdate.addVM();
                params = new Object[]
                        {new String(vm.getIp())};
                ip = (String)send(Requete.ADD,params);
                if(ip.equals("")){
                    repartiteurUpdate.findAndRemove(ip);
                }
            } else if (nbRequete < 5){
                ip = (String)send(Requete.DEL,params);
                if(!ip.equals("")){
                    repartiteurUpdate.removeVm(ip);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlRpcException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


