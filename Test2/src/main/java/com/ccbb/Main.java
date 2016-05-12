package com.ccbb;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import java.io.IOException;
import java.net.URL;

import org.openstack4j.api.OSClient;
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
    private static final String adresseDestination = "192.168.56.1";
    private static final String portDestination = "2001";
    private static final String root = "/test";

    public static void main(String[] args) throws IOException, InterruptedException, XmlRpcException {
       /* XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
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
        System.out.println(client.execute("A.bonjour", params));*/




                OSClient os = OSFactory.builder()
                        .endpoint("http://195.220.53.63:1500/v2.0")
                        .credentials("ens11","J8N9CE").tenantName("service")
                        .authenticate();
                System.out.println(os);
                System.out.println(os.images().list());

    }

}
