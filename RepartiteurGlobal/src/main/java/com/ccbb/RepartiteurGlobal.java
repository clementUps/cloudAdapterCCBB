package com.ccbb;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.openstack.OSFactory;

import java.io.IOException;

/**
 * Created by CCA3441 on 09/05/2016.
 */
public class RepartiteurGlobal {

    public static void main(String[] args) throws Exception {
           OSClient os = OSFactory.builder()
                .endpoint("http://127.0.0.1:5000/v2.0")
                .credentials("ens11", "J8N9CE")
                .tenantName("service")
                .authenticate();
        Flavor flavor = Builders.flavor()
                .name("Large Resources Template")
                .ram(4096)
                .vcpus(6)
                .disk(120)
                .build();

        flavor = os.compute().flavors().create(flavor);
        ServerCreate sc = Builders.server().name("privateCCBLInstance").flavor(flavor).image("ccbbImage").build();

        Server server = os.compute().servers().boot(sc);
        System.out.print(server.getAccessIPv4());
    }

}
