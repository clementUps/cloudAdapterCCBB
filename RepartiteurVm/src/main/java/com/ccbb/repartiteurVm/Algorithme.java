package com.ccbb.repartiteurVm;

import org.apache.xmlrpc.XmlRpcException;

import java.net.MalformedURLException;
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

    public static int getNbRequete(){
        return tacheList.size();
    }

    public synchronized static void deleteServer(Server server){
        if(servers.contains(server)){
            servers.remove(server);
        }
    }

    public synchronized static void algorithme() throws InterruptedException, MalformedURLException, XmlRpcException {
        isUse = true;
        if (tacheList == null) {
            tacheList = new ArrayList<Tache>();
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
                            tache.setResult((String) server.getClient().execute("Repartition.repartir", tache.getParams()));
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
        isUse = false;

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
