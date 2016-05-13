package com.ccbb;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.openstack4j.api.OSClient;
import org.openstack4j.core.transport.Config;
import org.openstack4j.core.transport.ProxyHost;
import org.openstack4j.model.network.IPVersionType;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.Subnet;
import org.openstack4j.openstack.OSFactory;
import org.openstack4j.openstack.networking.domain.NeutronNetwork;
import org.openstack4j.openstack.networking.domain.NeutronSubnet;

/**
 * Created by CCA3441 on 11/05/2016.
 */
public class Main {
    private static final String url = "http://";
    private static final String adresseDestination = "127.0.0.1";
    private static final String portDestination = "10999";
    private static final String root = "/test";

    public static void main(String[] args) throws IOException, InterruptedException, XmlRpcException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(url.concat(adresseDestination).concat(":").concat(String.valueOf(portDestination)).concat(root)));
        config.setEnabledForExtensions(true);
        config.setConnectionTimeout(60 * 1000);
        config.setReplyTimeout(60 * 1000);
        final XmlRpcClient client = new XmlRpcClient();

        // use Commons HttpClient as transport
        client.setTransportFactory(
                new XmlRpcCommonsTransportFactory(client));
        // set configuration
        client.setConfig(config);
        final Object[] params = new Object[]
                { new String(" nombre ")};
        System.out.println(client.execute("A.bonjour", params));
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
