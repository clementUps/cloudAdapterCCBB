package com.ccbb;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.openstack4j.model.compute.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by CCA3441 on 10/05/2016.
 */
public class RepartiteurUpdate {

    public RepartiteurUpdate() throws InterruptedException {
        Vm vm = new Vm();
        vm.setIp(setVm(VM_NAME.concat(""+lstVm.size()),ImageVm.REPARTITEUR_GLOBAL.toString()));
        vm.setName(VM_NAME.concat(""+lstVm.size()));
        lstVm.add(vm);
    }

    public enum ImageVm {
        //Objets directement construits
        CLIENT ("ubuntuCCBBC"),
        REPARTITEUR_GLOBAL ("ccblImageServer");

        private String name = "";

        //Constructeur
        ImageVm(String name){
            this.name = name;
        }

        public String toString(){
            return name;
        }
    }

    public final static String VM_NAME = "privateCCBB";
    private static List<Vm> lstVm = new ArrayList<Vm>();

    public static Vm addVM() throws InterruptedException {
        Vm vm = new Vm();
        vm.setIp(setVm(VM_NAME.concat(""+lstVm.size()),ImageVm.REPARTITEUR_GLOBAL.toString()));
        vm.setName(VM_NAME.concat(""+lstVm.size()));
        lstVm.add(vm);
        return vm;
    }

    public static String setVm(String vmName,String imageVm ) throws InterruptedException {
       executeProcess("nova boot --flavor m1.small --image "+imageVm
                + " --nic net-id=c1445469-4640-4c5a-ad86-9c0cb6650cca --security-group default"
                + " --key-name myKeyCCBB "+vmName);
        String strFinal =  "nova list | grep "+vmName;
        String ip = "";
        while (!(ip = executeProcess(strFinal)).contains("Running")) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return ip.split("private=")[1].substring(0,16).trim();
    }


    public static void removeVm(String ip){
        String name;
        if(!(name = findAndRemove(ip)).equals("")) {
            delVm(name);
        }
    }

    public static void delVm(String name){
        executeProcess("nova delete "+name);
    }

    public static List<Vm> getList(){
        return lstVm;
    }

    public static String findAndRemove(String ip){
        String name  = "";
        Iterator<Vm> i = lstVm.iterator();
        while (i.hasNext()) {
            Vm vm = i.next();
            if(vm.getIp().equals(ip)) {
                name = vm.getName();
                i.remove();
                return name;
            }
        }
        return name;
    }

    public static String executeProcess(String cmd) {
        ProcessBuilder process = new ProcessBuilder("/bin/sh", "-c", cmd);
        System.out.println("début ");
        Process p;
        StringBuilder sb = new StringBuilder();
        try {
            System.out.println("start ");
            p = process.start();
            System.out.println("lancé ");
            try {
                System.out.println("début wait ");
                p.waitFor();
                System.out.println("fin wait ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String ligne;

            while (( ligne = br.readLine()) != null) {
                sb.append(ligne).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
