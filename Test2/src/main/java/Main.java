import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Created by CCA3441 on 11/05/2016.
 */
public class Main {
    private static final String url = "http://";
    private static final String adresseDestination = "195.220.53.35";
    private static final String portDestination = "2000";
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
        client.execute("A.bonjour", params);
    }

}
