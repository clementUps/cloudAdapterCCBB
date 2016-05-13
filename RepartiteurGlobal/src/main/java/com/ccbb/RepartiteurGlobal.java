package com.ccbb;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.openstack.OSFactory;

import java.io.IOException;

/**
 * Created by CCA3441 on 09/05/2016.
 */
public class RepartiteurGlobal {

    public static void main(String[] args) throws Exception {
        ServerCreate sc = Builders.server().name("privateCCBLInstance").image("ccbbImage").build();
        OSClient os = OSFactory.builder()
                .endpoint("http://127.0.0.1:5000/v2.0")
                .credentials("ens11", "J8N9CE")
                .tenantName("service")
                .authenticate();
        Server server = os.compute().servers().boot(sc);
        System.out.print(server.getAccessIPv4());
    }

}
