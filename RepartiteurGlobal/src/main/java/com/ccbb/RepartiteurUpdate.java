package com.ccbb;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CCA3441 on 10/05/2016.
 */
public class RepartiteurUpdate {

    public RepartiteurUpdate(){
        vmGlobal = new Vm();
        vmGlobal.setId(number);
        vmGlobal.setIp(setVm(VM_NAME.concat(""+number),ImageVm.FEDORA.toString()));
        vmGlobal.setName(VM_NAME.concat(""+number));
    }

    public enum ImageVm {
        //Objets directement construits
        CLIENT ("ubuntuCCBBC"),
        REPARTITEUR_GLOBAL ("ubuntuCCBBG"),
        REPARTITEUR_GENERAL ("ubuntuCCBBR"),
        FEDORA ("FC23");

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
    private static int number = 0;
    private static List<Vm> lstVm = new ArrayList<Vm>();
    private static Vm vmGlobal = new Vm();

    public static String addVM(){
        number++;

        Vm vm = new Vm();
        vm.setId(number);
        vm.setIp(setVm(VM_NAME.concat(""+number),ImageVm.FEDORA.toString()));
        vm.setName(VM_NAME.concat(""+number));
        lstVm.add(vm);
        try {
            sendId(vm);
            sendUpdate(vm);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            suppVm(vm);
        } catch (XmlRpcException e) {
            e.printStackTrace();
            suppVm(vm);
        }
        return "";
    }

    private static int sendId(Vm vm) throws MalformedURLException, XmlRpcException {
        // make the a regular call
        Object[] params = new Object[]
                { new Integer(vm.getId())};
        return send(vm,"Updater.addId",params);
    }

    private static int sendUpdate(Vm vm)  throws MalformedURLException, XmlRpcException {
        Object[] params = new Object[]
                {new String(vm.getIp()), new Integer(vm.getPort())};
        return send(vmGlobal,"Updater.update",params);
    }

    private static int send(Vm vm,String action,Object[] params) throws MalformedURLException, XmlRpcException {
        String _IPClient = vm.getIp();
        int _PortClient = vm.getPort();

        // create configuration
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://" + _IPClient +":"+ _PortClient +"/repartiteur"));
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
        return (Integer)client.execute(action, params);
    }

    public static String setVm(String vmName,String imageVm ){
        executeProcess("nova boot --flavor m1.small --image "+imageVm
                + " --nic net-id=c1445469-4640-4c5a-ad86-9c0cb6650cca --security-group default"
                + " --key-name myKeyCCBB "+vmName);
        String str = executeProcess("neutron floatingip-create public");
        String strFinal = "";
        for(String st : str.split("\n")){
            if(st.contains("floating_ip_address")){
                for(char s : st.split("floating_ip_address")[1].toCharArray()){
                    if(s != '|'){
                        strFinal += s;
                    }
                }
                System.out.println(strFinal.trim());
            }
        }
        executeProcess("nova floating-ip-associate "+vmName+" "+strFinal.trim());
        return strFinal;
    }

    public static void sendDelete(Vm vm) throws MalformedURLException, XmlRpcException {
        // make the a regular call
        Object[] params = new Object[]
                { new String(vm.getIp()),new Integer(vm.getPort()) };
        send(vmGlobal,"Updater.remove",params);
    }

    public static void suppVm(Vm vmName) {
        try {
            sendDelete(vmName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executeProcess("nova delete "+vmName.getName());
        }
    }

    public static String delVM(int id){
        List<Vm> ls = new ArrayList<Vm>();
        for(Vm vm : lstVm){
            if(vm.getId() == id){
                suppVm(vm);
            } else {
                ls.add(vm);
            }
        }
        lstVm.clear();
        lstVm.addAll(ls);
        return "del";
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
