package com.ccbb.Reception;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * Created by clement on 06/05/2016.
 */
public class Algorithme {
    private static Queue<Server> servers;
    private static List<Tache> tacheList;
    private static boolean isUse = false;
    private static List<Server> serversSupp;
    private static boolean isDelete = false;
    private static int id;

    public static void setId(int ids){
        id = ids;
    }


    public synchronized static void algorithme() throws InterruptedException, MalformedURLException, XmlRpcException {
        isUse = true;
        if (tacheList == null) {
            tacheList = new ArrayList<Tache>();
        }
        if(!isDelete){
            createVm();
        }
        while (tacheList.size() > 0) {
            Thread.sleep(1);
            final Server server = servers.poll();
            if (server != null && server.getPret()) {
                server.setPret(false);
                final Tache tache = tacheList.remove(0);
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            System.out.print("Server " + server.getIp() + " port " + server.getPort() +"                        " );
                            tache.setResult((String) server.getClient().execute("Calculator.add", tache.getParams()));
                            tache.notifyResult();
                            server.setPret(true);
                            addServer(server);
                        } catch (XmlRpcException e) {
                            e.printStackTrace();
                        }
                    }
                };
                if (tache != null) {
                    tache.getThread(runnable).start();
                } else {
                    System.out.println("null");
                }
            }

        }
        isDelete = true;
        deleteVm();
        isUse = false;

    }

    private static void deleteVm() throws MalformedURLException, XmlRpcException {
        send("del");
    }

    private static int send(String action) throws MalformedURLException, XmlRpcException {
        // create configuration
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://195.220.53.63:2000/gestionVm"));
        //config.setServerURL(new URL("http://127.0.0.1:19000/updateClient"));
        config.setEnabledForExtensions(true);
        config.setConnectionTimeout(60 * 1000);
        config.setReplyTimeout(60 * 1000);

        XmlRpcClient client = new XmlRpcClient();

        // use Commons HttpClient as transport
        client.setTransportFactory(
                new XmlRpcCommonsTransportFactory(client));
        // set configuration
        client.setConfig(config);
        Object[] params = new Object[]
                { new Integer(id)};
        return (Integer)client.execute("RepartiteurEcoute."+action, params);
    }

    private static void createVm() throws MalformedURLException, XmlRpcException {
        send("add");
    }

    public static void addServer(final Server serverAdd) {
        if (servers == null) {
            servers = new LinkedList<Server>();
        }
        System.out.print(serverAdd.getPort()+" ");
        if (!servers.contains(serverAdd)) {
            System.out.println(""+serverAdd.getPret());
            servers.add(serverAdd);
        }
    }

    public synchronized static void addTache(Tache tache) throws InterruptedException {
        if (tacheList == null) {
            tacheList = new ArrayList<Tache>();
        }
        tacheList.add(tache);
        if (!isUse) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        algorithme();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (XmlRpcException e) {
                        e.printStackTrace();
                    }
                }
            };
            new Thread(runnable).start();
        }
    }
}
