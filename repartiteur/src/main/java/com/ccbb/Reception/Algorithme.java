package com.ccbb.Reception;

import org.apache.xmlrpc.XmlRpcException;

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


    public synchronized static void algorithme() throws InterruptedException {
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


    public static void delServer(final Server serverSupp) {
        if (servers != null) {
            if (servers.contains(serverSupp)) {
                System.out.println("Server Supprimer");
                servers.remove(serverSupp);
            } else {
                System.out.println("Ajout a la liste des server a supprimer");
                addSuppServer(serverSupp);
            }
        }
    }

    private static void addSuppServer(Server server) {
        if (serversSupp == null) {
            serversSupp = new ArrayList<Server>();
        }
        serversSupp.add(server);
    }

    private static void suppServer(Server server) {
        if (serversSupp != null && serversSupp.contains(server)) {
            serversSupp.remove(server);
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
                    }
                }
            };
            new Thread(runnable).start();
        }
    }
}
